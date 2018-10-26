package com.flym.fhzk.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.ClassifyPopAdapter;
import com.flym.fhzk.base.BasePopupWindow;
import com.flym.fhzk.data.model.ClassifyBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class ClassifyPop extends BasePopupWindow implements View.OnClickListener {
    private ArrayList<ClassifyBean.SkuInfoBean> list = new ArrayList<>();
    private DataReturnListene dataReturnListene;
    private final ClassifyPopAdapter adapter;

    public ClassifyPop(Context context) {
        super(context, R.layout.pop_classify);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.close);
        imageView.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        adapter = new ClassifyPopAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dataReturnListene.dataReturn(position);
                dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    public void setDataReturnListene(DataReturnListene dataReturnListene) {
        this.dataReturnListene = dataReturnListene;
    }

    public interface DataReturnListene {
        void dataReturn(int position);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        setWindowAlpha(0.4f);

    }

    public void setList(ArrayList<ClassifyBean.SkuInfoBean> skuInfoBean) {
        this.list.clear();
        this.list.addAll(skuInfoBean);
        adapter.notifyDataSetChanged();
    }
}
