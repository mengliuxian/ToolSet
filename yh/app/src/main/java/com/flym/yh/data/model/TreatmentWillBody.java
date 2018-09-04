package com.flym.yh.data.model;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/4/20
 */
public class TreatmentWillBody extends BaseBody {

    public String name;
    public int user_id;
    public List<Goods> goods;


    public static class Goods {
        public String name;
        public String remark;
        public String medicine_id;
        public int num;

    }
}
