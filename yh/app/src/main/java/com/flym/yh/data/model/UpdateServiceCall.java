package com.flym.yh.data.model;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/30
 */

public class UpdateServiceCall {
    private String appKey;
    private String sign;
    private String time;
    private String token;
    private int call_status;
    private String call_time;
    private List<Services> services;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCall_status() {
        return call_status;
    }

    public void setCall_status(int call_status) {
        this.call_status = call_status;
    }

    public String getCall_time() {
        return call_time;
    }

    public void setCall_time(String call_time) {
        this.call_time = call_time;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public static class Services {
        private int type_id;
        private String price;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
