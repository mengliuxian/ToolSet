package com.flym.fhzk.adapter.mine;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class CollectAdapter extends BaseQuickAdapter<GoodsBean.GoodsInfoBean, BaseViewHolder> {


    public CollectAdapter(@Nullable List<GoodsBean.GoodsInfoBean> data) {
        super(R.layout.item_collect, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.GoodsInfoBean item) {
        helper.setText(R.id.goods_name, item.name);
        GlideUtil.loadImage(mContext, item.pic, (ImageView) helper.getView(R.id.goods_logo));
        helper.setText(R.id.vouchers, "券￥" + String.valueOf(item.coupon));
        helper.setText(R.id.money, item.couponPrice);
        TextView view = (TextView) helper.getView(R.id.old_money);
        view.setText("￥" + item.price);
        //中划线
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.time, "有效期" + item.couponTime);

        helper.addOnClickListener(R.id.remove);
        helper.addOnClickListener(R.id.relative_layout);
    }
}
