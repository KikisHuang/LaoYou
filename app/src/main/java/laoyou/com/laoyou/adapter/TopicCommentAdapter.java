package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicCommentBean;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/18.
 */
public class TopicCommentAdapter extends BaseAdapter {
    private List<TopicCommentBean.ChatMessagesBean> list = null;
    private Context mContext;
    private TopicCommentListener listener;

    public TopicCommentAdapter(Context mContext, List<TopicCommentBean.ChatMessagesBean> list, TopicCommentListener listener) {
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

        Glide.with(mContext).load(list.get(position).getMcUserByUserId().getHeadImgUrl()).into(head_img);
        nickname_tv.setText(list.get(position).getMcUserByUserId().getName());
        content_tv.setText(list.get(position).getMessageContent());
        time_tv.setText(String.valueOf(list.get(position).getModifyTime()));
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.GoHomePage(list.get(position).getMcUserByUserId().getId());
            }
        });
        reply_tv.setVisibility(View.GONE);
        switch (list.get(position).getMessageTypeFlag()) {
            case 0:
                break;
            case 100:
                reply_tv.setVisibility(View.VISIBLE);
                break;
            default:
        }


        return view;
    }

}