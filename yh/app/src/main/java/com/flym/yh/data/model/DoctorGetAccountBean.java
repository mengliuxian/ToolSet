package com.flym.yh.data.model;

import java.util.List;

/**
 * Created by mengl on 2018/3/28.
 */

public class DoctorGetAccountBean {

    /**
     * data : /yihuan/api/topic/getList
     * list : [{"id":1,"title":"论坛标题","content":"论坛内容论坛内容论坛内容论坛内容","comments":0,"create_time":"2018-03-27 11:32:20","nickname":null,"headimgurl":null}]
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * title : 论坛标题
         * content : 论坛内容论坛内容论坛内容论坛内容
         * comments : 0
         * create_time : 2018-03-27 11:32:20
         * nickname : null
         * headimgurl : null
         */

        public String amount;
        public String fee;
        public  int type;
        public int remark;
        public String create_time;
    }

}
