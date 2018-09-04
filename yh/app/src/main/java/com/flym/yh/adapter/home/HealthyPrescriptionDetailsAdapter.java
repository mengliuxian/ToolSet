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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.DrugDirectoryBean;
import com.flym.yh.data.model.GoodsGetlistBean;
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
 * @date 2018/3/26
 * 商品清单 健康处方详情 adapter
 */

public class HealthyPrescriptionDetailsAdapter extends BaseQuickAdapter<GoodsGetlistBean.ListBean, BaseViewHolder> {
    private PopupWindow selectDN;
    List<DrugDirectoryBean.ListBean> list = null;
    private List<GoodsGetlistBean.ListBean> dataList;
    private boolean isCheck = false;

    public HealthyPrescriptionDetailsAdapter(@Nullable List<GoodsGetlistBean.ListBean> data) {
        super(R.layout.item_product_list_details, data);
        dataList = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodsGetlistBean.ListBean item) {
        helper.setIsRecyclable(false);

        helper.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(item);
                notifyDataSetChanged();
            }
        });
        list = new ArrayList<>();
        final EditText edName = helper.getView(R.id.ed_name);
        final EditText edNum = helper.getView(R.id.ed_num);
//        edName.setHint("输入药物名称");
//        edNum.setText("");
        if (edNum.getTag() instanceof TextWatcher) {
            edNum.removeTextChangedListener((TextWatcher) edNum.getTag());
        }
        if (edName.getTag() instanceof TextWatcher) {
            edName.removeTextChangedListener((TextWatcher) edName.getTag());
        }
        if (!TextUtils.isEmpty(item.name)) {
            helper.setText(R.id.ed_name, item.name);
        }
        if (item.num != 0) {
            helper.setText(R.id.ed_num, String.valueOf(item.num));
        }


        TextWatcherWrapper textName = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isCheck) {
                    if (TextUtils.isEmpty(s)) {
                        //showHistory();
                        if (selectDN != null) {
                            selectDN.dismiss();
                        }
                        item.name = "";
                        item.cate_id = 0;
                    } else {
                        getList(edName.getText().toString().trim(), edName, item,1);
                        isCheck = true;
                    }
                }
            }
        };


        TextWatcherWrapper textNum = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals("")) {
                    item.num = Integer.valueOf(s.toString().trim());
                    edNum.setBackgroundResource(R.drawable.shape_text_white);
                } else {
                    item.num = 0;
                    edNum.setBackgroundResource(R.drawable.shape_text_red);
                }
            }
        };


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
        edName.addTextChangedListener(textName);
        edName.setTag(textName);

    }

    public void dissPop() {
        if (selectDN != null) {
            selectDN.dismiss();
            selectDN = null;
        }
    }

    /**
     * 获取药物列表
     */

    @SuppressLint("CheckResult")
    public void getList(final String name, final EditText v, final GoodsGetlistBean.ListBean item, final int page) {
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
                            v.setText("");
                            ToastUtil.showMessage(mContext, "药品不存在");
                            isCheck = false;
                            return;
                        }
                        list.addAll(bean.getList());
                        selectDrugName(mContext, v, item,page,name);
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
    public void selectDrugName(final Context context, final EditText vtiew, final GoodsGetlistBean.ListBean item,
                               final int page ,final String name) {
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
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isCheck = true;
                vtiew.setText(list.get(position).getName());
                vtiew.setBackgroundResource(R.drawable.shape_text_white);
                item.name = list.get(position).getName();
                item.cate_id = list.get(position).getCate_id();
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
}
