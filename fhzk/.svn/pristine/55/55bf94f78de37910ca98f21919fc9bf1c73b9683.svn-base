package com.flym.fhzk.data.service;


import com.flym.fhzk.data.model.ActiveBean;
import com.flym.fhzk.data.model.BannerBean;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.data.model.HelpConfBean;
import com.flym.fhzk.data.model.HelpDetailBean;
import com.flym.fhzk.data.model.HotKeyBean;
import com.flym.fhzk.data.model.InviteInfoBean;
import com.flym.fhzk.data.model.MessageBean;
import com.flym.fhzk.data.model.ProfileBean;
import com.flym.fhzk.data.model.SignBean;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.data.model.VersionBean;
import com.flym.fhzk.http.SimpleResponse;
import com.flym.fhzk.utils.ApiConstants;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public interface Api {


    /**
     * 3.登录
     *
     * @param phone
     * @param password
     * @param origin
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    Flowable<SimpleResponse<SimpleString>> login(@Field("phone") String phone,
                                                 @Field("password") String password,
                                                 @Field("origin") String origin);

    /**
     * 获取新的token
     *
     * @param phone
     * @param password
     * @param origin
     * @return
     */
    @POST(ApiConstants.LOGIN)
    Call<SimpleResponse<SimpleString>> token(@Field("phone") String phone,
                                             @Field("password") String password,
                                             @Field("origin") String origin);

    /**
     * 1.	获取验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCODE)
    Flowable<SimpleResponse<SimpleString>> getCode(@Field("phone") String phone,
                                                   @Field("type") String type);

    /**
     * 2.	注册
     *
     * @param phone
     * @param password
     * @param phoneCode
     * @param inviteCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.REG)
    Flowable<SimpleResponse<SimpleString>> reg(@Field("phone") String phone,
                                               @Field("password") String password,
                                               @Field("phoneCode") String phoneCode,
                                               @Field("inviteCode") String inviteCode);

    /**
     * 4.	忘记密码
     *
     * @param phone
     * @param code
     * @param newpwd
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.SETPWD)
    Flowable<SimpleResponse<SimpleString>> setpwd(@Field("phone") String phone,
                                                  @Field("code") String code,
                                                  @Field("newpwd") String newpwd);

    /**
     * 5.	轮播图
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETBANNER)
    Flowable<SimpleResponse<BannerBean>> getBanner(@Field("skuId") String skuId);

    /**
     * 6.	商品分类
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETSKU)
    Flowable<SimpleResponse<ClassifyBean>> getSku(@Field("empty") String empty);

    /**
     * 7.	活动位
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETACTIVE)
    Flowable<SimpleResponse<ActiveBean>> getActive(@Field("empty") String empty);


    /**
     * 8.  商品
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETGOODS)
    Flowable<SimpleResponse<GoodsBean>> getGoods(@Field("dataType") String dataType,
                                                 @Field("page") String page);

    /**
     * 9.	热搜标签
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETHOTKEY)
    Flowable<SimpleResponse<HotKeyBean>> getHotKey(@Field("empty") String empty);

    /**
     * 10.	搜索
     *
     * @param keyId
     * @param keyword
     * @param sort    销量（sales），价格（price），默认为空
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETKEYGOODS)
    Flowable<SimpleResponse<GoodsBean>> getKeyGoods(@Field("keyId") String keyId,
                                                    @Field("keyword") String keyword,
                                                    @Field("sort") String sort,
                                                    @Field("page") String page);

    /**
     * 11.	优惠券
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCOUPONS)
    Flowable<SimpleResponse<SimpleString>> getCoupons(@Field("empty") String empty);

    /**
     * 12.	找券商品
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCOUPONGOODS)
    Flowable<SimpleResponse<GoodsBean>> getCouponGoods(@Field("page") String page);


    /**
     * 13.	找券-分类商品
     *
     * @param sort
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCOUPONSKUGOODS)
    Flowable<SimpleResponse<GoodsBean>> getCouponSkuGoods(@Field("skuId") String skuId,
                                                          @Field("sort") String sort,
                                                          @Field("page") String page);

    /**
     * 14.	版本更新
     *
     * @param versionCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETVERSION)
    Flowable<SimpleResponse<VersionBean>> getVersion(@Field("versionCode") String versionCode);

    /**
     * 15.	退出登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.LOGOUT)
    Flowable<SimpleResponse<SimpleString>> loginOut(@Field("empty") String empty);

    /**
     * 16.	个人信息
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETPROFILE)
    Flowable<SimpleResponse<ProfileBean>> getProfile(@Field("empty") String empty);

    /**
     * 17.	修改个人信息
     *
     * @param imgUrl
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.UPDATAPROFILE)
    Flowable<SimpleResponse<SimpleString>> updateProfile(@Field("imgUrl") String imgUrl);

    /**
     * 18.	收藏
     *
     * @param goodId
     * @param type   1收藏  2取消收藏
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.COLLECT)
    Flowable<SimpleResponse<SimpleString>> collect(@Field("goodId") String goodId,
                                                   @Field("type") String type);

    /**
     * 19.	我的收藏
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCOLLECTED)
    Flowable<SimpleResponse<GoodsBean>> getCollected(@Field("page") String page);

    /**
     * 20.	浏览
     *
     * @param goodId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.BROWSE)
    Flowable<SimpleResponse<SimpleString>> browse(@Field("goodId") String goodId);

    /**
     * 21.	我的足迹
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETBROWSED)
    Flowable<SimpleResponse<GoodsBean>> getBrowsed(@Field("page") String page);

    /**
     * 22.	删除我的足迹 & 25.	删除我的收藏
     *
     * @param browseId  (删除我的足迹) 为空表示清除所有
     * @param collectId (删除我的收藏)
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DELBROWSED)
    Flowable<SimpleResponse<SimpleString>> delBrowsed(@Field("browseId") String browseId,
                                                      @Field("collectId") String collectId);

    /**
     * 23.	首页-分类商品
     *
     * @param skuId
     * @param sort
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETSKUGOODS)
    Flowable<SimpleResponse<GoodsBean>> getSkuGoods(@Field("skuId") String skuId,
                                                    @Field("sort") String sort,
                                                    @Field("page") String page);

    /**
     * 24.	找券-分类
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCOUPONSKU)
    Flowable<SimpleResponse<ClassifyBean>> getCouponSku(@Field("empty") String empty);


    /**
     * 26.	未读消息
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.UNREADMSG)
    Flowable<SimpleResponse<SimpleString>> unreadMsg(@Field("empty") String empty);

    /**
     * 27.	消息列表
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETMESSAGE)
    Flowable<SimpleResponse<MessageBean>> getMessage(@Field("page") String page);

    /**
     * 28.	获取签到信息
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETSIGNINFO)
    Flowable<SimpleResponse<SignBean>> getSignInfo(@Field("empty") String empty);

    /**
     * 29.	签到
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.SIGNIN)
    Flowable<SimpleResponse<SimpleString>> signIn(@Field("empty") String empty);

    /**
     * 30.	帮助中心-1
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETHELPCONF)
    Flowable<SimpleResponse<HelpConfBean>> getHelpConf(@Field("empty") String empty);

    /**
     * 31.	帮助中心-2
     *
     * @param confId
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETHELPDETAIL)
    Flowable<SimpleResponse<HelpDetailBean>> getHelpDetail(@Field("confId") String confId,
                                                           @Field("page") String page);

    /**
     * 32.邀请
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETINVITEINFO)
    Flowable<SimpleResponse<InviteInfoBean>> getInviteInfo(@Field("empty") String empty);

    /**
     * 33.	关于我们
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.ABOUTUS)
    Flowable<SimpleResponse<String>> aboutUs(@Field("empty") String empty);

    /**
     * 34.	活动位-分类
     *
     * @param channelId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCHANNELSKU)
    Flowable<SimpleResponse<ClassifyBean>> getChannelSku(@Field("channelId") String channelId);

    /**
     * 35.	活动位-商品
     *
     * @param channelId
     * @param skuId
     * @param selected
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCHANNELGOODS)
    Flowable<SimpleResponse<GoodsBean>> getChannelGoods(@Field("channelId") String channelId,
                                                        @Field("skuId") String skuId,
                                                        @Field("selected") String selected,
                                                        @Field("page") String page);


}



