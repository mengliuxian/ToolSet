package com.flym.yh.data.model;

import java.io.Serializable;

public class DoctorGetdetailBean implements Serializable {


    private static final long serialVersionUID = 637456444888893600L;
    /**
     * info : {"name":null,"sex":0,"phone":"15044563067","image":null,"qr_code":null,"hospital":null,"department_id":0,"department_name":null,"job":null,"province":null,"city":null,"district":null,"address":null,"account":"0.00","freeze":"0.00","inquiry_status":1,"inquiry_time":null,"call_status":1,"call_time":null,"appointment_status":1,"broadcast_status":1,"lecture_status":1,"read_status":1}
     * stats : {"fans":0,"orders":0,"favor_rate":"0%"}
     * certs : {"id_card_front":"/public/uploads/20180328/6cb0d6a4cdea97059ab8b30a3f143cbb.png","id_card_back":null,"license":null,"practising":null,"employee_card":null}
     */

    public InfoBean info;
    public StatsBean stats;
    public CertsBean certs;

    public static class InfoBean implements Serializable {
        private static final long serialVersionUID = 2811082566399875662L;
        /**
         * name : null
         * sex : 0
         * phone : 15044563067
         * image : null
         * qr_code : null
         * hospital : null
         * department_id : 0
         * department_name : null
         * job : null
         * province : null
         * city : null
         * district : null
         * address : null
         * account : 0.00
         * freeze : 0.00
         * inquiry_status : 1
         * inquiry_time : null
         * call_status : 1
         * call_time : null
         * appointment_status : 1
         * broadcast_status : 1
         * lecture_status : 1
         * read_status : 1
         */

        public String name;
        public int sex;
        public String phone;
        public String image;
        public String qr_code;
        public String hospital;
        public int department_id;
        public String department_name;
        public String job;
        public String specialty;
        public String province = "";
        public String city = "";
        public String district = "";
        public String address;
        public String refuse_reason;
        public String account;
        public String freeze;
        public int inquiry_status;
        public String inquiry_time;
        public int call_status;
        public String call_time;
        public int appointment_status;
        public int broadcast_status;
        public int lecture_status;
        public int read_status;
    }

    public static class StatsBean implements Serializable {
        private static final long serialVersionUID = 60300701819858478L;
        /**
         * fans : 0
         * orders : 0
         * favor_rate : 0%
         */

        public String fans;
        public String orders;
        public String favor_rate;
    }

    public static class CertsBean implements Serializable {
        private static final long serialVersionUID = -4461575149220830060L;
        /**
         * id_card_front : /public/uploads/20180328/6cb0d6a4cdea97059ab8b30a3f143cbb.png
         * id_card_back : null
         * license : null
         * practising : null
         * employee_card : null
         */

        public String id_card_front;
        public String id_card_back;
        public String license;
        public String practising;
        public String employee_card;
    }

}
