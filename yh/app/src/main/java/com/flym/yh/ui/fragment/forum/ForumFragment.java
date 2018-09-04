package com.flym.yh.ui.fragment.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.mine.MyForumListAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.TopicGetlistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.forum.ReturnCardActivity;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by mengl on 2018/3/27.
 * 患者论坛
 */

public class ForumFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView spring;

    private MyForumListAdapter adapter;

    private ArrayList<TopicGetlistBean.ListBean> dataList = new ArrayList<>();
    private int page = 1;

    public static ForumFragment newInstance() {

        Bundle args = new Bundle();

        ForumFragment fragment = new ForumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        topicGetlist();

        adapter = new MyForumListAdapter(dataList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TopicGetlistBean.ListBean listBean = dataList.get(position);
                listBean.vis = true;
                adapter.notifyItemChanged(position);
                ActivityManager.getInstance().startNextActivity(new Intent(context, ReturnCardActivity.class
                        ).putExtra("id", listBean.id)
                );
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initSpring();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

    }

    /**
     * 设置刷新控件
     */
    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                topicGetlist();
            }

            @Override
            public void onLoadmore() {
                page++;
                topicGetlist();
            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));
        spring.setHeader(new DefaultHeader(getActivity()));
    }

    /**
     * 获取话题列表
     */
    public void topicGetlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().topicGetlist(page)
                .compose(new SimpleTransFormer<TopicGetlistBean>(TopicGetlistBean.class))
                .subscribeWith(new DisposableWrapper<TopicGetlistBean>() {
                    @Override
                    public void onNext(TopicGetlistBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

//                        spring.onFinishFreshAndLoad();
                    }
                }));
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_forum;
    }


}
