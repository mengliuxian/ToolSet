package com.flym.yh.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.flym.yh.data.model.GetOpenIdBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.WxLoginAndShareUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private DisposableWrapper<GetOpenIdBean> disposableWrapper;
    private String state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.entry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, "Constants.APP_ID", false);


        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        int type = resp.getType();
        if (type == ConstantsAPI.COMMAND_SENDAUTH) {
            //登录的回调
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) resp).code;
                    state = ((SendAuth.Resp) resp).state;
                    getOpenId(code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    if (WxLoginAndShareUtil.listeners != null) {
                        WxLoginAndShareUtil.listeners.onFail("失败");
                    }
//                    ToastUtil.showMessage(this, getString(R.string.Network_error));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
//			result = R.string.errcode_unsupported;
                    break;
                default:
//			result = R.string.errcode_unknown;
                    break;
            }
        } else if (type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            //分享的回调
//            Intent intent = new Intent(WxLoginAndShareUtil.ACTION_SHARE_RESPONSE);
//            intent.putExtra(WxLoginAndShareUtil.EXTRA_RESULT, new WxLoginAndShareUtil.Response(resp));
            //发送app内部广播，LocalBroadcastManager避免被其他app获取
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//            sendBroadcast(intent);
//            finish();

        }


//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    public void getOpenId(final String code) {
        if (!TextUtils.isEmpty(code)) {
            disposableWrapper = RetrofitUtil.getApiService()
                    .getOpenId(Constants.APP_ID, Constants.APP_SECRET, code, "authorization_code")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableWrapper<GetOpenIdBean>() {
                        @Override
                        public void onNext(GetOpenIdBean getOpenIdBean) {
                            if (getOpenIdBean != null) {
                                sendBroadcast(getOpenIdBean);

                            }
                        }
                    });
        }
    }

    public void getUserInfo(String token, String openId) {
//        int lang = UserManager.getLang(this);
//        String Lang = "";
//        if (lang == 1) {
//            Lang = "zh_CN";
//        } else if (lang == 2) {
//            Lang = "en";
//        }
//        RetrofitUtil.getApiService().getUserInfo(token, openId, Lang)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableWrapper<WxUserInfo>() {
//                    @Override
//                    public void onNext(WxUserInfo wxUserInfo) {
//                        sendBroadcast(wxUserInfo);
//                    }
//                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableWrapper != null) {

            disposableWrapper.dispose();
        }
    }

    public void sendBroadcast(GetOpenIdBean wxUserInfo) {
        if (WxLoginAndShareUtil.listeners != null) {
            WxLoginAndShareUtil.listeners.onSuccess(wxUserInfo);
        }


        Intent intent = new Intent();
        intent.putExtra("WxUserInfo", wxUserInfo);
        switch (state) {
            default:
                break;
            case "diandi_wx_login":
                intent.setAction(Constants.LOGIN_ACTION);
                break;
//            case "diandi_wx_bind":
//                intent.setAction(Constants.BIND_ACTION);
//                break;
        }
        sendBroadcast(intent);
        finish();
    }
}