package com.flym.yh.data.model;

import java.util.List;

public class GetServicelistBean  {
        /**
         * data : /yihuan/api/app/getServicelist
         * list : [{"id":18,"parent_id":2,"name":"静脉采血"},{"id":16,"parent_id":2,"name":"打针"},{"id":17,"parent_id":2,"name":"输液"}]
         */

        public String data;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 18
             * parent_id : 2
             * name : 静脉采血
             */

            public int id;
            public int parent_id;
            public String name;
        }

}
