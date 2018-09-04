package com.flym.yh.data.model;

import java.util.List;
//import com.hyphenate.easeui.domain.AddMedicationTemplate;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class AddMedicationTempateRe {
    private String token;
    public int id;
    private String name;
    private List<AddMedicationTemplate> goods;
    private String appKey;
    private String time;
    private String sign;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddMedicationTemplate> getGoods() {
        return goods;
    }



    public void setGoods(List<AddMedicationTemplate> list) {
        this.goods = list;
    }
}
