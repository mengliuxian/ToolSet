package com.flym.yh.ui.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.FastReplyAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DelFastReplyBody;
import com.flym.yh.data.model.FastReplyBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.SignUtil;
import com.flym.yh.utils.ToastUtil;
import com.flym.yh.utils.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 快捷回复
 */
public class FastReplyActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.spring)
    SpringView spring;

    private int type;
    private FastReplyAdapter adapter;
    private double oldPosition;
    private String content;
    private Dialog dialog;

    private ArrayList<FastReplyBean.ListBean> dataList = new ArrayList<>();
    private ArrayList<FastReplyBean.ListBean> dataSList = new ArrayList<>();
    private ArrayList<Integer> selecterList = new ArrayList<>();
    private Dialog dialog1;

    @Override
    protected String getPagerTitle() {
        return getString(R.string.fast_reply);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fast_reply;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        initSpring();
    }

    private void initSpring() {

        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                shortcutGetlist();
            }

            @Override
            public void onLoadmore() {
                page++;
                shortcutGetlist();
            }
        });
        spring.setFooter(new DefaultFooter(this));
        spring.setHeader(new DefaultHeader(this));

    }

    private void initTab() {
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.del));
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 0 && dataList.size() != 0) {
                    type = 1;
                    add.setText(getString(R.string.del));
                    toolbarRight.setText(getString(R.string.cancel));
                } else {
                    for (int i = 0; i < dataList.size(); i++) {
//                        MedicationTemplateBean.ListBean listBean = dataList.get(i);
//                        listBean.setShow(false);
                    }
                    type = 0;
                    add.setText(getString(R.string.add_template));
                    toolbarRight.setText(getString(R.string.complete));
                }
                adapter.setType(type);
            }
        });


    }

    @Override
    protected void initData() {
        shortcutGetlist();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FastReplyAdapter(dataList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FastReplyBean.ListBean listBean = dataList.get(position);
                if (type != 0) {
                    if (listBean.show) {
                        listBean.show = false;
                        selecterList.remove(Integer.valueOf(listBean.id));
                        dataSList.remove(listBean);
                    } else {
                        listBean.show = true;
                        selecterList.add(listBean.id);
                        dataSList.add(listBean);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FastReplyBean.ListBean listBean = dataList.get(position);
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("content", listBean.content);
                    setResult(RESULT_OK, intent);
                    ActivityManager.getInstance().popActivity(FastReplyActivity.this);
                }
            }
        });

    }


    @OnClick({R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (type == 0) {

                    content = "";

                    dialog = CustomDialog.addFastReply(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(content)) {
                                dialog.dismiss();
                                shortCutsave();
                            } else {
                                ToastUtil.showMessage(FastReplyActivity.this,
                                        "内容不能为空");
                            }
                        }
                    }, new CustomDialog.EditTextReultListence() {
                        @Override
                        public void onEditReult(String s) {
                            content = s;
                        }
                    });
                } else if (type == 1) {
                    shortCutdel();
                }

                break;
        }
    }

    private int page = 1;

    /**
     * 获取快捷回复列表
     */
    public void shortcutGetlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().shortcutGetlist(page)
                .compose(new SimpleTransFormer<FastReplyBean>(FastReplyBean.class))
                .subscribeWith(new DisposableWrapper<FastReplyBean>() {
                    @Override
                    public void onNext(FastReplyBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                })
        );
    }

    /**
     * 新增快捷回复
     */
    public void shortCutsave() {
        compositeDisposable.add(RetrofitUtil.getApiService().shortCutsave(content)
                .compose(new SimpleTransFormer<FastReplyBean>(FastReplyBean.class))
                .subscribeWith(new DisposableWrapper<FastReplyBean>() {
                    @Override
                    public void onNext(FastReplyBean o) {
                        dialog1 = CustomDialog.sendPrescriptionSucces(FastReplyActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                page = 1;
                                shortcutGetlist();
                            }
                        });

                    }
                })
        );
    }

    /**
     * 删除快捷回复
     */
    @SuppressLint("CheckResult")
    public void shortCutdel() {
        DelFastReplyBody body = new DelFastReplyBody();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(this));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("id", selecterList);

        body.token = UserManager.getToken(this);
        body.appKey = "android";
        body.time = String.valueOf(System.currentTimeMillis() / 1000);
        body.id = selecterList;
        body.sign = SignUtil.getSign(map);


        String s = GsonUtil.toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().shortCutdel(requestBody)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString bean) {
                        dataList.removeAll(dataSList);
                        adapter.notifyDataSetChanged();
                    }
                });

    }

}
