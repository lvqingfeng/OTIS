package com.nice.otis.api;

import com.nice.otis.base.ConsumptionBean;
import com.nice.otis.base.RechargeBean;
import com.nice.otis.bean.BannerBean;
import com.nice.otis.bean.BaseData;
import com.nice.otis.bean.BrandAttrBean;
import com.nice.otis.bean.BrandBean;
import com.nice.otis.bean.HeadLineDetailsBean;
import com.nice.otis.bean.HeadlineListBean;
import com.nice.otis.bean.IntegralBean;
import com.nice.otis.bean.LoginBean;
import com.nice.otis.bean.NoticeBean;
import com.nice.otis.bean.NoticeDetailsBean;
import com.nice.otis.bean.PersonBean;
import com.nice.otis.bean.RankListBean;
import com.nice.otis.bean.RegisterBean;
import com.nice.otis.bean.ToubleBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 接口
 */

public interface ApiService {
    String STATUS_SUC = "1";
    String TOKEN_EX = "2";
    String BASE_URL = "http://lift.lzy95.cn/";//正式服务器
    //用户协议
    String USER_AGREEMENT="index.php?s=/addon/Login/Wap/reg_agreement";
    //关于我们
    String ABOUT_US="index.php?s=/addon/UserCenter/Wap/about";
    /****
     * 注册
     * */
    @POST("index.php?s=/addon/Login/wap/register")
    @FormUrlEncoded
    Observable<RegisterBean> register(@Field("mobile") String mobile, @Field("password") String password
            , @Field("captcha") String captcha, @Field("device_number") String device_number);

    /****
     * 发送验证码
     * */
    @POST("index.php?s=/addon/Login/wap/create_code")
    @FormUrlEncoded
    Observable<BaseData> sendCaptcha(@Field("mobile") String mobile, @Field("type") String type);

    /****
     * 登录
     * */
    @POST("index.php?s=/addon/Login/wap/login")
    @FormUrlEncoded
    Observable<LoginBean> login(@Field("mobile") String mobile, @Field("password") String password, @Field("device_number") String device_number);

    /***
     * 重置密码
     * */
    @POST("index.php?s=/addon/Login/wap/reset_password")
    @FormUrlEncoded
    Observable<BaseData> resetPassword(@Field("mobile") String mobile
            , @Field("password") String password, @Field("captcha") String captcha);

    /****
     * 电梯品牌获取
     * */
    @POST("index.php?s=/addon/Trouble/Wap/get_brand")
    @FormUrlEncoded
    Observable<BrandBean> updateotisList(@Field("token") String token);

    /****
     * 反馈信息接口
     * */
    @POST("index.php?s=/addon/Feedback/wap/addFeedback")
    @FormUrlEncoded
    Observable<BaseData> updateFeedBack(@Field("mobile") String mobile, @Field("token") String token, @Field("content") String content);

    /*****
     * 轮播图
     * */
    @POST("index.php?s=/addon/Advertisement/wap/ad_list")
    @FormUrlEncoded
    Observable<BannerBean> loadBanner(@Field("token") String token);

    /*****
     * 电梯品牌下的型号查询
     * */
    @POST("index.php?s=/addon/Trouble/Wap/get_attr")
    @FormUrlEncoded
    Observable<BrandAttrBean> updateBrandAttrList(@Field("brand_id") String brand_id, @Field("token") String token);

    /***
     * 个人信息添加  修改
     * */
    @POST("index.php?s=/addon/UserCenter/wap/save_info")
    @FormUrlEncoded
    Observable<BaseData> uploadPersonData(@Field("token") String token
            , @Field("uid") String uid, @Field("mobile") String mobile, @Field("gender") String gender
            , @Field("truename") String truename, @Field("nickname") String nickname
            , @Field("birthday") String birthday);

    /*****
     * 获取个人信息
     * */
    @POST("index.php?s=/addon/UserCenter/Wap/get_user_info")
    @FormUrlEncoded
    Observable<PersonBean> getPersonData(@Field("token") String token);

    /*****
     * 上传头像
     * */
    @POST("index.php?s=/addon/UserCenter/wap/upload_head_img")
    @Multipart
    Observable<BaseData> uploadHeadImage(@Part MultipartBody.Part body, @Part("uid") RequestBody uid
            , @Part("token") RequestBody token);

    @POST("index.php?s=/addon/Trouble/Wap/chart_sort")
    @FormUrlEncoded
    Observable<RankListBean> uploadRankList(@Field("token") String token, @Field("page") String page, @Field("rows") String rows);

    /****
     * 资讯信息列表
     * */
    @POST("index.php?s=/addon/Headline/Wap/headlineList")
    @FormUrlEncoded
    Observable<HeadlineListBean> updateHeadLineList(@Field("token") String token
            , @Field("category_id") String category_id, @Field("page") String page, @Field("rows") String rows);

    /****
     * 咨询详情
     * **/
    @POST("index.php?s=/addon/Headline/Wap/headLineDetails")
    @FormUrlEncoded
    Observable<HeadLineDetailsBean> updateHeadlineDetails(@Field("id") String id, @Field("token") String token);

    /****
     * 公告列表
     * */
    @POST("index.php?s=/addon/UserCenter/Wap/notice")
    @FormUrlEncoded
    Observable<NoticeBean> updateNoticeList(@Field("token") String token, @Field("page") String page
            , @Field("rows") String rows);

    /****
     * 公告详情
     * */
    @POST("index.php?s=/addon/UserCenter/Wap/notice_detail")
    @FormUrlEncoded
    Observable<NoticeDetailsBean> updateNoticeDetails(@Field("id") String id, @Field("token") String token);

    /****
     * 故障查询
     * */
    @POST("index.php?s=/addon/Trouble/Wap/serach")
    @FormUrlEncoded
    Observable<ToubleBean> getToubleData(@Field("brand_id") String brand_id
            , @Field("attr_id") String attr_id
            , @Field("code") String code);

    /*****
     * 积分兑换
     * */
    @GET("index.php?s=/addon/Point/wap/get_point_category")
    Observable<IntegralBean>updatePointCategory();

    /****
     * 消费记录
     * */
    @POST("index.php?s=/addon/Point/Wap/get_point_logs")
    @FormUrlEncoded
    Observable<ConsumptionBean>updateConsumtion(@Field("uid")String uid,@Field("token")String token
            ,@Field("page")String page,@Field("rows")String rows);

    /**
     * 充值记录
     * **/
    @POST("index.php?s=/addon/UserCenter/wap/get_money_logs")
    @FormUrlEncoded
    Observable<RechargeBean>updateRecharge(@Field("uid")String uid,@Field("token")String token
            ,@Field("page")String page,@Field("rows")String rows);
}