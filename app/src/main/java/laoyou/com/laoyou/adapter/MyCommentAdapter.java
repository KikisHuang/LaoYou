package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.listener.HeartValueAndCommentListener;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.DateUtils.getMyDate;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/18.
 */
public class MyCommentAdapter extends BaseAdapter {
    private List<CommentBean> list = null;
    private Context mContext;
    private int tag;
    private HeartValueAndCommentListener listener;

    public MyCommentAdapter(Context mContext, List<CommentBean> list, int tag, HeartValueAndCommentListener listener) {
        this.mContext = mContext.getApplicationContext();
        this.list = list;
        this.tag = tag;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.my_comment_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        ImageView preview_img = OverallViewHolder.ViewHolder.get(view, R.id.preview_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView reply_to_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_to_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView reply_name_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_name_tv);
        TextView reply_content_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_content_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);
        LinearLayout reply_layout = OverallViewHolder.ViewHolder.get(view, R.id.reply_layout);


        Glide.with(mContext).load(list.get(position).getUserImg()).apply(getGlideOptions()).into(head_img);
        Glide.with(mContext).load(list.get(position).getHasImg()).apply(getGlideOptions()).into(preview_img);
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoHomePage(list.get(position).getUserId());
            }
        });
        nickname_tv.setText(list.get(position).getUserName());
        content_tv.setText(list.get(position).getMessageContent());
        time_tv.setText(getMyDate(list.get(position).getCreateTime()));

//        reply_layout.setVisibility(tag == 1 ? View.GONE : View.VISIBLE);

        reply_to_tv.setVisibility(tag == 1 ? View.GONE : View.VISIBLE);
        if (list.get(position).getReUser() != null && !list.get(position).getReUser().isEmpty()) {
            reply_layout.setVisibility(View.VISIBLE);
            reply_name_tv.setText(list.get(position).getReUser() + "：");
            reply_content_tv.setText(list.get(position).getReMessage());
        } else
            reply_layout.setVisibility(View.GONE);


        return view;
    }

}
