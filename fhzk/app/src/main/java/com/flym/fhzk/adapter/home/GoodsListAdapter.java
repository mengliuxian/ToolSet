package com.flym.fhzk.adapter.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.ui.activity.goods.TbWebViewActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class GoodsListAdapter extends BaseQuickAdapter<GoodsBean.GoodsInfoBean, BaseViewHolder> {
    public GoodsListAdapter(@Nullable List<GoodsBean.GoodsInfoBean> data) {
        super(R.layout.item_goods_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsBean.GoodsInfoBean item) {
        GlideUtil.loadImage(mContext, item.pic, (ImageView) helper.getView(R.id.logo));
        TextView textView = (TextView) helper.getView(R.id.mark);
        switch (item.isTm) {
            case 0:
                textView.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textView.setText("天猫");
                break;
            default:
                break;
        }
        helper.setText(R.id.goods_name, item.name);
        helper.setText(R.id.money, item.couponPrice);
        helper.setText(R.id.vouchers_return, "券￥" + String.valueOf(item.coupon));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TbWebViewActivity.class);
                if (!TextUtils.isEmpty(item.couponUrl)) {
                    intent.putExtra("url", item.couponUrl);
                }
                intent.putExtra("goodsId", item.goodsId);
                ActivityManager.getInstance().startNextActivity(intent);
            }
        });
    }
}
