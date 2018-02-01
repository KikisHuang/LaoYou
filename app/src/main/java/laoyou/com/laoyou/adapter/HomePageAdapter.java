package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.OverallViewHolder;
import laoyou.com.laoyou.view.NineGridlayout;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;

/**
 * Created by lian on 2017/11/18.
 */
public class HomePageAdapter extends BaseAdapter {
    private List<TopicTypeBean> list = null;
    private Context mContext;
    private RecyclerViewOnItemClickListener listener;

    public HomePageAdapter(Context mContext, List<TopicTypeBean> list, RecyclerViewOnItemClickListener listener) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.home_page_item, null);

        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);
        TextView like_tv = OverallViewHolder.ViewHolder.get(view, R.id.like_tv);
        TextView comment_tv = OverallViewHolder.ViewHolder.get(view, R.id.comment_tv);
        TextView check_comment_tv = OverallViewHolder.ViewHolder.get(view, R.id.check_comment_tv);
        TextView bottom_line = OverallViewHolder.ViewHolder.get(view, R.id.bottom_line);

        TextView comment_tv1 = OverallViewHolder.ViewHolder.get(view, R.id.comment_tv1);
        TextView comment_tv2 = OverallViewHolder.ViewHolder.get(view, R.id.comment_tv2);
        TextView comment_tv3 = OverallViewHolder.ViewHolder.get(view, R.id.comment_tv3);

        TextView name_tv1 = OverallViewHolder.ViewHolder.get(view, R.id.name_tv1);
        TextView name_tv2 = OverallViewHolder.ViewHolder.get(view, R.id.name_tv2);
        TextView name_tv3 = OverallViewHolder.ViewHolder.get(view, R.id.name_tv3);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        ImageView like_img = OverallViewHolder.ViewHolder.get(view, R.id.like_img);

        ImageView play_logo = OverallViewHolder.ViewHolder.get(view, R.id.play_logo);
        ImageView video_img = OverallViewHolder.ViewHolder.get(view, R.id.video_img);
        FrameLayout video_layouts = OverallViewHolder.ViewHolder.get(view, R.id.video_layouts);

//            content_img = (ImageView) OverallViewHolder.ViewHolder.get(view,R.id.content_img);
        ImageView more_tv = OverallViewHolder.ViewHolder.get(view, R.id.more_tv);
        LinearLayout topic_layout = OverallViewHolder.ViewHolder.get(view, R.id.topic_layout);
        LinearLayout reply_layout1 = OverallViewHolder.ViewHolder.get(view, R.id.reply_layout1);
        LinearLayout reply_layout2 = OverallViewHolder.ViewHolder.get(view, R.id.reply_layout2);
        LinearLayout reply_layout3 = OverallViewHolder.ViewHolder.get(view, R.id.reply_layout3);
        TextView reply_persen_tv1 = OverallViewHolder.ViewHolder.get(view, R.id.reply_persen_tv1);
        TextView reply_persen_tv2 = OverallViewHolder.ViewHolder.get(view, R.id.reply_persen_tv2);
        TextView reply_persen_tv3 = OverallViewHolder.ViewHolder.get(view, R.id.reply_persen_tv3);

        LinearLayout comment_layout1 = OverallViewHolder.ViewHolder.get(view, R.id.comment_layout_1);
        LinearLayout comment_layout2 = OverallViewHolder.ViewHolder.get(view, R.id.comment_layout_2);
        LinearLayout comment_layout3 = OverallViewHolder.ViewHolder.get(view, R.id.comment_layout_3);
        LinearLayout comment_layout = OverallViewHolder.ViewHolder.get(view, R.id.comment_layout);

        NineGridlayout content_img_layout = OverallViewHolder.ViewHolder.get(view, R.id.content_img_layout);

        LinearLayout view_layout = OverallViewHolder.ViewHolder.get(view, R.id.view_layout);

        bottom_line.setVisibility(position + 1 == list.size() ? View.VISIBLE : View.GONE);

        Glide.with(mContext).load(list.get(position).isLikeFlag() ? R.mipmap.on_like_icon : R.mipmap.off_like_icon).into(like_img);

        more_tv.setVisibility(View.GONE);
        view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), "", "", "");
            }
        });

        like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.LikeClick(list.get(position).getId());
            }
        });


        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getUserId());
            }
        });

        name_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getComments().get(0).get(3));
            }
        });
        name_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getComments().get(1).get(3));
            }
        });
        name_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getComments().get(2).get(3));
            }
        });


        check_comment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), "", "", "");
            }
        });

        comment_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getComments().get(0).get(3), list.get(position).getComments().get(0).get(0), list.get(position).getComments().get(0).get(2));
            }
        });
        comment_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getComments().get(1).get(3), list.get(position).getComments().get(1).get(0), list.get(position).getComments().get(1).get(2));

            }
        });
        comment_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoCommentPage(list.get(position).getId(), list.get(position).getComments().get(2).get(3), list.get(position).getComments().get(2).get(0), list.get(position).getComments().get(2).get(2));

            }
        });

        topic_layout.setVisibility(View.GONE);

        nickname_tv.setText(list.get(position).getUserName());
        Glide.with(mContext).load(list.get(position).getHeadImgUrl()).apply(getGlideOptions()).into(head_img);
        time_tv.setText(list.get(position).getCreateTime());
        if (list.get(position).getMessageContent() == null || list.get(position).getMessageContent().isEmpty())
            content_tv.setVisibility(View.GONE);
        else
            content_tv.setText(list.get(position).getMessageContent());

        like_tv.setText(list.get(position).getLikeCount() + "");
        comment_tv.setText(list.get(position).getReplyCount() + "");

        if (list.get(position).getVideos() != null && list.get(position).getVideoCover() != null) {
            int w = (int) (DeviceUtils.getWindowWidth(mContext) * 1 / 1.8);
            int h = (int) (w * 0.8 / 1);

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayout(1,w,h);
            video_img.setLayoutParams(lp);
            video_layouts.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(list.get(position).getVideoCover()).apply(getGlideOptions()).into(video_img);
            video_layouts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.GoVideoPage(list.get(position).getVideos(), list.get(position).getVideoCover());
                }
            });
        } else
            video_layouts.setVisibility(View.GONE);

        if (list.get(position).getComments() != null) {
            int size = list.get(position).getComments().size();
            if (size > 0) {
                comment_layout.setVisibility(View.VISIBLE);
                switch (size) {
                    case 1:
                        List<String> one = list.get(position).getComments().get(0);
                        setCommentData(name_tv1, one.get(0), true, one.get(1), reply_layout1, reply_persen_tv1);
                        setCommentData(comment_tv1, one.get(2), false, null, reply_layout1, reply_persen_tv1);

                        comment_layout1.setVisibility(View.VISIBLE);
                        comment_layout2.setVisibility(View.GONE);
                        comment_layout3.setVisibility(View.GONE);
                        check_comment_tv.setVisibility(View.GONE);
                        break;
                    case 2:
                        List<String> tone = list.get(position).getComments().get(0);
                        List<String> ttwo = list.get(position).getComments().get(1);
                        setCommentData(name_tv1, tone.get(0), true, tone.get(1), reply_layout1, reply_persen_tv1);
                        setCommentData(comment_tv1, tone.get(2), false, null, reply_layout1, reply_persen_tv1);

                        setCommentData(name_tv2, ttwo.get(0), true, ttwo.get(1), reply_layout2, reply_persen_tv2);
                        setCommentData(comment_tv2, ttwo.get(2), false, null, reply_layout3, reply_persen_tv3);

                        comment_layout1.setVisibility(View.VISIBLE);
                        comment_layout2.setVisibility(View.VISIBLE);
                        check_comment_tv.setVisibility(View.GONE);
                        comment_layout3.setVisibility(View.GONE);
                        break;
                    case 3:

                        List<String> thone = list.get(position).getComments().get(0);
                        List<String> thtwo = list.get(position).getComments().get(1);
                        List<String> three = list.get(position).getComments().get(2);

                        setCommentData(name_tv1, thone.get(0), true, thone.get(1), reply_layout1, reply_persen_tv1);
                        setCommentData(comment_tv1, thone.get(2), false, null, reply_layout1, reply_persen_tv1);

                        setCommentData(name_tv2, thtwo.get(0), true, thtwo.get(1), reply_layout2, reply_persen_tv2);
                        setCommentData(comment_tv2, thtwo.get(2), false, null, reply_layout2, reply_persen_tv2);

                        setCommentData(name_tv3, three.get(0), true, three.get(1), reply_layout3, reply_persen_tv3);
                        setCommentData(comment_tv3, three.get(2), false, null, reply_layout3, reply_persen_tv3);

                        comment_layout1.setVisibility(View.VISIBLE);
                        comment_layout2.setVisibility(View.VISIBLE);
                        comment_layout3.setVisibility(View.VISIBLE);

                        check_comment_tv.setVisibility(View.VISIBLE);
                        break;
                    default:
                        List<String> fone = list.get(position).getComments().get(0);
                        List<String> ftwo = list.get(position).getComments().get(1);
                        List<String> fthree = list.get(position).getComments().get(2);


                        setCommentData(name_tv1, fone.get(0), true, fone.get(1), reply_layout1, reply_persen_tv1);
                        setCommentData(comment_tv1, fone.get(2), false, null, reply_layout1, reply_persen_tv1);

                        setCommentData(name_tv2, ftwo.get(0), true, ftwo.get(1), reply_layout2, reply_persen_tv2);
                        setCommentData(comment_tv2, ftwo.get(2), false, null, reply_layout2, reply_persen_tv2);

                        setCommentData(name_tv3, fthree.get(0), true, fthree.get(1), reply_layout3, reply_persen_tv3);
                        setCommentData(comment_tv3, fthree.get(2), false, null, reply_layout3, reply_persen_tv3);

                        comment_layout1.setVisibility(View.VISIBLE);
                        comment_layout2.setVisibility(View.VISIBLE);
                        comment_layout3.setVisibility(View.VISIBLE);
                        check_comment_tv.setVisibility(View.VISIBLE);
                        break;
                }
            } else
                check_comment_tv.setVisibility(View.GONE);
        } else {
            comment_layout.setVisibility(View.GONE);
            check_comment_tv.setVisibility(View.GONE);
        }


        if (list.get(position).getPhotos() != null && list.get(position).getPhotos().size() > 0) {
            content_img_layout.setItemClickListener(listener);
            content_img_layout.setImagesData(list.get(position).getPhotos());
            content_img_layout.setVisibility(View.VISIBLE);
        } else
            content_img_layout.setVisibility(View.GONE);

        return view;
    }


    private void setCommentData(TextView tv, String str, boolean b, String s, LinearLayout layout, TextView rtv) {
        if (b) {
            if (s != null && !s.isEmpty()) {
                tv.setText(str);
                layout.setVisibility(View.VISIBLE);
                rtv.setText(s + "：");
            } else {
                layout.setVisibility(View.GONE);
                tv.setText(str + "：");
            }
        } else
            tv.setText(str);
    }

}
