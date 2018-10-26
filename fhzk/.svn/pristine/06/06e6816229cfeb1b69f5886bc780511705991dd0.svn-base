package com.flym.fhzk.http;

import android.text.TextUtils;

/**
 * Created by Morphine on 2017/6/12.
 */

public class ResponseError extends Exception {

    private final String errorCode;
    private String errorMsg;

    public ResponseError(String errorCode, String errorMsg) {
        super("errorCode:" + errorCode + " errorMsg:" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        if (TextUtils.isEmpty(errorMsg)) {
            return super.getMessage();
        }
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
