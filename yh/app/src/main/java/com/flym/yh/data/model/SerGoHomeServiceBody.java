package com.flym.yh.data.model;

import java.util.List;

public class SerGoHomeServiceBody {
    public String appKey;
    public String sign;
    public String time;
    public String token;
    public int appointment_status;
    public List<UpdateServiceCall.Services> services;
}
