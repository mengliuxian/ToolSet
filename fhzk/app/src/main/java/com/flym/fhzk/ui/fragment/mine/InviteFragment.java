package com.flym.fhzk.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flym.fhzk.MyApplication;
import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.InviteInfoBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.ClipboardUtils;
import com.flym.fhzk.view.AutoWrapTextView;
import com.flym.fhzk.view.LoadingDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class InviteFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.accumulated_rebates)
    TextView accumulatedRebates;
    @BindView(R.id.success)
    TextView success;
    @BindView(R.id.can_rebates)
    TextView canRebates;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.text)
    AutoWrapTextView text;
    @BindView(R.id.text2)
    AutoWrapTextView text2;
    @BindView(R.id.invite)
    ImageView invite;

    public static InviteFragment newInstance() {

        Bundle args = new Bundle();

        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("邀请得奖励");
        return toolbar;
    }

    @Override
    protected void initData() {
        getInviteInfo();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_invite;
    }

    @OnClick(R.id.invite)
    public void onViewClicked() {

        new ShareAction(ActivityManager.getInstance().currentActivity())
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                //自定义按钮
                .addButton("umeng_sharebutton_custom", "copy", "umeng_socialize_copy", "")
                //点击事件的回调
                .setShareboardclickCallback(shareBoardlistener)
                .open();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 该监听器的目的是监听分享面板的点击事件：
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                //根据key来区分自定义按钮的类型，并进行对应的操作
                if (snsPlatform.mKeyword.equals("copy")) {
                    ClipboardUtils.coptPaste(context, "url");
                    Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
                }

            } else {
                UMWeb web = new UMWeb("http://www.baidu.com");
                web.setTitle("This is music title");
                web.setThumb(new UMImage(context, R.drawable.ic_simple_logo));
                web.setDescription("my description");
                //社交平台的分享行为
                new ShareAction(getActivity())
                        .setPlatform(share_media)
                        .setCallback(new UMShareListener() {
                            /**
                             * @descrption 分享开始的回调
                             * @param platform 平台类型
                             */
                            @Override
                            public void onStart(SHARE_MEDIA platform) {
                                //判断客户端安装
                                MyApplication.getUmShareAPI().isInstall(getActivity(), platform);
                            }

                            /**
                             * @descrption 分享成功的回调
                             * @param platform 平台类型
                             */
                            @Override
                            public void onResult(SHARE_MEDIA platform) {
                            }

                            /**
                             * @descrption 分享失败的回调
                             * @param platform 平台类型
                             * @param t 错误原因
                             */
                            @Override
                            public void onError(SHARE_MEDIA platform, Throwable t) {
                            }

                            /**
                             * @descrption 分享取消的回调
                             * @param platform 平台类型
                             */
                            @Override
                            public void onCancel(SHARE_MEDIA platform) {

                            }
                        })
                        .withMedia(web)
                        .share();
            }
        }
    };
    /**
     * 友盟分享回调监听
     */
//    private UMShareListener shareListener = ;

    /**
     * 32.邀请
     */
    public void getInviteInfo() {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        compositeDisposable.add(RetrofitUtil.getApiService().getInviteInfo(null)
                .compose(new SimpleTransFormer<InviteInfoBean>(InviteInfoBean.class))
                .subscribeWith(new DisposableWrapper<InviteInfoBean>(loadingDialog) {
                    @Override
                    public void onNext(InviteInfoBean inviteInfoBean) {
                        if (inviteInfoBean != null) {
                            accumulatedRebates.setText(inviteInfoBean.money);
                            canRebates.setText(String.valueOf(inviteInfoBean.numb));
                        }
                    }
                }));
    }
}
