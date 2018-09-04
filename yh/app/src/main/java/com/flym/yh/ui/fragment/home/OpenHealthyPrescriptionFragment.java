package com.flym.yh.ui.fragment.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.home.HealthyPrescriptionDetailsAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GoodsGetlistBean;
import com.flym.yh.data.model.RecipeGetlistBean;
import com.flym.yh.data.model.RecipeSendBody;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.DiagnosticTemplateActivity;
import com.flym.yh.ui.activity.home.ShoppingMallActivity;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.SignUtil;
import com.flym.yh.utils.ToastUtil;
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
 * @date 2018/3/26
 * 开健康处方
 */

public class OpenHealthyPrescriptionFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_patient_name)
    EditText edPatientName;
    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.ed_sex)
    EditText edSex;
    @BindView(R.id.ed_disease_name)
    EditText edDiseaseName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ed_note)
    EditText edNote;

    private List<GoodsGetlistBean.ListBean> dataList = new ArrayList<>();
    private HealthyPrescriptionDetailsAdapter mAdapter;
    private ArrayList<GoodsGetlistBean.ListBean> rList;
    HealthyPrescriptionDetailsFragment.OnReult ls;
    private RecipeGetlistBean.ListBean bean;
    private Dialog dialog;
    private Dialog dialog1;

    public static OpenHealthyPrescriptionFragment newInstance(RecipeGetlistBean.ListBean bean) {

        Bundle args = new Bundle();
        args.putParcelable("bean", bean);
        OpenHealthyPrescriptionFragment fragment = new OpenHealthyPrescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OpenHealthyPrescriptionFragment newInstance() {

        Bundle args = new Bundle();
        OpenHealthyPrescriptionFragment fragment = new OpenHealthyPrescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setLs(HealthyPrescriptionDetailsFragment.OnReult ls) {
        this.ls = ls;
    }

    @Override
    protected void initData() {
//        if (list.size() != 0) {
//            list.clear();
//        }
        setAdapter();
        setData();
    }

    private void setData() {
        if (bean != null) {
            edPatientName.setText(bean.user_name);
            edSex.setText(bean.sex);
            edAge.setText(String.valueOf(bean.age));
            edDiseaseName.setText(bean.name);
            edNote.setText(bean.content);
            if (bean.goods_list != null) {
                dataList.clear();
                dataList.addAll(bean.goods_list);
                mAdapter.notifyDataSetChanged();
            }

        }

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        dataList.add(new GoodsGetlistBean.ListBean());
        dataList.add(new GoodsGetlistBean.ListBean());
        mAdapter = new HealthyPrescriptionDetailsAdapter(dataList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        bean = getArguments().getParcelable("bean");
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.health_prescription));
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_open_healthy_prescription;
    }

    @OnClick({R.id.tv_import_template, R.id.tv_from_shop_mall, R.id.tv_add, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_import_template:
                ActivityManager.getInstance().startNextActivityForResult(
                        new Intent(context, DiagnosticTemplateActivity.class).putExtra("type", 1)
                        , 12);
                break;
            case R.id.tv_from_shop_mall:
                ActivityManager.getInstance().startNextActivityForResult(
                        new Intent(context, ShoppingMallActivity.class),
                        Constants.SHOPMALL);
                break;
            case R.id.tv_add:
                mAdapter.dissPop();
                dataList.add(new GoodsGetlistBean.ListBean());
                mAdapter.notifyItemChanged(dataList.size() - 1);
                break;
            case R.id.tv_send:
                if (!TextUtils.isEmpty(edPatientName.getText().toString()) &&
                        !TextUtils.isEmpty(edAge.getText().toString()) &&
                        !TextUtils.isEmpty(edSex.getText().toString()) &&
                        !TextUtils.isEmpty(edDiseaseName.getText().toString()) &&
                        check()) {
                    dialog = CustomDialog.sendPrescription(context, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            recipeSend();

                        }
                    });

                } else {
                    ToastUtil.showMessage(getActivity(), "请把信息输入完整");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SHOPMALL && data != null) {
            rList = data.getParcelableArrayListExtra("list");
            dataList.addAll(rList);
            mAdapter.notifyDataSetChanged();
        } else if (requestCode == 12 && data != null) {
            String bean = data.getStringExtra("bean");
            edDiseaseName.setText(bean);
        }
    }

    /**
     * 检查list里面输入是否完整
     */
    public boolean check() {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).cate_id <= 0) {
                return false;
            }
            if (dataList.get(i).num <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 发送健康处方
     */
    @SuppressLint("CheckResult")
    public void recipeSend() {
        RecipeSendBody body = new RecipeSendBody();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(getActivity()));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("user_id", 1);
        map.put("id", 0);
        map.put("name", edDiseaseName.getText().toString());
        map.put("content", edNote.getText().toString());
        map.put("user_name", edPatientName.getText().toString());
        map.put("age", edAge.getText().toString());
        map.put("sex", edSex.getText().toString());
        body.token = UserManager.getToken(getActivity());
        body.appKey = "android";
        body.time = String.valueOf(System.currentTimeMillis() / 1000);
        body.user_id = 1;
        body.id = 0;
        body.name = edDiseaseName.getText().toString();
        body.content = edNote.getText().toString();
        body.user_name = edPatientName.getText().toString();
        body.age = Integer.parseInt(edAge.getText().toString());
        body.sex = edSex.getText().toString();
        List<RecipeSendBody.Goods> lis = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            RecipeSendBody.Goods goods = new RecipeSendBody.Goods();
            GoodsGetlistBean.ListBean listBean = dataList.get(i);
            goods.name = listBean.name;
            goods.goods_id = String.valueOf(listBean.cate_id);
//            goods.goods_id ="1";
            goods.num = listBean.num;
            lis.add(goods);
        }
        map.put("goods", lis);
        body.sign = SignUtil.getSign(map);
        body.goods = lis;

        String s = GsonUtil.toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().recipeSend(requestBody)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString bean) {
                        dialog = CustomDialog.sendPrescriptionSucces(context, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        FragmentUtil.popBackStack(getFragmentManager());
                    }
                });

    }

}
