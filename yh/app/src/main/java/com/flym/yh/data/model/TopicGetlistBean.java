package com.flym.yh.data.model;

import java.util.List;

/**
 * Created by mengl on 2018/3/28.
 */

public class TopicGetlistBean {

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

        public int id;
        public String title;
        public String content;
        public String comments;
        public String create_time;
        public String nickname;
        public String headimgurl;
        public boolean vis;
    }

}
