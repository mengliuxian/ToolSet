package com.flym.yh.data.model;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/30
 */

public class GetPhoneLongTimeBean {

    /**
     * data : /yihuan/api/app/getCalltimelist
     * list : [{"id":21,"parent_id":3,"name":"45分钟"},{"id":20,"parent_id":3,"name":"25分钟"},{"id":19,"parent_id":3,"name":"10分钟"}]
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
         * id : 21
         * parent_id : 3
         * name : 45分钟
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
