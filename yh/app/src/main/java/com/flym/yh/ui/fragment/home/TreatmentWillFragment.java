package com.flym.yh.ui.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.flym.yh.adapter.home.DrugListAdapter;
import com.flym.yh.base.BaseFragment;
//import com.hyphenate.easeui.domain.AddMedicationTemplate;
import com.flym.yh.data.model.AddMedicationTemplate;
import com.flym.yh.data.model.DrugDirectoryBean;
import com.flym.yh.data.model.MedicationTemplateBean;
import com.flym.yh.data.model.TreatmentWillBody;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.DiagnosticTemplateActivity;
import com.flym.yh.ui.activity.home.DrugDirectoryActivity;
import com.flym.yh.ui.activity.home.MedicationTemplateActivity;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.SignUtil;
import com.flym.yh.utils.ToastUtil;
import com.flym.yh.utils.UserManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 治疗建议
 */
public class TreatmentWillFragment extends BaseFragment implements DrugListAdapter.CalculatePrice {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_import_template)
    TextView tvImportTemplate;
    @BindView(R.id.ed_disease_name)
    EditText edDiseaseName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    private final int TEMPLATE = 1;
    private final int DIRECTORY = 2;
    private final int TEMPLATES = 3;
    private DrugListAdapter adapter;
    private ArrayList<AddMedicationTemplate> dataList = new ArrayList<>();

    public static TreatmentWillFragment newInstance() {

        Bundle args = new Bundle();

        TreatmentWillFragment fragment = new TreatmentWillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.treatment));
        return toolbar;
    }

    @Override
    protected void initData() {
        dataList.add(new AddMedicationTemplate());
        dataList.add(new AddMedicationTemplate());
        adapter = new DrugListAdapter(dataList);
        adapter.setLis(this);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_treatment_will;
    }

    @OnClick({R.id.tv_from_medication_template, R.id.tv_import_template,
            R.id.tv_from_drug_directory, R.id.tv_add, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_from_medication_template:
                //用药模板
                ActivityManager.getInstance().startNextActivityForResult(new Intent(context,
                        MedicationTemplateActivity.class).putExtra("type", 1), TEMPLATE);

                break;
            case R.id.tv_from_drug_directory:
                //药物目录
                ActivityManager.getInstance().startNextActivityForResult(new Intent(context,
                        DrugDirectoryActivity.class), DIRECTORY);

                break;
            case R.id.tv_import_template:
                //诊断模板
                ActivityManager.getInstance().startNextActivityForResult(new Intent(context,
                        DiagnosticTemplateActivity.class).putExtra("type", 1), TEMPLATES);

                break;
            case R.id.tv_add:
                adapter.dissPop();
                dataList.add(new AddMedicationTemplate());
                adapter.notifyItemChanged(dataList.size() - 1);

                break;
            case R.id.tv_send:
                if (edDiseaseName.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入疾病名称");
                    return;
                }
                if (!check()) {
                    ToastUtil.showMessage(getActivity(), "请把信息输入完整");
                    return;
                }
                therapySend();
                break;
        }
    }

    /**
     * 检查list里面输入是否完整
     */
    public boolean check() {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getMedicine_id() <= 0) {
                return false;
            }
            if (dataList.get(i).getMum() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == TEMPLATE) {
                MedicationTemplateBean.ListBean bean = data.getParcelableExtra("bean");
                if (bean != null && bean.getGoods_list() != null) {
                    List<MedicationTemplateBean.ListBean.GoodsListBean> goods_list = bean.getGoods_list();
                    for (int i = 0; i < goods_list.size(); i++) {
                        AddMedicationTemplate addMedicationTemplate = new AddMedicationTemplate();
                        MedicationTemplateBean.ListBean.GoodsListBean goodsListBean = goods_list.get(i);
                        addMedicationTemplate.setName(goodsListBean.getName());
                        addMedicationTemplate.setMum(goodsListBean.getNum());
                        addMedicationTemplate.price = goodsListBean.getPrice();
                        addMedicationTemplate.setMedicine_id(goodsListBean.getMedicine_id());
                        addMedicationTemplate.setRemark(goodsListBean.getRemark());
                        dataList.add(addMedicationTemplate);
                    }

                }
            } else if (requestCode == DIRECTORY) {
                ArrayList<DrugDirectoryBean.ListBean> list = data.getParcelableArrayListExtra("list");
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        AddMedicationTemplate addMedicationTemplate = new AddMedicationTemplate();
                        DrugDirectoryBean.ListBean listBean = list.get(i);
                        addMedicationTemplate.setName(listBean.getName());
                        addMedicationTemplate.price = listBean.getPrice();
                        addMedicationTemplate.setMedicine_id(listBean.getCate_id());
                        dataList.add(addMedicationTemplate);
                    }
                }
            } else if (requestCode == TEMPLATES) {
                String bean = data.getStringExtra("bean");
                edDiseaseName.setText(bean);
            }
            adapter.notifyDataSetChanged();
            onCalculatePrice();
        }
    }

    /**
     * 药品金额
     */
    @Override
    public void onCalculatePrice() {
        if (dataList.size() > 0) {
            float pr = 0;
            for (int i = 0; i < dataList.size(); i++) {
                AddMedicationTemplate addMedicationTemplate = dataList.get(i);
                if (TextUtils.isEmpty(addMedicationTemplate.price)) {
                    continue;
                }
                float p = Float.parseFloat(addMedicationTemplate.price);
                if (addMedicationTemplate.getMum() > 0) {
                    pr += p * addMedicationTemplate.getMum();
                }

            }
            if (pr == 0) {
                return;
            }
            tvMoney.setText(new DecimalFormat("0.00").format(pr));
        }
    }


    /**
     * 发送治疗建议
     */
    @SuppressLint("CheckResult")
    public void therapySend() {
        TreatmentWillBody body = new TreatmentWillBody();
        List<TreatmentWillBody.Goods> goodsList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(context));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("name", edDiseaseName.getText().toString());
        map.put("user_id", 0);
        for (int i = 0; i < dataList.size(); i++) {
            AddMedicationTemplate addMedicationTemplate = dataList.get(i);
            TreatmentWillBody.Goods goods = new TreatmentWillBody.Goods();
            goods.name = addMedicationTemplate.getName();
            goods.num = addMedicationTemplate.getMum();
            goods.medicine_id = String.valueOf(addMedicationTemplate.getMedicine_id());
            goods.remark = addMedicationTemplate.getRemark();
            goodsList.add(goods);
        }

        map.put("goods", goodsList);

        body.token = UserManager.getToken(context);
        body.appKey = "android";
        body.time = String.valueOf(System.currentTimeMillis() / 1000);
        body.name = edDiseaseName.getText().toString();
        body.user_id = 0;
        body.goods = goodsList;
        body.sign = SignUtil.getSign(map);


        String s = GsonUtil.toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().therapySend(requestBody)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString o) {

                        //返回聊天
                        StringBuilder builder = new StringBuilder();
                        builder.append(getString(R.string.treatment_8194));
                        builder.append("\n");
                        builder.append(edDiseaseName.getText().toString());
                        builder.append("\n");
                        builder.append("药物清单");
                        builder.append("\n");
                        for (int i = 0; i < dataList.size(); i++) {
                            AddMedicationTemplate bean = dataList.get(i);
                            builder.append(i + 1 + "  ").append(bean.getName()).append("\n");
                            builder.append("x").append(bean.getMum()).append("盒").append("\n");
                            if (!TextUtils.isEmpty(bean.getRemark())) {
                                builder.append("用法:").append(bean.getRemark()).append("\n");
                            }
                        }
                        builder.append("(请联系客服中心  帮忙代购)");
                        builder.append("\n");
                        builder.append(tvMoney.getText().toString());
                        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        builder.append(getString(R.string.empty_8194));
                        builder.append(format);

                        Intent intent = new Intent();
                        intent.putExtra("list", builder.toString());
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        ActivityManager.getInstance().popActivity(getActivity());
                    }
                });

    }


}
