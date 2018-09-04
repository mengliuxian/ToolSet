package com.flym.yh.data.model;

import java.util.List;

public class RecipeSendBody extends BaseBody {

    public int user_id;
    public int id;
    public String name;
    public String content;
    public String user_name;
    public int age;
    public String sex;
    public List<Goods> goods;

    public static class Goods {
        public String name;
        public int num;
        public String goods_id;
    }
}
