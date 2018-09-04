package com.flym.yh.data.model;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/28
 */

public class GetDoctorArtivleListBean {

    /**
     * data : /yihuan/api/article/getMylist
     * list : [{"id":4,"title":"df","image":"/public/uploads/20180328/b4b02c164009ad9634ec2388d1133068.jpg","content":"ncivmckclm付聚聚女超级IC卡吃米线快发快发v考查课上扣扣瞅瞅没穿长裤开学考试没接受奥vi冯卡具i放开我女iv辛苦内v覅科目三聚大姐夫男女继续看少女级想看电视v看看怎么在空间覅v开什么车距i时快时慢陈几点开非买不可只看到距i分开过今晚继续结果i","create_time":"2018-03-28 15:13:14"},{"id":3,"title":"标题","image":"/public/uploads/20180328/20ac9dc5d01e75602a812d0f1e5957b3.jpg","content":"我是内容是什么我是内容是我是内容是什么我是内容是什么我是内容是什么我是我是内容是什么我是内容是什么是什么我是内容是我是内容是什么我是内容是什么我是内容是什么","create_time":"2018-03-28 15:06:00"}]
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
         * id : 4
         * title : df
         * image : /public/uploads/20180328/b4b02c164009ad9634ec2388d1133068.jpg
         * content : ncivmckclm付聚聚女超级IC卡吃米线快发快发v考查课上扣扣瞅瞅没穿长裤开学考试没接受奥vi冯卡具i放开我女iv辛苦内v覅科目三聚大姐夫男女继续看少女级想看电视v看看怎么在空间覅v开什么车距i时快时慢陈几点开非买不可只看到距i分开过今晚继续结果i
         * create_time : 2018-03-28 15:13:14
         */

        private int id;
        private String title;
        private String image;
        private String content;
        private String create_time;
        private int cate_id;

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
