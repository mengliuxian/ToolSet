package com.flym.yh.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetdetailBean;

import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.DateFormatUtil;

import com.flym.yh.utils.UserManager;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;

import java.text.ParseException;
import java.util.Date;

public class ChatActivity extends BaseActivity implements EaseChatFragment.onApp {

    private EaseChatFragment chatFragment;
    private String head;
    private String name;
    private int id;
    private DoctorGetdetailBean data;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        data = UserManager.getUserDataFromNative(this);
        name = getIntent().getStringExtra("name");
        id = getIntent().getIntExtra("id", 0);
        head = getIntent().getStringExtra("head");
        String time = getIntent().getStringExtra("time");
        final String myName = getIntent().getStringExtra("myName");
        String inTime = "1";
        try {
            Date date = DateFormatUtil.stringToDate(time, "yyyy-MM-dd HH:mm:ss");
            Date nowDate = new Date();
            if (((nowDate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000)) > 2) {
                inTime = "0";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        chatFragment = new EaseChatFragment();
        chatFragment.setOnApp(this);
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        if (!name.isEmpty()) {
            bundle.putString(EaseConstant.EXTRA_USER_ID, name);
            bundle.putString(EaseConstant.EXTRA_USER_NAME, name);
            bundle.putString(EaseConstant.EXTRA_USER_TIME, inTime);

        }

        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                if (username.equals(myName)) {
                    EaseUser user = new EaseUser(myName);
                    user.setNickname(myName);
                    if (data != null && data.info != null)
                        user.setAvatar(ApiConstants.getImageUrl(data.info.image));
                    return user;
                } else {
                    EaseUser user = new EaseUser(username);
                    user.setNickname(username);
                    user.setAvatar(head);

                    return user;
                }
            }
        });
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (chatFragment != null) {
//            chatFragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    @Override
    public void onComment() {

        //评论
        ActivityManager.getInstance().startNextActivity(
                new Intent(ChatActivity.this, CommentActivity.class)
                        .putExtra("name", name)
                        .putExtra("head", head)
                        .putExtra("id", id)
        );
    }

    @Override
    public void itemTakePict() {
        //下诊断
        ActivityManager.getInstance().startNextActivityForResult(new Intent(this,
                        DiagnosticTemplateActivity.class).putExtra("type", 1)
                , 1);


    }

    @Override
    public void itemPict() {
        //治疗建议
        ActivityManager.getInstance().startNextActivityForResult(new Intent(this,
                        TreatmentWillActivity.class)
                , 2);
    }

    @Override
    public void itemLocation() {
        //健康处方
        ActivityManager.getInstance().startNextActivity(new Intent(this,
                OpenHealthyPrescriptionActivity.class));
    }

    @Override
    public void fastReply() {
        //快捷回复

        ActivityManager.getInstance().startNextActivityForResult(new Intent(this,
                        FastReplyActivity.class)
                , 4);
    }
}
