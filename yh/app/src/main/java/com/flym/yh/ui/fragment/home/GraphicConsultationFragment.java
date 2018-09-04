package com.flym.yh.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.GraphicConsultationAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.ChatGetuserBean;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.ChatActivity;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 图文咨询
 */

public class GraphicConsultationFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;

    private int page = 1;
    private ArrayList<GetServicesBean.ListBean> dataList = new ArrayList<>();
    private GraphicConsultationAdapter adapter;
    private String myName;

    public static GraphicConsultationFragment newInstance() {

        Bundle args = new Bundle();

        GraphicConsultationFragment fragment = new GraphicConsultationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getListOld();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new GraphicConsultationAdapter(dataList);
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetServicesBean.ListBean listBean = dataList.get(position);
                //跳转到聊天页面
                chatGetuser(listBean.getId(), listBean);
//                ActivityManager.getInstance().startNextActivity(
//                        new Intent(context, ChatActivity.class)
//                                .putExtra("bean", listBean)
//
//                );

            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setSpring();
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_graphic_consultation;
    }

    /**
     * 设置刷新控件
     */
    private void setSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                getListOld();
            }

            @Override
            public void onLoadmore() {
                page++;
                getListOld();
            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));
        spring.setHeader(new DefaultHeader(getActivity()));
    }

    /**
     * 获取服务列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .doctorGetorderlist(1, 32)
                .compose(new SimpleTransFormer<GetServicesBean>(Object.class))
                .subscribeWith(new DisposableWrapper<GetServicesBean>() {
                    @Override
                    public void onNext(GetServicesBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        if (o.getList() != null) {
                            dataList.addAll(o.getList());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                }));
    }

    /**
     * 获取账号密码
     */
    public void chatGetuser(int id, final GetServicesBean.ListBean listBean) {
//        if (!EMClient.getInstance().isLoggedInBefore()) {
        compositeDisposable.add(RetrofitUtil.getApiService().chatGetuser(id)
                .compose(new SimpleTransFormer<ChatGetuserBean>(ChatGetuserBean.class))
                .subscribeWith(new DisposableWrapper<ChatGetuserBean>() {
                    @Override
                    public void onNext(ChatGetuserBean o) {
                        if (o.info != null) {
                            myName = o.info.username;
                            //登陆环信
                            EMClient.getInstance().login(o.info.username, o.info.password, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    //环信登陆成功回调
                                    //两个方法是为了保证进入主页面后本地会话和群组都 load 完毕
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    ActivityManager.getInstance().startNextActivity(
                                            new Intent(context, ChatActivity.class)
                                                    .putExtra("name", listBean.nickname)
                                                    .putExtra("head", listBean.headimgurl)
                                                    .putExtra("time", listBean.getCreate_time())
                                                    .putExtra("myName", myName)
                                                    .putExtra("id", listBean.getId())

                                    );
                                }

                                @Override
                                public void onError(int code, String error) {

                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);


                    }
                })
        );
//        }

    }
}
