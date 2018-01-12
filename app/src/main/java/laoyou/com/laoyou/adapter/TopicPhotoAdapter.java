package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/24.
 */
public class TopicPhotoAdapter extends BaseAdapter {
    private List<String> list = null;
    private Context mContext;
    private TopicCommentListener listener;

    public TopicPhotoAdapter(Context mContext, List<String> list, TopicCommentListener listener) {
        this.mContext = mContext.getApplicationContext();
        this.list = list;
        this.listener = listener;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {

        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.topic_photo_item, null);

        final ImageView head = OverallViewHolder.ViewHolder.get(view, R.id.img);

        //获取图片真正的宽高
        Glide.with(mContext)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .load(list.get(position))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        LinearLayout.LayoutParams lp;
                        if (width > height)
                            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((int) (DeviceUtils.getWindowWidth(mContext)) * 0.6 / 1));
                        else
                            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowHeight(mContext) * 0.7 / 1));


                        head.setLayoutParams(lp);
                        Glide.with(mContext).load(list.get(position)).apply(getGlideOptions()).into(head);

                        head.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.GoPhotoPage(list.get(position));
//                goPhotoViewerPage(mContext, list, position, 1);
                            }
                        });
                    }
                });


        return view;

    }
}
