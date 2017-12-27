package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/18.
 */
public class MyCommentAdapter extends BaseAdapter {
    private List<CommentBean> list = null;
    private Context mContext;

    public MyCommentAdapter(Context mContext, List<CommentBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.my_comment_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        ImageView preview_img = OverallViewHolder.ViewHolder.get(view, R.id.preview_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView reply_to_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_to_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView reply_name_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_name_tv);
        TextView reply_content_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_content_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);




        return view;
    }

}
