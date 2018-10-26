package com.flym.fhzk.adapter.actin;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class RankListAdapter extends BaseQuickAdapter<GoodsBean.GoodsInfoBean, BaseViewHolder> {
    public RankListAdapter(@Nullable List<GoodsBean.GoodsInfoBean> data) {
        super(R.layout.item_rank_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.GoodsInfoBean item) {
        helper.setVisible(R.id.rank_image_view, false);
        int position = helper.getPosition();
        switch (position) {
            default:
                break;
            case 0:
                helper.setVisible(R.id.rank_image_view, true);
                GlideUtil.loadImage(mContext, R.drawable.ic_rank_one, (ImageView) helper.getView(R.id.rank_image_view));
                break;
            case 1:
                helper.setVisible(R.id.rank_image_view, true);
                GlideUtil.loadImage(mContext, R.drawable.ic_rank_two, (ImageView) helper.getView(R.id.rank_image_view));
                break;
            case 2:
                helper.setVisible(R.id.rank_image_view, true);
                GlideUtil.loadImage(mContext, R.drawable.ic_rank_three, (ImageView) helper.getView(R.id.rank_image_view));
                break;
        }
        GlideUtil.loadImage(mContext, item.pic, (ImageView) helper.getView(R.id.goods_logo));
        helper.setText(R.id.goods_name, item.name);
        helper.setText(R.id.goods_money, item.price);
        helper.setText(R.id.sales, "已售：" + String.valueOf(item.sales));
    }
}
