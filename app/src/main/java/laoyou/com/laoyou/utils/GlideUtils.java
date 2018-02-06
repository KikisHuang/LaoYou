package laoyou.com.laoyou.utils;

import com.bumptech.glide.request.RequestOptions;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/12/27.
 */
public class GlideUtils {
    /**
     * 获得Glide Options ;
     */
    public static RequestOptions getGlideOptions() {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.error(R.drawable.nodata_img);
        return options;
    }
}
