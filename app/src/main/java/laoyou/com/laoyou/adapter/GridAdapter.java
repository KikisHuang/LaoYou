package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/18.
 */
public class GridAdapter extends BaseAdapter {
    private List<LikeListBean> list = null;
    private Context mContext;

    public GridAdapter(Context mContext, List<LikeListBean> list) {
        this.mContext = mContext.getApplicationContext();
        this.list = list;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(view, R.id.icon_img);
        ImageView more_img = OverallViewHolder.ViewHolder.get(view, R.id.more_img);
        if (position < 7) {
            Glide.with(mContext).load(list.get(position).getHeadImgUrl()).apply(getGlideOptions()).into(icon_img);
            more_img.setVisibility(View.GONE);
            icon_img.setVisibility(View.VISIBLE);
        } else {
            more_img.setVisibility(View.VISIBLE);
            icon_img.setVisibility(View.GONE);
        }

        return view;
    }

}
