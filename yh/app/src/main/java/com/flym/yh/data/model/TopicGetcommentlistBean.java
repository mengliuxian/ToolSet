package com.flym.yh.data.model;

import java.util.List;

/**
 * Created by mengl on 2018/3/28.
 */

public class TopicGetcommentlistBean {

    /**
     * data : /yihuan/api/topic/getCommentlist
     * list : [{"id":1,"centent":"好好","doctor_id":1,"create_time":"2018-03-27 11:34:07","nickname":null,"headimgurl":null}]
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * centent : 好好
         * doctor_id : 1
         * create_time : 2018-03-27 11:34:07
         * nickname : null
         * headimgurl : null
         */

        public int id;
        public String content;
        public int doctor_id;
        public String create_time;
        public String nickname;
        public String headimgurl;

    }
}
