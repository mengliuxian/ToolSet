package com.flym.fhzk.data.model;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/1 0001
 */

public class HotKeyBean {

    /**
     * result : 0
     * message : 请求成功
     * datas : {"keyInfo":[{"id":1,"key":"童装"},{"id":2,"key":"套装"}]}
     */

        public List<KeyInfoBean> keyInfo;

        public static class KeyInfoBean {
            /**
             * id : 1
             * key : 童装
             */

            public int id;
            public String key;
        }

}
