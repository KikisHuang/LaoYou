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

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.ChatMessages;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.DateUtils.getMyDate;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/18.
 */
public class TopicCommentAdapter extends BaseAdapter {
    private List<ChatMessages> list = null;
    private Context mContext;
    private TopicCommentListener listener;

    public TopicCommentAdapter(Context mContext, List<ChatMessages> list, TopicCommentListener listener) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.topic_comment_item, null);

        CircleImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView reply_tv = OverallViewHolder.ViewHolder.get(view, R.id.reply_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);
        TextView no_data_tv = OverallViewHolder.ViewHolder.get(view, R.id.no_data_tv);
        ImageView comment_img = OverallViewHolder.ViewHolder.get(view, R.id.comment_img);

        Glide.with(mContext).load(list.get(position).getUserImg()).apply(getGlideOptions()).into(head_img);

        nickname_tv.setText(list.get(position).getUserName());
        content_tv.setText(list.get(position).getMessageContent());
        time_tv.setText(getMyDate(list.get(position).getCreateTime()));
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.GoHomePage(list.get(position).getUserId());
            }
        });
        if (list.get(position).getReUser() != null) {
            reply_tv.setVisibility(View.VISIBLE);
            reply_tv.setText(gets(R.string.reply) + " " + list.get(position).getReUser() + "：");
        } else
            reply_tv.setVisibility(View.GONE);
        if (list.get(position).getChatImgs() != null && list.get(position).getChatImgs().size() > 0) {
            int w = (int) (DeviceUtils.getWindowWidth(mContext) * 1 / 3.5);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            comment_img.setLayoutParams(lp);
            Glide.with(mContext).load(list.get(position).getChatImgs().get(0).getUrl()).apply(getGlideOptions()).into(comment_img);
            comment_img.setVisibility(View.VISIBLE);
            comment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.GoPhotoPage(list.get(position).getChatImgs().get(0).getUrl());
                }
            });
        } else
            comment_img.setVisibility(View.GONE);


//        no_data_tv.setVisibility(position + 1 == list.size() ? View.VISIBLE : View.GONE);

        return view;
    }

}
