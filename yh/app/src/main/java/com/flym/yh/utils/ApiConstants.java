package com.flym.yh.utils;

import android.text.TextUtils;

/**
 * Created by Morphine on 2017/6/12.
 */

public class ApiConstants {

    public static final String BASE_URL = "http://php.ssssapp.com/yihuan/";

    public static final String GETCODE = BASE_URL + "api/app/getCode";

    public static final String REGISTER = BASE_URL + "api/app/register";

    public static final String LOGIN = BASE_URL + "api/app/login";

    public static final String FINDPASS = BASE_URL + "api/app/findPass";

    public static final String UPLOAD = BASE_URL + "api/app/upload";

    public static final String GETDEPARTMENTLIST = BASE_URL + "api/app/getDepartmentlist";

    public static final String GETADLIST = BASE_URL + "api/app/getAdlist";

    public static final String GETDIAGNOSTICTEMPLATE = BASE_URL + "api/diagnosetpl/getList";
    public static final String SAVEDIAGNOSTICTEMPLATE = BASE_URL + "api/diagnosetpl/save";
    public static final String DELDIAGNOSTICTEMPLATE = BASE_URL + "api/diagnosetpl/del";

    public static final String GETDRUGDIRECTORY = BASE_URL + "api/medicine/getList";
    public static final String GETDRUGCATELIST = BASE_URL + "api/medicine/getCatelist";
    public static final String GETDRUGDETAIL = BASE_URL + "api/medicine/getDetail";


    public static final String GETMEDICATIONTEMPLATE = BASE_URL + "api/pharmacytpl/getList";
    public static final String SAVEMEDICATIONTEMPLATE = BASE_URL + "api/pharmacytpl/save";
    public static final String DELMEDICATIONTEMPLATE = BASE_URL + "api/pharmacytpl/del";

    public static final String ARTICLEGETLIST = BASE_URL + "api/article/getList";
    public static final String ARTICLEGETCATELIST = BASE_URL + "api/article/getCatelist";

    public static final String TOPICGETLIST = BASE_URL + "api/topic/getList";
    public static final String TOPICGETDETAIL = BASE_URL + "api/topic/getDetail";
    public static final String TOPICGETCOMMENTLIST = BASE_URL + "api/topic/getCommentlist";
    public static final String TOPICCOMMENT = BASE_URL + "api/topic/comment";

    public static final String GETDOCTORARTIVLELIST = BASE_URL + "api/article/getMylist";
    public static final String SAVEDOCTORCATE = BASE_URL + "api/article/save";
    public static final String DOCTORUPDATEACCOUNT = BASE_URL + "api/doctor/updateAccount";
    public static final String DOCTORUPDATEINFO = BASE_URL + "api/doctor/updateInfo";
    public static final String DOCTORGETDETAIL = BASE_URL + "api/doctor/getDetail";
    public static final String DOCTORUPDATEPASS = BASE_URL + "api/doctor/updatePass";
    public static final String DOCTORGETCOMMENTLIST = BASE_URL + "api/doctor/getCommentlist";
    public static final String DOCTORGETMESSAGELIST = BASE_URL + "api/doctor/getMessagelist";
    public static final String DOCTORGETTOPICLIST = BASE_URL + "api/doctor/getTopiclist";
    public static final String DOCTORCOMMENT = BASE_URL + "api/doctor/comment";
    public static final String DOCTORUPDATESERVICETIME = BASE_URL + "api/doctor/updateService";

    public static final String GOODSGETCATELIST = BASE_URL + "api/goods/getCatelist";
    public static final String GOODSGETDETAIL = BASE_URL + "api/goods/getDetail";
    public static final String GOODSGETLIST = BASE_URL + "api/goods/getList";

    public static final String GETSERVICESLIST = BASE_URL + "api/doctoritems/getList";
    public static final String GETPHONELONG = BASE_URL + "api/app/getCalltimelist";
    public static final String GETSERVICELIST = BASE_URL + "api/app/getServicelist";
    public static final String DOCTORGETORDERLIST = BASE_URL + "api/doctor/getOrderlist";

    public static final String RECIPEGETLIST = BASE_URL + "api/recipe/getList";
    public static final String RECIPESEND = BASE_URL + "api/recipe/send";
    public static final String RECIPERECALL = BASE_URL + "api/recipe/recall";
    public static final String RECIPEDEL = BASE_URL + "api/recipe/del";

    public static final String SHORTCUTGETLIST = BASE_URL + "api/shortcut/getList";
    public static final String SHORTCUTSAVE = BASE_URL + "api/shortcut/save";
    public static final String SHORTCUTDEL = BASE_URL + "api/shortcut/del";

    public static final String DOCTORITEMSSAVE = BASE_URL + "api/doctoritems/save";
    public static final String DOCTORITEMSDEL = BASE_URL + "api/doctoritems/del";
    public static final String DOCTORITEMSGETDETAIL = BASE_URL + "api/doctoritems/getDetail";

    public static final String THERAPYSEND = BASE_URL + "api/therapy/send";

    public static final String CHATGETUSER = BASE_URL + "api/chat/getUser";

    public static final String DOCTORGETACCOUNTLOG = BASE_URL + "api/doctor/getAccountlog";
    public static final String DOCTORSERVICE = BASE_URL + "api/doctor/service";
    public static final String DOCTORBINDTHIRD = BASE_URL + "api/doctor/bindThird";
    public static final String DOCTORWITHDRAW = BASE_URL + "api/doctor/withdraw";
    public static final String DOCTORFEEDBACK = BASE_URL + "api/doctor/feedback";



    public static final String GETOPENID = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public static String getImageUrl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            return "";
        }
        if (imgUrl.startsWith("http")) {
            return imgUrl;
        } else {
            return BASE_URL + imgUrl;
        }
    }

}
