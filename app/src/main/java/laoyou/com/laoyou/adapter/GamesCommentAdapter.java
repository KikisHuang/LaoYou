package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/18.
 */
public class GamesCommentAdapter extends BaseAdapter {
    private List<CafCommentBean> list = null;
    private Context mContext;

    public GamesCommentAdapter(Context mContext, List<CafCommentBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.game_comment_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
//        ImageView preview_img = OverallViewHolder.ViewHolder.get(view, R.id.preview_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView content_tv = OverallViewHolder.ViewHolder.get(view, R.id.content_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);


        nickname_tv.setText(list.get(position).getMcUserByUserId().getName());
        content_tv.setText(list.get(position).getInfo());
        time_tv.setText(list.get(position).getCreateTime());
        Glide.with(mContext).load(list.get(position).getMcUserByUserId().getHeadImgUrl()).apply(getGlideOptions()).into(head_img);


       /* if (list.get(position).getChatThemeImgs() != null)
            Glide.with(mContext).load(list.get(position).getChatThemeImgs().get(0)).into(preview_img);

        Glide.with(mContext).load(list.get(position).getUserImg()).apply(getGlideOptions()).into(head_img);

        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoHomePage(list.get(position).getUserId());
            }
        });
        nickname_tv.setText(list.get(position).getUserName());
        content_tv.setText(list.get(position).getMessageContent());
        time_tv.setText(getMyDate(list.get(position).getCreateTime()));*/


        return view;
    }

}
