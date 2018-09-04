package com.flym.yh.data.model;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class QuicklyFindMedicineBean {

    /**
     * data : /yihuan/api/medicine/getCatelist
     * list : [{"id":1,"parent_id":0,"name":"神经用药","_child":[{"id":2,"parent_id":1,"name":"抑郁症"},{"id":3,"parent_id":1,"name":"癫痫"},{"id":4,"parent_id":1,"name":"精神分裂症"}]}]
     */

    private String data;
    private List<ListBean> list;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * parent_id : 0
         * name : 神经用药
         * _child : [{"id":2,"parent_id":1,"name":"抑郁症"},{"id":3,"parent_id":1,"name":"癫痫"},{"id":4,"parent_id":1,"name":"精神分裂症"}]
         */

        private int id;
        private int parent_id;
        private String name;
        private List<ChildBean> _child;
        private boolean isShow;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildBean> get_child() {
            return _child;
        }

        public void set_child(List<ChildBean> _child) {
            this._child = _child;
        }

        public static class ChildBean {
            /**
             * id : 2
             * parent_id : 1
             * name : 抑郁症
             */

            private int id;
            private int parent_id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
