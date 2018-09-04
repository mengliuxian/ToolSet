package com.flym.yh.data.model;

/**
 * Author:mengl
 * Time:2018/5/7
 */
public class DocGetDetailBean {


        /**
         * data : /yihuan/api/doctoritems/getDetail
         * info : {"id":33,"title":"测试标题","image":"/public/uploads/20180507/6df1b99701068fbaffee8ec5e5b73f39.png","summary":"测试剧透","content":"测试详情","file_id":"http://wenzhenzaixian.oss-cn-beijing.aliyuncs.com/Aideo/2018-05-07/1525673137.mp3","price":"120.00","create_time":"2018-05-07 14:09:42","doctor_id":7,"type_name":"名医讲堂","type_content":null}
         */

        public String data;
        public InfoBean info;

        public static class InfoBean {
            /**
             * id : 33
             * title : 测试标题
             * image : /public/uploads/20180507/6df1b99701068fbaffee8ec5e5b73f39.png
             * summary : 测试剧透
             * content : 测试详情
             * file_id : http://wenzhenzaixian.oss-cn-beijing.aliyuncs.com/Aideo/2018-05-07/1525673137.mp3
             * price : 120.00
             * create_time : 2018-05-07 14:09:42
             * doctor_id : 7
             * type_name : 名医讲堂
             * type_content : null
             */
            public int id;
            public String title;
            public String image;
            public String summary;
            public String content;
            public String file_id;
            public String price;
            public String create_time;
            public int doctor_id;
            public String type_name;
            public Object type_content;
        }

}
