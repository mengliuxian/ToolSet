package com.flym.yh.data.model;

/**
 * Author:mengl
 * Time:2018/5/11
 */
public class ChatGetuserBean {
    /**
     * data : /yihuan/api/chat/getUser
     * info : {"username":"doctor_7","password":"XS2aGGiyMWOnyYSXODB12b068e272ac7a9b0ab3c6ccb594aa3b4"}
     */

    public String data;
    public InfoBean info;

    public static class InfoBean {
        /**
         * username : doctor_7
         * password : XS2aGGiyMWOnyYSXODB12b068e272ac7a9b0ab3c6ccb594aa3b4
         */

        public String username;
        public String password;
    }
}
