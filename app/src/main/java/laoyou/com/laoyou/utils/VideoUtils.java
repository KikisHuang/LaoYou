package laoyou.com.laoyou.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;

import java.util.HashMap;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by lian on 2017/12/29.
 */
public class VideoUtils {
    /**
     * 获取网络视频截图;
     *
     * @param url    路径
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap createVideoThumbnail(String url, int width, int height) {

        if (width >= 1080)
            width = Fields.MAXWIDTH;

        height = Fields.MAXHEIGHT;

        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14)
                retriever.setDataSource(url, new HashMap<String, String>());
            else
                retriever.setDataSource(url);

            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        if (bitmap == null)
            return getThumbnai(url);
        else
            return bitmap;
    }

    /**
     * 获取网络视频截图;
     *
     * @param path 路径
     * @return
     */
    public static Bitmap getThumbnai(String path) {
        Bitmap bitmap = null;
        //创建FFmpegMediaMetadataRetriever对象
        FFmpegMediaMetadataRetriever mm = new FFmpegMediaMetadataRetriever();
        try {
            //获取视频文件数据
            mm.setDataSource(path);
//获取文件缩略图
            bitmap = mm.getFrameAtTime();
        } catch (Exception e) {
        } finally {
            mm.release();
        }
        return bitmap;
    }
}
