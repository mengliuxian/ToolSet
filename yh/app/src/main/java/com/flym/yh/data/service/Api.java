package com.flym.yh.data.service;

import com.flym.yh.data.model.AdlistBean;
import com.flym.yh.data.model.ArticleGetListBean;
import com.flym.yh.data.model.ChatGetuserBean;
import com.flym.yh.data.model.DocGetDetailBean;
import com.flym.yh.data.model.DoctorGetAccountBean;
import com.flym.yh.data.model.DoctorGetcommentlistBean;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.DoctorGetmessagelistBean;
import com.flym.yh.data.model.DrugDirectoryBean;
import com.flym.yh.data.model.FastReplyBean;
import com.flym.yh.data.model.GetCateListBean;
import com.flym.yh.data.model.GetDepartmenglistBean;
import com.flym.yh.data.model.GetDoctorArtivleListBean;
import com.flym.yh.data.model.GetOpenIdBean;
import com.flym.yh.data.model.GetServicelistBean;
import com.flym.yh.data.model.GoodsGetcatelistBean;
import com.flym.yh.data.model.GetPhoneLongTimeBean;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.data.model.GoodsGetlistBean;
import com.flym.yh.data.model.MedicationTemplateBean;
import com.flym.yh.data.model.QuicklyFindMedicineBean;
import com.flym.yh.data.model.RecipeGetlistBean;
import com.flym.yh.data.model.SaveDiagnosticTemplateBean;
import com.flym.yh.data.model.ShopDetailsBean;
import com.flym.yh.data.model.TopicGetcommentlistBean;
import com.flym.yh.data.model.TopicGetdetailBean;
import com.flym.yh.data.model.TopicGetlistBean;
import com.flym.yh.net.SimpleResponse;
import com.flym.yh.net.SimpleString;
import com.flym.yh.utils.ApiConstants;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by mengl on 2018/3/19.
 */

public interface Api {

    /**
     * 1.获取验证码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETCODE)
    Flowable<SimpleResponse<SimpleString>> getCode(@Field("phone") String phone, @Field("type") String type);


    /**
     * 2.注册
     *
     * @param phone
     * @param password
     * @param confirm_pass
     * @param code
     * @param referrer
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.REGISTER)
    Flowable<SimpleResponse<SimpleString>> register(@Field("phone") String phone,
                                                    @Field("password") String password,
                                                    @Field("confirm_pass") String confirm_pass,
                                                    @Field("code") String code,
                                                    @Field("referrer") String referrer
    );

    /**
     * 3.登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    Flowable<SimpleResponse<SimpleString>> login(@Field("phone") String phone,
                                                 @Field("password") String password
    );

    /**
     * 4.找回密码
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.FINDPASS)
    Flowable<SimpleResponse<SimpleString>> findPass(@Field("phone") String phone,
                                                    @Field("password") String password,
                                                    @Field("code") String code
    );

    /**
     * 5.文件上传
     *
     * @return
     */
    @Multipart
    @POST(ApiConstants.UPLOAD)
    Flowable<SimpleResponse<SimpleString>> upload(@Part MultipartBody.Part body);


    /**
     * 6.获取科室列表
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDEPARTMENTLIST)
    Flowable<SimpleResponse<GetDepartmenglistBean>> getDepartmentlist(@Field("empty") String empty);

    /**
     * 获取轮播图列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETADLIST)
    Flowable<SimpleResponse<AdlistBean>> getAdlist(@Field("empty") String empty);


    /**
     * 获取诊断列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDIAGNOSTICTEMPLATE)
    Flowable<SimpleResponse<SaveDiagnosticTemplateBean>> getDiagnosticTemplate(@Field("page") int page,
                                                                               @Field("name") String name);

    /**
     * 保存诊断列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.SAVEDIAGNOSTICTEMPLATE)
    Flowable<SimpleResponse<Object>> saveDiagnosticTemplate(@Field("name") String name, @Field("id") int id);

    /**
     * 删除诊断列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.DELDIAGNOSTICTEMPLATE)
    Flowable<SimpleResponse<Object>> delDiagnosticTemplate(@Field("id") int id);


    /**
     * 获取药物列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDRUGDIRECTORY)
    Flowable<SimpleResponse<DrugDirectoryBean>> getDrugDirectory(@Field("name") String name,
                                                                 @Field("page") int page,
                                                                 @Field("cate_id") int cate_id);

    /**
     * 获取药物分类列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDRUGCATELIST)
    Flowable<SimpleResponse<QuicklyFindMedicineBean>> getDrugCatelist(@Field("id") int id);

    /**
     * 获取药物详情  获取商品详情
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDRUGDETAIL)
    Flowable<SimpleResponse<Object>> getDrugDetail(@Field("id") int id);

    /**
     * 获取用药模板列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETMEDICATIONTEMPLATE)
    Flowable<SimpleResponse<MedicationTemplateBean>> getMedicationTemplate(@Field("page") int page, @Field("name") String name);

    /**
     * 保存用药模板列表
     */
    @POST(ApiConstants.SAVEMEDICATIONTEMPLATE)
    Flowable<SimpleResponse<Object>> saveMedicationTemplate(@Body RequestBody body);

    /**
     * 删除用药模板列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.DELMEDICATIONTEMPLATE)
    Flowable<SimpleResponse<Object>> delMedicationTemplate(@Field("id") int id);

    /**
     * 获取资讯列表
     *
     * @param title
     * @param page
     * @param cate_id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.ARTICLEGETLIST)
    Flowable<SimpleResponse<ArticleGetListBean>> getList(@Field("title") String title,
                                                         @Field("page") int page,
                                                         @Field("cate_id") int cate_id);

    /**
     * 获取资讯分类列表
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.ARTICLEGETCATELIST)
    Flowable<SimpleResponse<GetCateListBean>> getCatelist(@Field("empty") String empty);


    /**
     * 获取话题列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.TOPICGETLIST)
    Flowable<SimpleResponse<TopicGetlistBean>> topicGetlist(@Field("page") int page);

    /**
     * 获取论坛详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.TOPICGETDETAIL)
    Flowable<SimpleResponse<TopicGetdetailBean>> topicGetdetail(@Field("id") int id);

    /**
     * 获取话题评论列表
     *
     * @param id
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.TOPICGETCOMMENTLIST)
    Flowable<SimpleResponse<TopicGetcommentlistBean>> topicGetcommentlist(@Field("id") int id,
                                                                          @Field("page") int page);

    /**
     * 更新医生个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATEACCOUNT)
    Flowable<SimpleResponse<Object>> doctorUpdateaccount(@Field("image") String image,
                                                         @Field("name") String name,
                                                         @Field("sex") String sex,
                                                         @Field("hospital") String hospital,
                                                         @Field("department_id") String department_id,
                                                         @Field("job") String job,
                                                         @Field("specialty") String specialty,
                                                         @Field("province") String province,
                                                         @Field("city") String city,
                                                         @Field("district") String district,
                                                         @Field("address") String address,
                                                         @Field("qr_code") String qr_code
    );

    /**
     * 更新医生职业信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATEINFO)
    Flowable<SimpleResponse<Object>> doctorUpdateinfo(@Field("id_card_front") String id_card_front,
                                                      @Field("id_card_back") String id_card_back,
                                                      @Field("license") String license,
                                                      @Field("practising") String practising,
                                                      @Field("employee_card") String employee_card,
                                                      @Field("auth_status") String auth_status

    );

    /**
     * 获取登录医生详情
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETDETAIL)
    Flowable<SimpleResponse<DoctorGetdetailBean>> doctorGetdetail(@Field("empty") String empty);

    /**
     * 更新医生账户密码
     *
     * @param old_pass
     * @param password
     * @param confirm_pass
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATEPASS)
    Flowable<SimpleResponse<SimpleString>> doctorUpdatepass(@Field("old_pass") String old_pass,
                                                            @Field("password") String password,
                                                            @Field("confirm_pass") String confirm_pass);

    /**
     * 获取评价列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETCOMMENTLIST)
    Flowable<SimpleResponse<DoctorGetcommentlistBean>> doctorGetcommentlist(@Field("page") int page);

    /**
     * 获取消息通知列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETMESSAGELIST)
    Flowable<SimpleResponse<DoctorGetmessagelistBean>> doctorGetmessagelist(@Field("page") int page);

    /**
     * 获取我的论坛列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETTOPICLIST)
    Flowable<SimpleResponse<TopicGetlistBean>> doctorGettopiclist(@Field("page") int page);

    /**
     * 评论患者
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORCOMMENT)
    Flowable<SimpleResponse<SimpleString>> doctorComment(@Field("level") int level,
                                                         @Field("order_id") int log_id);


    /**
     * 获取医生投稿列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETDOCTORARTIVLELIST)
    Flowable<SimpleResponse<GetDoctorArtivleListBean>> getDoctorArtivleList(@Field("page") int page,
                                                                            @Field("status") int status);

    /**
     * 保存医生投稿
     */
    @FormUrlEncoded
    @POST(ApiConstants.SAVEDOCTORCATE)
    Flowable<SimpleResponse<Object>> saveDoctorArtivle(@Field("title") String title,
                                                       @Field("image") String image,
                                                       @Field("content") String content,
                                                       @Field("cate_id") int cate_id,
                                                       @Field("id") int id);

    /**
     * 新增服务
     *
     * @param title
     * @param image
     * @param summary
     * @param price
     * @param content
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORITEMSSAVE)
    Flowable<SimpleResponse<Object>> doctoritemsSave(@Field("title") String title,
                                                     @Field("image") String image,
                                                     @Field("summary") String summary,
                                                     @Field("price") String price,
                                                     @Field("content") String content,
                                                     @Field("file_id") String file_id,
                                                     @Field("type_id") int id);

    /**
     * 获取服务详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORITEMSGETDETAIL)
    Flowable<SimpleResponse<DocGetDetailBean>> doctoritemsGetdetail(@Field("id") int id);

    /**
     * 获取账号密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.CHATGETUSER)
    Flowable<SimpleResponse<ChatGetuserBean>> chatGetuser(@Field("id") int id);

    /**
     * 获取我的收入明细
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETACCOUNTLOG)
    Flowable<SimpleResponse<DoctorGetAccountBean>> doctorGetaccountlog(@Field("page") int page,
                                                                       @Field("start_time") String start,
                                                                       @Field("end_time") String end);

    /**
     * 联系患者
     *
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORSERVICE)
    Flowable<SimpleResponse<Object>> doctorService(@Field("order_id") int order_id);

    /**
     * 绑定第三方
     *
     * @param wx_openid
     * @param ali_openid
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORBINDTHIRD)
    Flowable<SimpleResponse<Object>> doctorBindthird(@Field("wx_openid") String wx_openid,
                                                     @Field("ali_openid") String ali_openid);

    /**
     * 提现余额
     *
     * @param amount
     * @param pay_type 支付类型（0微信，1支付宝）
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORWITHDRAW)
    Flowable<SimpleResponse<Object>> doctorWithdraw(@Field("amount") int amount,
                                                    @Field("pay_type") int pay_type);

    /**
     * 反馈
     *
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORFEEDBACK)
    Flowable<SimpleResponse<Object>> doctorBeedback(@Field("content") String content);

    /**
     * 删除服务
     *
     * @return
     */
    @POST(ApiConstants.DOCTORITEMSDEL)
    Flowable<SimpleResponse<SimpleString>> doctoritemsDel(@Body RequestBody body);


    /**
     * 获取服务列表
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETSERVICESLIST)
    Flowable<SimpleResponse<GetServicesBean>> getServicesList(@Field("page") int page,
                                                              @Field("type_id") int type_id);

    /**
     * 获取服务账单列表
     *
     * @param page
     * @param type_id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORGETORDERLIST)
    Flowable<SimpleResponse<GetServicesBean>> doctorGetorderlist(@Field("page") int page,
                                                                 @Field("type_id") int type_id);


    /**
     * 保存服务状态信息
     */
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<Object>> UpdateServicesCall(@Body RequestBody services);


    /**
     * 获取电话时长列表
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETPHONELONG)
    Flowable<SimpleResponse<GetPhoneLongTimeBean>> getPhoneLong(@Field("empty") String empty);

    /**
     * 获取商品列表
     *
     * @param page
     * @param cate_id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GOODSGETLIST)
    Flowable<SimpleResponse<GoodsGetlistBean>> goodsGetlist(@Field("page") int page,
                                                            @Field("cate_id") String cate_id,
                                                            @Field("order") String order);

    /**
     * 获取商品分类列表
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GOODSGETCATELIST)
    Flowable<SimpleResponse<GoodsGetcatelistBean>> goodsGetcatelist(@Field("empty") String empty);

    /**
     * 获取商品详情
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GOODSGETDETAIL)
    Flowable<SimpleResponse<ShopDetailsBean>> goodsGetdetail(@Field("id") int id);

    /**
     * 更新医生服务状态(门诊时间)
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<SimpleString>> doctorUpdateServiceTime(@Field("inquiry_time") String inquiry_time);

    /**
     * 图文咨询
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<SimpleString>> doctorUpdateServiceConsulting(@Field("inquiry_status") int inquiry_status,
                                                                         @Field("inquiry_price") String inquiry_price);

    /**
     * 医学阅读
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<SimpleString>> doctorUpdateServiceReading(@Field("read_status") int read_status);

    /**
     * 名师讲堂
     *
     * @param lecture_status
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<SimpleString>> doctorUpdateServiceClassroom(@Field("lecture_status") int lecture_status);


    /**
     * 名医广播
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.DOCTORUPDATESERVICETIME)
    Flowable<SimpleResponse<SimpleString>> doctorUpdateServiceRadio(@Field("broadcast_status") int broadcast_status);


    /**
     * 评论话题
     *
     * @param content
     * @param topic_id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.TOPICCOMMENT)
    Flowable<SimpleResponse<Object>> topicComment(@Field("content") String content, @Field("topic_id") int topic_id);

    /**
     * 获取上门服务列表
     *
     * @param empty
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.GETSERVICELIST)
    Flowable<SimpleResponse<GetServicelistBean>> getServicelist(@Field("empty") String empty);

    /**
     * 获取健康处方列表
     *
     * @param page
     * @param name
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.RECIPEGETLIST)
    Flowable<SimpleResponse<RecipeGetlistBean>> recipeGetlist(@Field("page") int page,
                                                              @Field("name") String name,
                                                              @Field("status") int status);

    /**
     * 撤回健康处方
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.RECIPERECALL)
    Flowable<SimpleResponse<SimpleString>> recipeRecall(@Field("id") int id);

    /**
     * 删除健康处方
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.RECIPEDEL)
    Flowable<SimpleResponse<SimpleString>> recipeDel(@Field("id") int id);

    /**
     * 获取快捷回复列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.SHORTCUTGETLIST)
    Flowable<SimpleResponse<FastReplyBean>> shortcutGetlist(@Field("page") int page);

    /**
     * 新增快捷回复
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.SHORTCUTSAVE)
    Flowable<SimpleResponse<FastReplyBean>> shortCutsave(@Field("content") String content);

    /**
     * 发送治疗建议
     *
     * @return
     */
    @POST(ApiConstants.THERAPYSEND)
    Flowable<SimpleResponse<SimpleString>> therapySend(@Body RequestBody body);

    /**
     * 删除快捷回复
     *
     * @return
     */

    @POST(ApiConstants.SHORTCUTDEL)
    Flowable<SimpleResponse<SimpleString>> shortCutdel(@Body RequestBody body);

    /**
     * 发送健康处方
     *
     * @param body
     * @return
     */
    @POST(ApiConstants.RECIPESEND)
    Flowable<SimpleResponse<SimpleString>> recipeSend(@Body RequestBody body);

    /**
     * 通过code获取access_token
     *
     * @param code
     * @return
     */
    @GET(ApiConstants.GETOPENID)
    Flowable<GetOpenIdBean> getOpenId(@Query("appid") String appid,
                                      @Query("secret") String secret,
                                      @Query("code") String code,
                                      @Query("grant_type") String authorization_code);
}
