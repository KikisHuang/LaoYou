package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.ReleaseTopicListener;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/18.
 */
public class PhotoGridAdapter extends BaseAdapter {
    private List<LocalMedia> list = null;
    private Context mContext;
    private ReleaseTopicListener listener;
    private int TopicType = -1;

    public PhotoGridAdapter(Context mContext, List<LocalMedia> list, ReleaseTopicListener listener) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.photo_grid_item, null);

        final ImageView photo_img = OverallViewHolder.ViewHolder.get(view, R.id.photo_img);
        ImageView remove_img = OverallViewHolder.ViewHolder.get(view, R.id.remove_img);
        if (position + 1 != list.size()) {
            remove_img.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) (DeviceUtils.getWindowWidth(mContext) * 1 / 3.4), (int) (DeviceUtils.getWindowWidth(mContext) * 1 / 3.4));
            photo_img.setLayoutParams(lp);
            Glide.with(mContext).load(list.get(position).getPath()).apply(getGlideOptions()).into(photo_img);

            remove_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRemove(position);
                }
            });
        } else if (list.get(position) == null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) (DeviceUtils.getWindowWidth(mContext) * 1 / 3.4), (int) (DeviceUtils.getWindowWidth(mContext) * 1 / 3.4));
            photo_img.setLayoutParams(lp);
            remove_img.setVisibility(View.GONE);
            Glide.with(mContext).load(R.mipmap.imaginary_line_add).into(photo_img);
            photo_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.AddPhoto();
                }
            });
        }
        return view;
    }

}
