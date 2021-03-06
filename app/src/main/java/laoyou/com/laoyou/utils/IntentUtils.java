package laoyou.com.laoyou.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.activity.AddFriendActivity;
import laoyou.com.laoyou.activity.AddLikeGameActivity;
import laoyou.com.laoyou.activity.AddTopicTypeActivity;
import laoyou.com.laoyou.activity.AddressbookActivity;
import laoyou.com.laoyou.activity.AdvertisementActivity;
import laoyou.com.laoyou.activity.CertificationActivity;
import laoyou.com.laoyou.activity.ChangePassWordActivity;
import laoyou.com.laoyou.activity.CreateFlashChatActivity;
import laoyou.com.laoyou.activity.FlashChatActivity;
import laoyou.com.laoyou.activity.FlashChatBasicsActivity;
import laoyou.com.laoyou.activity.FlashChatGambitActivity;
import laoyou.com.laoyou.activity.FlashChatMemberActivity;
import laoyou.com.laoyou.activity.ForgetPasswordActivity;
import laoyou.com.laoyou.activity.GameInfoDetailsActivity;
import laoyou.com.laoyou.activity.GameInformationActivity;
import laoyou.com.laoyou.activity.HomePageActivity;
import laoyou.com.laoyou.activity.InternetCafActivity;
import laoyou.com.laoyou.activity.InternetCafLocationActivity;
import laoyou.com.laoyou.activity.LikeGameActivity;
import laoyou.com.laoyou.activity.LoginActivity;
import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.activity.MainActivity;
import laoyou.com.laoyou.activity.MyCommentActivity;
import laoyou.com.laoyou.activity.MyHomePageActivity;
import laoyou.com.laoyou.activity.MyNoticeActivity;
import laoyou.com.laoyou.activity.MyPhotoActivity;
import laoyou.com.laoyou.activity.NearbyFlashChatActivity;
import laoyou.com.laoyou.activity.OthersDetailsPageActivity;
import laoyou.com.laoyou.activity.OutSideActivity;
import laoyou.com.laoyou.activity.OverInfoActivity;
import laoyou.com.laoyou.activity.ParticipationActivity;
import laoyou.com.laoyou.activity.PhotoViewerActivity;
import laoyou.com.laoyou.activity.QueryActivity;
import laoyou.com.laoyou.activity.RegisterActivity;
import laoyou.com.laoyou.activity.ReleaseTopicActivity;
import laoyou.com.laoyou.activity.SendPhoneCodeActivity;
import laoyou.com.laoyou.activity.SettingActivity;
import laoyou.com.laoyou.activity.ShareMyQrCodeActivity;
import laoyou.com.laoyou.activity.TopicCircleActivity;
import laoyou.com.laoyou.activity.TopicCommentDetailsActivity;
import laoyou.com.laoyou.activity.TopicTypeDetailsActivity;
import laoyou.com.laoyou.activity.VideoPlayPageActivity;
import laoyou.com.laoyou.activity.WelcomeActivity;
import laoyou.com.laoyou.tencent.ui.BlackListActivity;
import laoyou.com.laoyou.tencent.ui.MessageNotifySettingActivity;

/**
 * Created by lian on 2017/10/25.
 */
public class IntentUtils {

    private static final String TAG = "IntentUtils";


    /**
     * 欢迎页面;
     *
     * @param context 上下文;
     */
    public static void goWelcomePage(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        startPage(context, intent);
    }

    /**
     * 广告页面;
     *
     * @param context 上下文;
     */
    public static void goAdvertisementPage(Context context) {
        Intent intent = new Intent(context, AdvertisementActivity.class);
        startPage(context, intent);
    }

    /**
     * 主页面;
     *
     * @param context 上下文;
     */
    public static void goMainPage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        startPage(context, intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginOperPage(Context context) {

        Intent intent = new Intent(context, LoginOperationActivity.class);
        startPage(context, intent);
    }

    /**
     * 网吧列表页面;
     *
     * @param context 上下文;
     */
    public static void goLocationPage(Context context, double longitude, double latitude) {
        Intent intent = new Intent(context, InternetCafLocationActivity.class);
        intent.putExtra("caf_latitude", latitude + "");
        intent.putExtra("caf_longitude", longitude + "");
        startPage(context, intent);
    }

    /**
     * 网吧详情页面;
     *
     * @param context 上下文;
     */
    public static void goInternetCafPage(Context context, String id) {
        Intent intent = new Intent(context, InternetCafActivity.class);
        intent.putExtra("caf_id", id);
        startPage(context, intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginPage(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET2);
    }

    /**
     * 实名认证页面;
     *
     * @param context 上下文;
     */
    public static void goCertificationPage(Context context) {
        Intent intent = new Intent(context, CertificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 忘记密码页面;
     *
     * @param context 上下文;
     */
    public static void goForGetPassPage(Context context, String phone) {

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra("forget_phone", phone);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET2);
    }

    /**
     * 我的评论、心动值页面;
     *
     * @param context 上下文;
     * @param flag    标识符 0评论,1心动值;
     */
    public static void goMyCommentPage(Context context, int flag) {

        Intent intent = new Intent(context, MyCommentActivity.class);
        intent.putExtra("Comment_of_HeartValue", String.valueOf(flag));
        startPage(context, intent);
    }

    /**
     * 话题圈页面;
     *
     * @param context 上下文;
     */
    public static void goTopicCirclePage(Context context) {

        Intent intent = new Intent(context, TopicCircleActivity.class);
        startPage(context, intent);
    }

    /**
     * 发布话题圈动态页面;
     *
     * @param context 上下文;
     * @param str
     */
    public static void goReleaseTopicPage(Context context, List<LocalMedia> photo, List<LocalMedia> video, List<String> str) {

        Intent intent = new Intent(context, ReleaseTopicActivity.class);
        intent.putParcelableArrayListExtra("Release_photo", (ArrayList<? extends Parcelable>) photo);
        intent.putParcelableArrayListExtra("Release_video", (ArrayList<? extends Parcelable>) video);
        intent.putStringArrayListExtra("Release_type", (ArrayList<String>) str);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET5);
    }

    /**
     * 发布话题圈类型选择页面;
     *
     * @param context 上下文;
     */
    public static void goAddTopicTypePage(Context context) {
        Intent intent = new Intent(context, AddTopicTypeActivity.class);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET5);
    }

    /**
     * 添加好友申请页面;
     *
     * @param context 上下文;
     */
    public static void goAddFriendPage(Context context, String id) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        intent.putExtra("apply_for_id", id);
        startPage(context, intent);
    }

    /**
     * 话题圈类型详情页面;
     *
     * @param context        上下文;
     * @param id
     * @param name           名称
     * @param followCount    参与人数
     * @param chatThemeCount 主题数
     */
    public static void goTopicTypeDetailsPage(Context context, String id, String name, String followCount, String chatThemeCount, String imgurl) {

        Intent intent = new Intent(context, TopicTypeDetailsActivity.class);
        intent.putExtra("Topic_Type_id", id);
        intent.putExtra("Topic_Type_name", name);
        intent.putExtra("Topic_Type_follow", followCount != null && !followCount.isEmpty() ? followCount : "0");
        intent.putExtra("Topic_Type_chat", chatThemeCount != null && !chatThemeCount.isEmpty() ? chatThemeCount : "0");
        intent.putExtra("Topic_Type_imgurl", imgurl != null && !imgurl.isEmpty() ? imgurl : "");
        startPage(context, intent);
    }

    /**
     * 我关注的话题圈页面;
     */
    public static void goMyNoticesPage(Context context) {
        Intent intent = new Intent(context, MyNoticeActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的相册页面;
     *
     * @param context 上下文;
     */
    public static void goMyPhotoPage(Context context, boolean isMe, String id) {

        Intent intent = new Intent(context, MyPhotoActivity.class);
        intent.putExtra("Photo_IsMe", isMe);
        intent.putExtra("Photo_id", id);
        startPage(context, intent);
    }

    /**
     * 照片查看器页面;
     *
     * @param context  上下文;
     * @param list     照片集合;
     * @param pos      从第几页显示;
     * @param Function 功能标识符 0 ：全开,1：只开启保存, 2：只开启删除, 3：全部关闭;;
     */
    public static void goPhotoViewerPage(Context context, List<String> list, int pos, int Function) {

        Intent intent = new Intent(context, PhotoViewerActivity.class);
        intent.putStringArrayListExtra("Photo_list", (ArrayList<String>) list);
        intent.putExtra("Photo_pos", String.valueOf(pos));
        intent.putExtra("Photo_Function", String.valueOf(Function));
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET4);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 视频查看器页面;
     *
     * @param context   上下文;
     * @param url       视频路径;
     * @param coverPath 封面图片;
     */
    public static void goVideoPlayerPage(Context context, String url, String coverPath) {

        Intent intent = new Intent(context, VideoPlayPageActivity.class);
        intent.putExtra("Video_url", String.valueOf(url));
        intent.putExtra("Video_coverPath", String.valueOf(coverPath));
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET4);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 黑名单页面;
     *
     * @param context 上下文;
     */
    public static void goBlackListPage(Context context) {

        Intent intent = new Intent(context, BlackListActivity.class);
        startPage(context, intent);
    }

    /**
     * 发送验证码页面;
     *
     * @param context 上下文;
     */
    public static void goSendPhoneCodePage(Context context) {

        Intent intent = new Intent(context, SendPhoneCodeActivity.class);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET4);
    }

    /**
     * 注册页面;
     *
     * @param context 上下文;
     */
    public static void goRegisterPage(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET2);
    }

    /**
     * 设置页面;
     *
     * @param context 上下文;
     */
    public static void goSettingPage(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的二维码页面;
     *
     * @param context 上下文;
     */
    public static void goShareMyQrCodePage(Context context,String headicon) {
        Intent intent = new Intent(context, ShareMyQrCodeActivity.class);
        intent.putExtra("my_head_id", headicon);
        startPage(context, intent);
    }

    /**
     * 消息提醒设置页面;
     *
     * @param context 上下文;
     */
    public static void goMessageNotifySettingPage(Context context) {
        Intent intent = new Intent(context, MessageNotifySettingActivity.class);
        startPage(context, intent);
    }

    /**
     * 修改密码页面;
     *
     * @param context 上下文;
     */
    public static void goChangePassPage(Context context) {
        Intent intent = new Intent(context, ChangePassWordActivity.class);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 游戏资讯页面;
     *
     * @param context 上下文;
     */
    public static void goGameInformationPage(Context context) {
        Intent intent = new Intent(context, GameInformationActivity.class);
        startPage(context, intent);
    }

    /**
     * 游戏资讯详情页面;
     *
     * @param context 上下文;
     * @param id
     */
    public static void goGameInfoDetailsPage(Context context, String id, String coverpath) {
        Intent intent = new Intent(context, GameInfoDetailsActivity.class);
        intent.putExtra("game_info_id", id);
        intent.putExtra("game_info_cover", coverpath);
        startPage(context, intent);
    }

    /**
     * 喜欢的游戏页面;
     *
     * @param context 上下文;
     */
    public static void goLikeGamePage(Context context, String id) {
        Intent intent = new Intent(context, LikeGameActivity.class);
        intent.putExtra("Like_games_id", id);
        startPage(context, intent);
    }

    /**
     * 添加喜欢的游戏页面;
     *
     * @param context 上下文;
     * @param tag     0、第一次添加，1、修改;
     */
    public static void goAddLikeGamePage(Context context, int tag) {
        Intent intent = new Intent(context, AddLikeGameActivity.class);
        intent.putExtra("add_like_game_tag", String.valueOf(tag));
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET5);
    }

    /**
     * 外链页面;
     *
     * @param context 上下文;
     */
    public static void goOutSidePage(Context context, String url) {
        Intent intent = new Intent(context, OutSideActivity.class);
        intent.putExtra("outside_url", url);
        startPage(context, intent);
    }

    /**
     * 闪聊页面;
     *
     * @param context 上下文;
     */
    public static void goFlashChatPage(Context context) {
        Intent intent = new Intent(context, FlashChatActivity.class);
        startPage(context, intent);
    }

    /**
     * 查询密码页面;
     *
     * @param context 上下文;
     */
    public static void goQueryPassPage(Context context) {
        Intent intent = new Intent(context, QueryActivity.class);
        startPage(context, intent);
    }

    /**
     * 附近的闪聊页面;
     *
     * @param context 上下文;
     */
    public static void goNearbyFlashChatPage(Context context) {
        Intent intent = new Intent(context, NearbyFlashChatActivity.class);
        startPage(context, intent);
    }

    /**
     * 创建闪聊页面1;
     *
     * @param context 上下文;
     */
    public static void goCreateFlashChatPage(Context context) {
        Intent intent = new Intent(context, CreateFlashChatActivity.class);
        startPage(context, intent);
    }

    /**
     * 创建闪聊页面2(填写基本资料);
     *
     * @param context 上下文;
     */
    public static void goFlashChatBasicsPage(Context context) {
        Intent intent = new Intent(context, FlashChatBasicsActivity.class);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 创建闪聊页面3(选择话题类型);
     *
     * @param context 上下文;
     * @param name    群名称
     * @param info    群简介
     */
    public static void goFlashChatGambitPage(Context context, String name, String info) {
        Intent intent = new Intent(context, FlashChatGambitActivity.class);
        intent.putExtra("basics_name", name);
        intent.putExtra("basics_info", info);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 创建闪聊页面4(选择群人数);
     *
     * @param context   上下文;
     * @param groupName
     * @param groupInfo
     * @param type
     */
    public static void goFlashChatMemberPage(Context context, String groupName, String groupInfo, String type) {
        Intent intent = new Intent(context, FlashChatMemberActivity.class);
        intent.putExtra("group_name", groupName);
        intent.putExtra("group_info", groupInfo);
        intent.putExtra("group_type", type);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 通讯录页面;
     *
     * @param context 上下文;
     */
    public static void goAddressBookPage(Context context) {
        Intent intent = new Intent(context, AddressbookActivity.class);
        startPage(context, intent);
    }

    /**
     * 完善资料页面;
     *
     * @param context 上下文;
     * @param sex
     */
    public static void goOverInfoPage(Context context, String phone, String pass, String code, int sex) {

        Intent intent = new Intent(context, OverInfoActivity.class);
        intent.putExtra("register_phone_of_headimg", phone);
        intent.putExtra("register_pass_of_name", pass);
        intent.putExtra("register_code", code);
        intent.putExtra("register_sex", sex + "");

//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET1);
    }

    /**
     * 我的个人主页页面;
     *
     * @param context 上下文;
     */
    public static void goMyHomePage(Context context) {

        Intent intent = new Intent(context, MyHomePageActivity.class);
//       startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET1);
    }

    /**
     * 他人主页页面;
     *
     * @param context 上下文;
     * @param id
     */
    public static void goOthersDetailsPage(Context context, String id) {
        Intent intent = new Intent(context, OthersDetailsPageActivity.class);
        intent.putExtra("Others_id", id);
        startPage(context, intent);
    }

    /**
     * 个人主页页面;
     *
     * @param context 上下文;
     */
    public static void goHomePage(Context context, String id, boolean isTencent) {

        Intent intent = new Intent(context, HomePageActivity.class);
        intent.putExtra("Page_Home_id", id);
        intent.putExtra("Page_Home_Tencent_Flag", isTencent + "");
//        startPage(context, intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 参与的人页面;
     *
     * @param context 上下文;
     * @param tag     标识, 1、参与的人  2、点赞列表 ;
     */
    public static void goParticipationPage(Context context, int tag, String id) {

        Intent intent = new Intent(context, ParticipationActivity.class);
        intent.putExtra("List_Tag", tag + "");
        intent.putExtra("List_id", id);
        startPage(context, intent);
    }

    /**
     * 话题圈详情评论页面;
     *
     * @param context 上下文;
     */
    public static void goTopicCommentDetailsPage(Context context, String id, String userId, String name, String content) {

        Intent intent = new Intent(context, TopicCommentDetailsActivity.class);
        intent.putExtra("Page_CommentDetails_id", id);
        intent.putExtra("Page_CommentDetails_userId", userId);
        intent.putExtra("Page_CommentDetails_name", name);
        intent.putExtra("Page_CommentDetails_content", content);
        startPage(context, intent);

    }

    /**
     * 跳转通用方法;
     *
     * @param context
     * @param intent
     */
    private static void startPage(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
//            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            context.startActivity(intent);
        else
            context.startActivity(intent);
    }

}
