package laoyou.com.laoyou.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.PageTopBannerBean;

import static laoyou.com.laoyou.utils.SynUtils.getLayout;


/**
 * Created by lian on 2017/5/3.
 */
public class homeViewPageUtils {

    private static final String TAG = "homeViewPageUtils";

    /**
     * ViewPager底部小圆点初始化;
     *
     * @param size              数据长度;
     * @param mImageViewDotList 底部圆点数据集合;
     * @param mLinearLayoutDot  底部圆点布局;
     * @param dotPosition       圆点起始的位置;
     */
    public static void setDot(int size, List<ImageView> mImageViewDotList, LinearLayout mLinearLayoutDot, int dotPosition) {
        mLinearLayoutDot.removeAllViews();
        mImageViewDotList.clear();
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayout(0, 15, 15);
        params.rightMargin = 20;
        //  for循环创建images.length个ImageView（小圆点）
        for (int i = 0; i < size; i++) {
            ImageView imageViewDot = new ImageView(MyApplication.getContext());
            imageViewDot.setLayoutParams(params);
            //  设置小圆点的背景为暗红图片
            imageViewDot.setBackgroundResource(R.drawable.dot_corners_false);
            mLinearLayoutDot.addView(imageViewDot);
            mImageViewDotList.add(imageViewDot);
        }
        //设置第一个小圆点图片背景为红色
        mImageViewDotList.get(dotPosition).setBackgroundResource(R.drawable.dot_corners_true);
    }

    /**
     * 主页viewPager;
     * ViewPager中图片初始化,根据flag判断需要显示的图片样式;
     *
     * @param images         图片数据源;
     * @param mImageViewList 返回的结果集;
     * @return
     */
    public static List<PageTopBannerBean> getTopImg(final List<PageTopBannerBean> images, List<PageTopBannerBean> mImageViewList, LayoutInflater inflater) {

        for (int i = 0; i < images.size() + 2; i++) {
            PageTopBannerBean pb = new PageTopBannerBean();
            View view = inflater.inflate(R.layout.top_img_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.top_img);

            int w = (int) (DeviceUtils.getWindowWidth(MyApplication.getContext()) * 3 / 4);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayout(2, w, (int) (w * 1.5 / 3));
            imageView.setLayoutParams(lp);

            if (imageView != null)
                imageView.setVisibility(View.VISIBLE);

            if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景

//                Glide.with(MyApplication.getContext()).load(images.get(images.size() - 1).getImgUrl()).apply(getGlideOptions()).into(imageView);

                pb.setType(images.get(images.size() - 1).getType());
                pb.setId(images.get(images.size() - 1).getId());
                pb.setView(view);
                pb.setImgUrl(images.get(images.size() - 1).getImgUrl());
                pb.setUid(images.get(images.size() - 1).getUid());
                pb.setHttpUrl(images.get(images.size() - 1).getHttpUrl());
                pb.setValue(images.get(images.size() - 1).getValue());

                mImageViewList.add(pb);
            } else if (i == images.size() + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
//                imageView = setImg(context);
//                Glide.with(MyApplication.getContext()).load(images.get(0).getImgUrl()).apply(getGlideOptions()).into(imageView);

                pb.setType(images.get(0).getType());
                pb.setId(images.get(0).getId());
                pb.setView(view);
                pb.setImgUrl(images.get(0).getImgUrl());
                pb.setUid(images.get(0).getUid());
                pb.setHttpUrl(images.get(0).getHttpUrl());
                pb.setValue(images.get(0).getValue());

                mImageViewList.add(pb);
            } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
//                imageView = setImg(context);
//                Glide.with(MyApplication.getContext()).load(images.get(i - 1).getImgUrl()).apply(getGlideOptions()).into(imageView);

                pb.setType(images.get(i - 1).getType());
                pb.setId(images.get(i - 1).getId());
                pb.setView(view);
                pb.setUid(images.get(i - 1).getUid());
                pb.setImgUrl(images.get(i - 1).getImgUrl());
                pb.setHttpUrl(images.get(i - 1).getHttpUrl());
                pb.setValue(images.get(i - 1).getValue());

                mImageViewList.add(pb);
            }
        }

        return mImageViewList;
    }
}
