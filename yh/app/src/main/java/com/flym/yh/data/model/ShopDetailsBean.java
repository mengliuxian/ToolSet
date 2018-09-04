package com.flym.yh.data.model;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/4/19
 */
public class ShopDetailsBean {
        public String data;
        public InfoBean info;

        public static class InfoBean {

            public int id;
            public String name;
            public String image;
            public String content;
            public String price;
            public String unit;
            public int sales;
            public int cate_id;
            public List<String> image_list;
        }
}
