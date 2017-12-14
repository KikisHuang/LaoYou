package com.tencent.qcloud.sdk;

/**
 * Created by lian on 2017/10/25.
 */
public class Interface {

    //public static final String URL = "http://spib.mozu123.com/mcServicePlatform_InternetBar/";
    public static final String URL = "http://192.168.1.200:8888/mcServicePlatform_InternetBar/";


    /**
     * ip查询;
     */
    public static final String IPQUERYURL = "http://ip.chinaz.com/getip.aspx";

    /**
     * 获取轮播广告接口;
     * 获取轮播广告 参数： showPosition=显示位置 0首页
     */
    public static final String GETBANNER = "banner/getBanner.app";
    /**
     * 忘记密码验证码获取接口;
     * 参数: key = 用户key
     * 返回成功code是1
     */
    public static final String GETREPASSWORDCODE = "main/getRePasswordCode.app";
    /**
     * 账号密码登陆接口;
     * account=账号,password=密码 返回： 成功code是1,data是key
     * 返回成功code是1
     */
    public static final String LOGIN = "main/login.app";
    /**
     * 修改密码接口;
     * 接口 修改密码 参数: key = 用户key ,password=旧密码，newPassword=新密码
     * 返回成功code是1
     */
    public static final String MODIFYPASSWORD = "main/modifyPassword.app";
    /**
     * 注册接口;
     * 参数： password=密码,phone=手机号(手机号和账号'account'是2个分开的字段，传phone接口会将手机号写入账号！)
     */
    public static final String REGISTER = "main/register.app";
    /**
     * 修改用户信息接口;
     * 修改个人信息(昵称/头像) 参数: key = 用户key
     * 返回成功code是1
     */
    public static final String MODIFYUSER = "user/modify/modifyUser.app";
    /**
     * 获取我的详情信息接口;
     * 参数: key = 用户key
     * 返回成功code是1
     */
    public static final String MYINFODETAILS = "user/select/getMyDetails.app";
    /**
     * 忘记密码（修改新密码）接口;
     * newPassword=新密码，code=验证码
     * 返回成功code是1
     */
    public static final String REPASSWORD = "main/rePassword.app";
    /**
     * 注册发送验证码接口;
     * 参数: phone = 用户号码
     * 返回成功code是1
     */
    public static final String GETREGISTERCODE = "main/getRegisterCode.app";
    /**
     * 检测账号接口;
     * 参数: account = 账号;
     * 返回成功code是1
     */
    public static final String CHECKACCOUNT = "main/checkAccount.app";
    /**
     * 申请查询\实名认证接口;
     * 接口会校验是否已经提交过，与是第一次提交还是被拒绝后的重新提交
     * name=名字, idcard=身份证, idcardImg=手持身份证自拍(文件), idcardImg2=身份证证件 正面(文件), idcardImg3=身份证证件 背面(文件)
     */
    public static final String APPLYQUERY = "query/add/applyQuery.app";
    /**
     * 获取申请结果 获取结果后由客户端自己判断是否已经审核通过或是否有申请过 接口;
     * 已申请code是1 ,未申请code是0
     */
    public static final String GETAPPLYQUERY = "query/select/getApplyQuery.app";
    /**
     * 重新获取密码接口;
     * 成功code是1，data是结果;
     */
    public static final String FEEDBACKPASSWORDERROR = "query/modify/feedbackPasswordError.app";

    /**
     * 网吧平台新增接口 /  获取系统设置 里面有个参数 hideBannerFlag
     * * 隐藏Banner标记，默认0
     * 1为隐藏，0为不隐藏
     */
    public static final String GETSETTING = "setting/select/getSetting.app";

    /**
     * 第三方微信登录接口
     */
    public static final String WECHATLogin = "main/wx.app";

    /**
     * gps定位接口
     */
    public static final String GPSLOCATION = "user/modify/gpsLocation.app";
    /**
     * 获取附近的人接口
     */
    public static final String GETNEARBYUSER = "user/select/getNearbyUser.app";

    /**
     * 为用户重新创建腾讯云账号接口
     */
    public static final String CREATECLOUDTENCENTACCOUNT = "cloudTencent/modify/createCloudTencentAccount.app";

    /**
     * 导入腾讯云账号接口
     */
    public static final String ACCOUNTIMPORT = "cloudTencent/add/accountImport.app";

    /**
     * 获取腾讯云的userSig,要用POST请求,因为担心乱码问题
     */
    public static final String GETUSERSIG = "cloudTencent/getUsersig.app";
    /**
     * 修改群资料;
     */
    public static final String GROUPBASEINFO = "cloudTencent/modify/groupBaseInfo.app";
    /**
     * 根据昵称搜索用户;
     */
    public static final String SEARCHUSER = "user/select/searchUser.app";
    /**
     * 获取所有群组;
     */
    public static final String GETAPPIDGROUP = "cloudTencent/select/getAppidGroup.app";
    /**
     * 获取省接口;
     */
    public static final String GETPROVINCEBYPAGE = "area/select/getProvinceByPage.app";
    /**
     * 获取市接口;
     */
    public static final String GETCITYBYPAGE = "area/select/getCityByPage.app";
    /**
     * 游戏资讯接口;
     */
    public static final String GETNEWSTYPE = "news/select/getNewsType.app";
    /**
     * 获取游戏资讯列表接口;
     */
    public static final String GETBYPAGE = "news/select/getByPage.app";
}
