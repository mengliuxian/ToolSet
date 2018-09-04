package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.DoctorBroadcastAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DelFastReplyBody;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.SignUtil;
import com.flym.yh.utils.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 名医广播
 */

public class DoctorBroadcastFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;
    @BindView(R.id.tv_new)
    TextView tvNew;

    private int type = 0;
    private int page = 1;
    private List<GetServicesBean.ListBean> dataList = new ArrayList<>();
    private ArrayList<Integer> dataSList = new ArrayList<>();
    private List<GetServicesBean.ListBean> selectedList = new ArrayList<>();
    private DoctorBroadcastAdapter mAdapter;
    private int isRefresh;

    public static DoctorBroadcastFragment newInstance() {

        Bundle args = new Bundle();

        DoctorBroadcastFragment fragment = new DoctorBroadcastFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initData() {
        if (dataList.size() <= 0 ||  isRefresh == 1) {
            getListOld();
            isRefresh = 0;
        }
            setAdapter();
            setSpring();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new DoctorBroadcastAdapter(dataList, type);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetServicesBean.ListBean listBean = dataList.get(position);

                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        DoctorLectureDetailFragment.newInstance(listBean.getId()), getFragmentManager(), getString(R.string.details));
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GetServicesBean.ListBean listBean = dataList.get(position);
                if (!listBean.selected) {
                    if (!selectedList.contains(listBean)) {
                        selectedList.add(listBean);
                        dataSList.add(position);
                        listBean.selected = true;
                    }
                } else {
                    if (selectedList.contains(listBean)) {
                        selectedList.remove(listBean);
                        dataSList.remove(Integer.valueOf(position));
                        listBean.selected = false;
                    }
                }
                mAdapter.notifyItemChanged(position);
            }
        });
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

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public Toolbar getToolbar() {
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.manage));
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_doctor_broadcast;
    }

    @OnClick({R.id.toolbar_right, R.id.tv_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                if (type == 1) {
                    type = 0;
                    toolbarTitle.setText(getTag());
                    toolbarRight.setText(getString(R.string.manage));
                    tvNew.setText(getString(R.string.publish_article));
                } else {
                    type = 1;
                    toolbarTitle.setText(getString(R.string.del));
                    toolbarRight.setText(getString(R.string.complete));
                    tvNew.setText(getString(R.string.del));
                }
                mAdapter.setType(type);
                break;
            case R.id.tv_new:
                if (type == 0) {
                    isRefresh = 1;
                    FragmentUtil.replaceFragmentToActivity(R.id.container, NewDoctorBroadcastFragment.newInstance(), getFragmentManager(), getString(R.string.publish_broadcast));
                } else {
                    doctoritemsDel();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取服务列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getServicesList(1, 38)
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
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                }));
    }

    /**
     * 删除服务
     */
    public void doctoritemsDel() {
        DelFastReplyBody body = new DelFastReplyBody();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(context));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("id", dataSList);

        body.token = UserManager.getToken(context);
        body.appKey = "android";
        body.time = String.valueOf(System.currentTimeMillis() / 1000);
        body.id = dataSList;
        body.sign = SignUtil.getSign(map);


        String s = GsonUtil.toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().doctoritemsDel(requestBody)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString bean) {
                        dataList.removeAll(selectedList);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

}
