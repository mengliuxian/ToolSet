package com.flym.fhzk.data.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class TopsBean {

        public List<TopsInfoBean> topsInfo;

        public static class TopsInfoBean {
            /**
             * id : 1
             * name : 诺诺童装 秋冬保暖内衣套装 儿童纯棉中厚款贴身舒适打底家居套装
             * coupon : 0
             * rebate : 0
             * price : 0.00
             * couponPrice : 0.00
             * sales : 100
             * pic :
             * goodsId : 2147483647
             * goodsUrl :
             * isTm : 0
             */

            public int id;
            public String name;
            public int coupon;
            public int rebate;
            public String price;
            public String couponPrice;
            public int sales;
            public String pic;
            public int goodsId;
            public String goodsUrl;
            public int isTm;
        }

}
