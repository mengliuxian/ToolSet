package com.flym.yh.data.model;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/4/13
 */
public class FastReplyBean {

        /**
         * data : /yihuan/api/shortcut/getList
         * list : [{"id":3,"content":"how are you"},{"id":2,"content":"Asasdjajas大撒娇啊萨说萨萨阿双卡双待是萨是萨苏打水飒飒达萨萨顶顶程度的 vv说的都是颠三倒四疯疯癫癫风格复古风的官方的呃呃呃而为巍峨沃尔沃巍峨儿"}]
         */

        public String data;

        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 3
             * content : how are you
             */

            public int id;
            public boolean show;
            public String content;
        }

}

