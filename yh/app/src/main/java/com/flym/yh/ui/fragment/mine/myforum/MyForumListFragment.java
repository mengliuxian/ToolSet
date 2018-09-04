package com.flym.yh.ui.fragment.mine.myforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
 * Created by mengl on 2018/3/26.
 * 我的论坛
 */

public class MyForumListFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView springView;

    private MyForumListAdapter adapter;
    private ArrayList<TopicGetlistBean.ListBean> dataList = new ArrayList<>();
    private int page = 1;

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.my_forum));
        return toolbar;
    }

    @Override
    protected void initData() {
        doctorGettopiclist();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        initSpring();
    }


    /**
     * 设置刷新控件
     */
    private void initSpring() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                doctorGettopiclist();
            }

            @Override
            public void onLoadmore() {
                page++;
                doctorGettopiclist();
            }
        });
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setHeader(new DefaultHeader(getActivity()));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_forum_list;
    }

    /**
     * 获取我的论坛列表
     */
    public void doctorGettopiclist() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGettopiclist(page)
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
                        springView.onFinishFreshAndLoad();
                    }
                })

        );
    }
}
