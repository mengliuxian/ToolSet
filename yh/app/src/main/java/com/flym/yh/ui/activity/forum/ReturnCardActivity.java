package com.flym.yh.ui.activity.forum;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.forum.ReturnCardAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.TopicGetcommentlistBean;
import com.flym.yh.data.model.TopicGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.itemdecoration.LinearDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ReturnCardActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.r_title)
    TextView rTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.return_edit)
    EditText returnEdit;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.spring)
    SpringView spring;

    private ReturnCardAdapter adapter;
    private int id;
    private int page = 1;
    private ArrayList<TopicGetcommentlistBean.ListBean> dataList = new ArrayList<>();


    @Override
    protected String getPagerTitle() {
        return "回帖";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_return_card;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initSpring();
        id = getIntent().getIntExtra("id", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(this,
                LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {
                page++;
                topicGetcommentlist();
            }
        });
        spring.setFooter(new DefaultFooter(this));

    }

    @Override
    protected void initData() {
        topicGetdetail();
        topicGetcommentlist();

        adapter = new ReturnCardAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(returnEdit.getText().toString())) {
            topicComment();
        }

    }

    /**
     * 获取论坛详情
     */
    public void topicGetdetail() {
        compositeDisposable.add(RetrofitUtil.getApiService().topicGetdetail(id)
                .compose(new SimpleTransFormer<TopicGetdetailBean>(TopicGetdetailBean.class))
                .subscribeWith(new DisposableWrapper<TopicGetdetailBean>() {
                    @Override
                    public void onNext(TopicGetdetailBean topicGetdetailBean) {
                        setData(topicGetdetailBean);
                    }
                }));
    }

    private void setData(TopicGetdetailBean topicGetdetailBean) {
        if (topicGetdetailBean.info != null) {
            GlideUtil.loadImage(this, topicGetdetailBean.info.headimgurl, head, R.drawable.wd_grmp__touxiang);
            name.setText(topicGetdetailBean.info.nickname);
            time.setText(topicGetdetailBean.info.create_time);
            title.setText(topicGetdetailBean.info.title);
            content.setText(topicGetdetailBean.info.content);

        }

    }

    /**
     * 获取话题评论列表
     */
    public void topicGetcommentlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().topicGetcommentlist(id, page)
                .compose(new SimpleTransFormer<TopicGetcommentlistBean>(TopicGetcommentlistBean.class))
                .subscribeWith(new DisposableWrapper<TopicGetcommentlistBean>() {
                    @Override
                    public void onNext(TopicGetcommentlistBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();
                        spring.onFinishFreshAndLoad();

                    }
                }));
    }

    /**
     * 评论话题
     */
    public void topicComment() {
        compositeDisposable.add(RetrofitUtil.getApiService().topicComment(returnEdit.getText().toString()
                , id)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        returnEdit.setText("");
                        topicGetcommentlist();
                    }
                }));
    }


}
