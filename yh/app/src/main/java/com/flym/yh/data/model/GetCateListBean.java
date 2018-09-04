package com.flym.yh.data.model;

import java.util.List;

/**
 * Created by mengl on 2018/3/28.
 */

public class GetCateListBean {


    /**
     * data : /yihuan/api/article/getCatelist
     * list : [{"id":22,"name":"医学资讯","parent_id":4},{"id":23,"name":"医学护肤","parent_id":4},{"id":24,"name":"中医调养","parent_id":4},{"id":25,"name":"中医调养","parent_id":4},{"id":26,"name":"妇幼育儿","parent_id":4},{"id":27,"name":"性健康","parent_id":4}]
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 22
         * name : 医学资讯
         * parent_id : 4
         */

        public int id;
        public String name;
        public int parent_id;
        public boolean isShow;
    }

}
