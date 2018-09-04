package com.flym.yh.data.model;

import java.util.List;

public class GoodsGetcatelistBean {
        /**
         * data : /yihuan/api/goods/getCatelist
         * list : [{"id":1,"parent_id":0,"name":"兰蔻","_child":[{"id":9,"parent_id":1,"name":"祛痘"},{"id":8,"parent_id":1,"name":"控油"},{"id":7,"parent_id":1,"name":"混合型肌肤"},{"id":6,"parent_id":1,"name":"抗皱"},{"id":5,"parent_id":1,"name":"美白嫩肤"},{"id":4,"parent_id":1,"name":"干性肌肤"},{"id":3,"parent_id":1,"name":"保湿补水"},{"id":2,"parent_id":1,"name":"油性肌肤"}]},{"id":10,"parent_id":0,"name":"欧莱雅","_child":[{"id":15,"parent_id":10,"name":"抗皱"},{"id":14,"parent_id":10,"name":"美白嫩肤"},{"id":13,"parent_id":10,"name":"干性肌肤"},{"id":12,"parent_id":10,"name":"保湿补水"},{"id":11,"parent_id":10,"name":"油性肌肤"},{"id":16,"parent_id":10,"name":"混合型肌肤"}]}]
         */

        public String data;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 1
             * parent_id : 0
             * name : 兰蔻
             * _child : [{"id":9,"parent_id":1,"name":"祛痘"},{"id":8,"parent_id":1,"name":"控油"},{"id":7,"parent_id":1,"name":"混合型肌肤"},{"id":6,"parent_id":1,"name":"抗皱"},{"id":5,"parent_id":1,"name":"美白嫩肤"},{"id":4,"parent_id":1,"name":"干性肌肤"},{"id":3,"parent_id":1,"name":"保湿补水"},{"id":2,"parent_id":1,"name":"油性肌肤"}]
             */

            public int id;
            public int parent_id;
            public String name;
            public List<ChildBean> _child;

            public static class ChildBean {
                /**
                 * id : 9
                 * parent_id : 1
                 * name : 祛痘
                 */

                public String id;
                public int parent_id;
                public String name;
            }
        }

}
