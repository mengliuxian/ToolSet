package com.flym.yh.data.model;

/**
 * Created by mengl on 2018/3/28.
 */

public class TopicGetdetailBean {


    /**
     * data : /yihuan/api/topic/getDetail
     * info : {"id":1,"title":"论坛标题","content":"论坛内容论坛内容论坛内容论坛内容","comments":1,"create_time":"2018-03-27 11:32:20","nickname":"filbuk","headimgurl":null}
     */

    public String data;
    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * title : 论坛标题
         * content : 论坛内容论坛内容论坛内容论坛内容
         * comments : 1
         * create_time : 2018-03-27 11:32:20
         * nickname : filbuk
         * headimgurl : null
         */

        public int id;
        public String title;
        public String content;
        public int comments;
        public String create_time;
        public String nickname;
        public String headimgurl;
    }

}
