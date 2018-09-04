package com.flym.yh.adapter.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
//import com.hyphenate.easeui.domain.AddMedicationTemplate;
import com.flym.yh.data.model.AddMedicationTemplate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.DrugDirectoryBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.TextWatcherWrapper;
import com.flym.yh.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 药物清单 adapter
 */

public class DrugListAdapter extends BaseQuickAdapter<AddMedicationTemplate, BaseViewHolder> {
    List<DrugDirectoryBean.ListBean> list = new ArrayList<>();
    private PopupWindow selectDN;
    private boolean isCheck = false;
    List<AddMedicationTemplate> dataList;

    public DrugListAdapter(@Nullable List<AddMedicationTemplate> data) {
        super(R.layout.item_drug_list, data);
        this.dataList = data;
    }

    public void dissPop() {
        if (selectDN != null) {
            selectDN.dismiss();
            selectDN = null;
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, final AddMedicationTemplate item) {
        //直接禁止复用
        helper.setIsRecyclable(false);
        list = new ArrayList<>();
        final EditText edName = helper.getView(R.id.ed_name);
        final EditText edNum = helper.getView(R.id.ed_num);
        EditText edRemark = helper.getView(R.id.ed_remark);

        helper.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(item);
                notifyDataSetChanged();
                if (lis != null) {
                    lis.onCalculatePrice();
                }
            }
        });

        final TextWatcherWrapper textName = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isCheck) {
                    if (TextUtils.isEmpty(s)) {
                        //showHistory();
                        if (selectDN != null) {
                            selectDN.dismiss();
                        }
                        item.setName("");
                        item.setMedicine_id(0);
                        edName.setBackgroundResource(R.drawable.shape_text_white);
                    } else {
                        edName.setBackgroundResource(R.drawable.shape_text_red);
                        getList(edName.getText().toString().trim(), edName, item, 1);
                        isCheck = true;
                    }
                }
            }
        };
        if (edName.getTag() instanceof TextWatcher) {
            edName.removeTextChangedListener((TextWatcher) edName.getTag());
        }


        TextWatcherWrapper textNum = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    item.setMum(Integer.parseInt(s.toString().trim()));
                    edNum.setBackgroundResource(R.drawable.shape_text_white);
                    if (lis != null) {
                        lis.onCalculatePrice();
                    }
                } else {
                    item.setMum(0);
                    edNum.setBackgroundResource(R.drawable.shape_text_red);
                }
            }
        };

        if (edNum.getTag() instanceof TextWatcher) {
            edNum.removeTextChangedListener((TextWatcher) edNum.getTag());
        }


        TextWatcherWrapper textRemark = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setRemark(s.toString().trim());
            }
        };
        if (edRemark.getTag() instanceof TextWatcher) {
            edRemark.removeTextChangedListener((TextWatcher) edRemark.getTag());
        }

        if (!TextUtils.isEmpty(item.getName()))
        {
            edName.setBackgroundResource(R.drawable.shape_text_white);
        }else {
            edName.setBackgroundResource(R.drawable.shape_text_red);
        }
        helper.setText(R.id.ed_name, (null != item.getName()) ? item.getName() : "")
                .setText(R.id.ed_remark, (null != item.getRemark()) ? item.getRemark() : "");
        if (item.getMum() != 0) {
            helper.setText(R.id.ed_num, String.valueOf(item.getMum()));
            edNum.setBackgroundResource(R.drawable.shape_text_white);
        } else {
            edNum.setBackgroundResource(R.drawable.shape_text_red);
        }

        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (selectDN != null) {
                        dissPop();
                    }
                } else {
                    isCheck = false;
                }
            }
        };
        edName.setOnFocusChangeListener(listener);


        edNum.addTextChangedListener(textNum);
        edNum.setTag(textNum);
        edRemark.addTextChangedListener(textRemark);
        edRemark.setTag(textRemark);
        edName.addTextChangedListener(textName);
        edName.setTag(textName);
        helper.setText(R.id.tv_position, (helper.getAdapterPosition() + 1) + "");
    }


    /**
     * 获取药物列表
     */
    @SuppressLint("CheckResult")
    public void getList(final String name, final EditText v, final AddMedicationTemplate item, final int page) {
        RetrofitUtil.getApiService().getDrugDirectory(name, page, 0)
                .compose(new SimpleTransFormer<DrugDirectoryBean>(DrugDirectoryBean.class))
                .subscribeWith(new DisposableWrapper<DrugDirectoryBean>() {
                    @Override
                    public void onNext(DrugDirectoryBean bean) {
                        if (null != list && list.size() != 0) {
                            list.clear();
                        }
                        if (selectDN != null && selectDN.isShowing()) {
                            selectDN.dismiss();
                        }
                        if (bean.getList() == null || bean.getList().size() == 0) {
                            ToastUtil.showMessage(mContext, "药品不存在");
                            v.setText("");
                        }
                        list.addAll(bean.getList());
                        selectDrugName(mContext, v, item, page, name);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtil.showMessage(mContext, "稍后再试");
                        v.setText("");
                    }
                });
    }

    /**
     * 选择药品名称
     */
    public void selectDrugName(final Context context, final EditText vtiew, final AddMedicationTemplate item,
                               final int page, final String name) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_select_drug_name, null);
        selectDN = new PopupWindow(view.findViewById(R.id.pop_layout), vtiew.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        selectDN.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        SpringView springView = (SpringView) view.findViewById(R.id.spring);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadmore() {
                int pages = page + 1;
                getList(name, vtiew, item, pages);
            }
        });
        final SelectDrugNameAdapter mAdapter = new SelectDrugNameAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isCheck = true;
                vtiew.setText(list.get(position).getName());
                vtiew.setBackgroundResource(R.drawable.shape_text_white);
                item.setName(list.get(position).getName());
                item.setMedicine_id(list.get(position).getId());
                item.price = list.get(position).getPrice();
                if (lis != null) {
                    lis.onCalculatePrice();
                }
                selectDN.dismiss();
                isCheck = false;
            }
        });
        selectDN.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                list.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
        isCheck = false;
        selectDN.setFocusable(false);
        selectDN.showAsDropDown(vtiew);
    }

    private CalculatePrice lis;

    public void setLis(CalculatePrice lis) {
        this.lis = lis;
    }

    /**
     * 计算金额的回调
     */
    public interface CalculatePrice {
        void onCalculatePrice();
    }

}
