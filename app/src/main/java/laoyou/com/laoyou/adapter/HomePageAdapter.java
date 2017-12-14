package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/18.
 */
public class HomePageAdapter extends BaseAdapter {
    private List<String> list = null;
    private Context mContext;

    public HomePageAdapter(Context mContext, List<String> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.home_page_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        TextView name = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView flash_num_tv = OverallViewHolder.ViewHolder.get(view, R.id.flash_num_tv);
        LinearLayout topic_layout = OverallViewHolder.ViewHolder.get(view, R.id.topic_layout);
        LinearLayout comment_layout = OverallViewHolder.ViewHolder.get(view, R.id.comment_layout);
        TextView topic_title_tv = OverallViewHolder.ViewHolder.get(view, R.id.topic_title_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);
        TextView like_tv = OverallViewHolder.ViewHolder.get(view, R.id.like_tv);
        TextView comment_tv = OverallViewHolder.ViewHolder.get(view, R.id.comment_tv);
        ImageView more_tv = OverallViewHolder.ViewHolder.get(view, R.id.more_tv);
        TextView check_comment_tv = OverallViewHolder.ViewHolder.get(view, R.id.check_comment_tv);


        return view;
    }

}
