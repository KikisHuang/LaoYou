package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.utils.DeviceUtils;

/**
 * Created by lian on 2017/11/15.
 */
public class TopicTypeDetailsAdapter extends RecyclerView.Adapter<TopicTypeDetailsAdapter.MyViewHolder> {
    private Context context;
    private List<TopicTypeBean> list;
    private RecyclerViewOnItemClickListener listener;

    public TopicTypeDetailsAdapter(Context context, List<TopicTypeBean> list, RecyclerViewOnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.topic_type_details_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.bottom_line.setVisibility(position == list.size() ? View.VISIBLE : View.GONE);

        Glide.with(context).load(list.get(position).isLikeFlag() ? R.mipmap.heart_icon : R.mipmap.heart_black_icon).into(holder.like_img);

        holder.more_tv.setVisibility(View.GONE);
        holder.view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), "", "","");
            }
        });

        holder.like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.LikeClick(list.get(position).getId());
            }
        });

        PageHomeClick(holder, position);
        CommentClick(holder, position);
        holder.topic_layout.setVisibility(View.GONE);

        holder.nickname_tv.setText(list.get(position).getUserName());
        Glide.with(context).load(list.get(position).getHeadImgUrl()).into(holder.head_img);
        holder.time_tv.setText(list.get(position).getCreateTime());
        if (list.get(position).getMessageContent() == null || list.get(position).getMessageContent().isEmpty())
            holder.content_tv.setVisibility(View.GONE);
        else
            holder.content_tv.setText(list.get(position).getMessageContent());

        holder.like_tv.setText(list.get(position).getLikeCount() + "");
        holder.comment_tv.setText(list.get(position).getReplyCount() + "");

        if (list.get(position).getChatMessages().size() > 0) {
            switch (list.get(position).getChatMessages().size()) {
                case 1:
                    setCommentData(holder.name_tv1, list.get(position).getChatMessages().get(0).getUserName(), true);
                    setCommentData(holder.comment_tv1, list.get(position).getChatMessages().get(0).getMessageContent(), false);
                    holder.comment_layout1.setVisibility(View.VISIBLE);
                    holder.check_comment_tv.setVisibility(View.GONE);
                    break;
                case 2:
                    setCommentData(holder.name_tv1, list.get(position).getChatMessages().get(0).getUserName(), true);
                    setCommentData(holder.comment_tv1, list.get(position).getChatMessages().get(0).getMessageContent(), false);

                    setCommentData(holder.name_tv2, list.get(position).getChatMessages().get(1).getUserName(), true);
                    setCommentData(holder.comment_tv2, list.get(position).getChatMessages().get(1).getMessageContent(), false);

                    holder.comment_layout1.setVisibility(View.VISIBLE);
                    holder.comment_layout2.setVisibility(View.VISIBLE);
                    holder.check_comment_tv.setVisibility(View.GONE);
                    break;
                case 3:
                    setCommentData(holder.name_tv1, list.get(position).getChatMessages().get(0).getUserName(), true);
                    setCommentData(holder.comment_tv1, list.get(position).getChatMessages().get(0).getMessageContent(), false);

                    setCommentData(holder.name_tv2, list.get(position).getChatMessages().get(1).getUserName(), true);
                    setCommentData(holder.comment_tv2, list.get(position).getChatMessages().get(1).getMessageContent(), false);

                    setCommentData(holder.name_tv3, list.get(position).getChatMessages().get(2).getUserName(), true);
                    setCommentData(holder.comment_tv3, list.get(position).getChatMessages().get(2).getMessageContent(), false);

                    holder.comment_layout1.setVisibility(View.VISIBLE);
                    holder.comment_layout2.setVisibility(View.VISIBLE);
                    holder.comment_layout3.setVisibility(View.VISIBLE);

                    holder.check_comment_tv.setVisibility(View.VISIBLE);
                    break;
                default:
                    setCommentData(holder.name_tv1, list.get(position).getChatMessages().get(0).getUserName(), true);
                    setCommentData(holder.comment_tv1, list.get(position).getChatMessages().get(0).getMessageContent(), false);

                    setCommentData(holder.name_tv2, list.get(position).getChatMessages().get(1).getUserName(), true);
                    setCommentData(holder.comment_tv2, list.get(position).getChatMessages().get(1).getMessageContent(), false);

                    setCommentData(holder.name_tv3, list.get(position).getChatMessages().get(2).getUserName(), true);
                    setCommentData(holder.comment_tv3, list.get(position).getChatMessages().get(2).getMessageContent(), false);

                    holder.comment_layout1.setVisibility(View.VISIBLE);
                    holder.comment_layout2.setVisibility(View.VISIBLE);
                    holder.comment_layout3.setVisibility(View.VISIBLE);
                    holder.check_comment_tv.setVisibility(View.VISIBLE);
                    break;
            }
        } else
            holder.check_comment_tv.setVisibility(View.GONE);

        String url = list.get(position).getImgUrl();
        if (url != null && !url.isEmpty()) {
            int w = DeviceUtils.getWindowWidth(context) * 1 / 2;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w * 3 / 4);
            holder.content_img.setLayoutParams(lp);
            Glide.with(context).load(url).into(holder.content_img);
        } else
            holder.content_img.setVisibility(View.GONE);

    }

    private void CommentClick(MyViewHolder holder, final int position) {

        holder.check_comment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), "", "","");
            }
        });

        holder.comment_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getChatMessages().get(0).getId(), list.get(position).getChatMessages().get(0).getUserName(),list.get(position).getChatMessages().get(0).getMessageContent());
            }
        });
        holder.comment_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getChatMessages().get(1).getId(), list.get(position).getChatMessages().get(1).getUserName(),list.get(position).getChatMessages().get(1).getMessageContent());

            }
        });
        holder.comment_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getChatMessages().get(2).getId(), list.get(position).getChatMessages().get(2).getUserName(),list.get(position).getChatMessages().get(2).getMessageContent());

            }
        });

    }

    private void PageHomeClick(MyViewHolder holder, final int position) {
        holder.name_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getChatMessages().get(0).getUserId());
            }
        });
        holder.name_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getChatMessages().get(1).getUserId());
            }
        });
        holder.name_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getChatMessages().get(2).getUserId());
            }
        });
    }

    private void setCommentData(TextView tv, String str, boolean b) {
        if (b)
            tv.setText(str + "：");
        else
            tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nickname_tv, content_tv, time_tv, like_tv, comment_tv, check_comment_tv, bottom_line;
        private TextView comment_tv1, comment_tv2, comment_tv3;
        private TextView name_tv1, name_tv2, name_tv3;
        private ImageView head_img, more_tv, content_img, like_img;
        private LinearLayout topic_layout, comment_layout1, comment_layout2, comment_layout3, view_layout;

        public MyViewHolder(View view) {
            super(view);
            content_tv = (TextView) view.findViewById(R.id.content_tv);
            nickname_tv = (TextView) view.findViewById(R.id.nickname_tv);
            time_tv = (TextView) view.findViewById(R.id.time_tv);
            like_tv = (TextView) view.findViewById(R.id.like_tv);
            comment_tv = (TextView) view.findViewById(R.id.comment_tv);
            check_comment_tv = (TextView) view.findViewById(R.id.check_comment_tv);

            comment_tv1 = (TextView) view.findViewById(R.id.comment_tv1);
            comment_tv2 = (TextView) view.findViewById(R.id.comment_tv2);
            comment_tv3 = (TextView) view.findViewById(R.id.comment_tv3);

            name_tv1 = (TextView) view.findViewById(R.id.name_tv1);
            name_tv2 = (TextView) view.findViewById(R.id.name_tv2);
            name_tv3 = (TextView) view.findViewById(R.id.name_tv3);
            bottom_line = (TextView) view.findViewById(R.id.bottom_line);

            head_img = (ImageView) view.findViewById(R.id.head_img);
            like_img = (ImageView) view.findViewById(R.id.like_img);
            content_img = (ImageView) view.findViewById(R.id.content_img);
            more_tv = (ImageView) view.findViewById(R.id.more_tv);
            topic_layout = (LinearLayout) view.findViewById(R.id.topic_layout);

            comment_layout1 = (LinearLayout) view.findViewById(R.id.comment_layout_1);
            comment_layout2 = (LinearLayout) view.findViewById(R.id.comment_layout_2);
            comment_layout3 = (LinearLayout) view.findViewById(R.id.comment_layout_3);

            view_layout = (LinearLayout) view.findViewById(R.id.view_layout);

        }
    }
}
