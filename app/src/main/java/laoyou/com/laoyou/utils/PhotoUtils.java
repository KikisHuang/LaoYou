package laoyou.com.laoyou.utils;

import android.app.Activity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/10/18.
 * 照片选择器utils;
 */
public class PhotoUtils {

    /**
     * 单选、多选;
     *
     * @param num 可选择图片的数量;
     * @param ac
     */
    public static void getMULTIPLEPhoto(Activity ac, int num) {

        PictureSelector.create(ac)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)// 样式
                .imageSpanCount(3) //每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(false)// 是否可播放音频 true or false
                .maxSelectNum(num)
                .minSelectNum(1)
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.8f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/Hoop_Photo")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


//        //调用第三方图库选择
//        PhotoPicker.builder()
//                .setPhotoCount(num)//可选择图片数量
//                .setShowCamera(true)//是否显示拍照按钮
//                .setShowGif(false)//是否显示动态图
//                .setPreviewEnabled(true)//是否可以预览
//                .start(ac, PhotoPicker.REQUEST_CODE);
    }

    /**
     * 单选;
     *
     * @param tag Activity回调标识符;
     * @param ac
     */
    public static void getMULTIPLEPhotoTag(Activity ac, int tag) {
      /*  //调用第三方图库选择
        PhotoPicker.builder()
                .setPhotoCount(num)//可选择图片数量
                .setShowCamera(true)//是否显示拍照按钮
                .setShowGif(false)//是否显示动态图
                .setPreviewEnabled(true)//是否可以预览
                .start(ac, tag);*/

        PictureSelector.create(ac)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)// 样式
                .imageSpanCount(3) //每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(false)// 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.8f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/Hoop_Photo")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(tag);//结果回调onActivityResult code
    }
}
