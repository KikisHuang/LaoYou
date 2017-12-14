package com.tencent.qcloud.presentation.presenter;

import android.app.Activity;

import me.iwf.photopicker.PhotoPicker;

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
        //调用第三方图库选择
        PhotoPicker.builder()
                .setPhotoCount(num)//可选择图片数量
                .setShowCamera(true)//是否显示拍照按钮
                .setShowGif(false)//是否显示动态图
                .setPreviewEnabled(true)//是否可以预览
                .start(ac, PhotoPicker.REQUEST_CODE);
    }

    /**
     * 单选、多选;
     *
     * @param tag Activity回调标识符;
     * @param num 可选择图片的数量;
     *
     * @param ac
     */
    public static void getMULTIPLEPhotoTag(Activity ac, int num, int tag) {
        //调用第三方图库选择
        PhotoPicker.builder()
                .setPhotoCount(num)//可选择图片数量
                .setShowCamera(true)//是否显示拍照按钮
                .setShowGif(false)//是否显示动态图
                .setPreviewEnabled(true)//是否可以预览
                .start(ac, tag);
    }
}
