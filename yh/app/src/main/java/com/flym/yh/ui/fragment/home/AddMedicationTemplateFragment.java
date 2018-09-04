package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//import com.hyphenate.easeui.domain.AddMedicationTemplate;
import com.flym.yh.data.model.AddMedicationTemplate;
import com.flym.yh.R;
import com.flym.yh.adapter.home.DrugListAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.AddMedicationTempateRe;
import com.flym.yh.data.model.MedicationTemplateBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.RecycleViewDivider;
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
 * @date 2018/3/23
 * 添加用药模板  编辑用药模板
 */

public class AddMedicationTemplateFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private DrugListAdapter mAdapter;
    private List<AddMedicationTemplate> list = new ArrayList<>();
    private MedicationTemplateBean.ListBean listBean;


    public static AddMedicationTemplateFragment newInstance(MedicationTemplateBean.ListBean listBean) {

        Bundle args = new Bundle();
        args.putParcelable("bean", listBean);
        AddMedicationTemplateFragment fragment = new AddMedicationTemplateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (list.size() != 0) {
            list.clear();
        }
        list.add(new AddMedicationTemplate());
        setAdapter();
        if (listBean != null) {
            setData();
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new DrugListAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        recycler.addItemDecoration(new RecycleViewDivider(getActivity(), 1, 20, getActivity().getResources().getColor(R.color.gray_d9)));

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listBean = getArguments().getParcelable("bean");

    }

    private void setData() {
        edTitle.setText(listBean.getName());
        if (listBean.getGoods_list() != null) {
            for (int i = 0; i < listBean.getGoods_list().size(); i++) {
                AddMedicationTemplate b = new AddMedicationTemplate();
                MedicationTemplateBean.ListBean.GoodsListBean goodsListBean = listBean.getGoods_list().get(i);
                b.setName(goodsListBean.getName());
                b.setRemark(goodsListBean.getRemark());
                b.setMum(goodsListBean.getNum());
                b.setMedicine_id(goodsListBean.getMedicine_id());
                b.price = goodsListBean.getPrice();
                list.add(b);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
//        if (getTag().equals(getString(R.string.new_))) {
//            tv.setText(getString(R.string.save));
//        } else {
//            tv.setText(getString(R.string.complete));
////            ed.setText(getArguments().getString("diseaseName"));
//        }
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_medication_template;
    }

    @OnClick({R.id.tv_complete, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_complete:
                if (edTitle.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入标题");
                    return;
                }
                if (!check()) {
                    ToastUtil.showMessage(getActivity(), "请把信息输入完整");
                    return;
                }
                save();
                break;
            case R.id.tv_add:
                mAdapter.dissPop();
                list.add(new AddMedicationTemplate());
                mAdapter.notifyItemChanged(list.size() - 1);
                break;
            default:
                break;
        }
    }

    /**
     * 检查list里面输入是否完整
     */
    public boolean check() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMedicine_id() <= 0) {
                return false;
            }
            if (list.get(i).getMum() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 保存用药模板
     */
    public void save() {

        AddMedicationTempateRe add = new AddMedicationTempateRe();
        Map<String, Object> map = new HashMap<>();
        if (listBean != null) {

            map.put("id", listBean.getId());
            add.id = listBean.getId();
        }
        map.put("token", UserManager.getToken(ActivityManager.getInstance().currentActivity()));
        map.put("name", edTitle.getText().toString().trim());
        map.put("goods", list);
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));

        add.setGoods(list);
        add.setName(edTitle.getText().toString().trim());
        add.setToken(UserManager.getToken(ActivityManager.getInstance().currentActivity()));
        add.setAppKey("android");
        add.setTime(String.valueOf(System.currentTimeMillis() / 1000));
        add.setSign(SignUtil.getSign(map));


        String s = GsonUtil.toJson(add);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().saveMedicationTemplate(requestBody)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object bean) {
                        FragmentUtil.popBackStack(getFragmentManager(), getTag());
                    }
                });
    }

}
