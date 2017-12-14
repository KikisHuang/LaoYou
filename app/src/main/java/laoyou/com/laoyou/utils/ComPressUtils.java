package laoyou.com.laoyou.utils;

import android.content.Context;

import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by lian on 2017/12/7.
 */
public class ComPressUtils {
    /**
     * 压缩
     */
    public static void Compress(Context context, List<String> list, OnCompressListener listener, int size) {
        Luban.with(context)
                .load(list)                                   // 传人要压缩的图片列表
                .ignoreBy(size)                               // 忽略不压缩图片的大小
//               .setTargetDir(FileManager.getSaveFilePath() + "gxLuban")// 设置压缩后文件存储位置
                .setCompressListener(listener).launch();    //启动压缩
    }
}
