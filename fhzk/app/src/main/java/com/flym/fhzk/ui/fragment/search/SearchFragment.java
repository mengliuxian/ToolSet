package com.flym.fhzk.ui.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.HotKeyBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.search.GoodsActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.GsonUtil;
import com.flym.fhzk.utils.SharePreUtil;
import com.google.gson.reflect.TypeToken;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class SearchFragment extends BaseFragment {
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.remove)
    ImageView remove;
    @BindView(R.id.history_search)
    TagFlowLayout historySearch;
    @BindView(R.id.hot_search)
    TagFlowLayout hotSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TagAdapter<String> historyAdapter;
    private TagAdapter<HotKeyBean.KeyInfoBean> hotAdapter;

    ArrayList<String> history = new ArrayList<>();
    ArrayList<HotKeyBean.KeyInfoBean> hot = new ArrayList<>();
    private List<String> strings = new ArrayList<>();


    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initData() {
        getHotKey();
        historyAdapter = new TagAdapter<String>(strings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.view_tag, historySearch, false);
                TextView textView = (TextView) inflate;
                textView.setText(s);
                return textView;
            }
        };
        hotAdapter = new TagAdapter<HotKeyBean.KeyInfoBean>(hot) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyBean.KeyInfoBean s) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.view_tag, hotSearch, false);
                TextView textView = (TextView) inflate;
                textView.setText(s.key);
                return textView;
            }
        };

        historySearch.setAdapter(historyAdapter);
        hotSearch.setAdapter(hotAdapter);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //标签的点击回调
        historySearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String s = strings.get(position);
                //跳转到商品页面
                ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                        .putExtra("keyword", s));
                return true;
            }
        });
        hotSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                int s = hot.get(position).id;
                //跳转到商品页面
                ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                        .putExtra("Id", s));
                return true;
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @OnClick({R.id.search_text, R.id.remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_text:
                String s = searchEdit.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    //保存历史搜索记录
                    history.add(0, s);
                    if (history.size() > 10) {
                        strings = history.subList(0, 10);
                    } else {
                        strings.clear();
                        strings.addAll(history);
                    }
                    ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                            .putExtra("keyword", s));
                }
                break;
            case R.id.remove:
                strings.clear();
                history.clear();
                historyAdapter.notifyDataChanged();
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (strings.size() <= 0) {
            String s = SharePreUtil.getSting(context, "history");
            if (!TextUtils.isEmpty(s)) {
                List<String> string = GsonUtil.fromJson(s, new TypeToken<List<String>>() {
                });
                strings.addAll(string);
            }
        }
        historyAdapter.notifyDataChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String s = GsonUtil.toJson(history);
        SharePreUtil.putString(context, "history", s);
    }


    /**
     * 9.	热搜标签
     */
    public void getHotKey() {
        compositeDisposable.add(RetrofitUtil.getApiService().getHotKey(null)
                .compose(new SimpleTransFormer<HotKeyBean>(HotKeyBean.class))
                .subscribeWith(new DisposableWrapper<HotKeyBean>() {
                    @Override
                    public void onNext(HotKeyBean hotKeyBean) {
                        hot.clear();
                        if (hotKeyBean.keyInfo != null) {
                            hot.addAll(hotKeyBean.keyInfo);
                        }
                        hotAdapter.notifyDataChanged();
                    }
                }));
    }
}
