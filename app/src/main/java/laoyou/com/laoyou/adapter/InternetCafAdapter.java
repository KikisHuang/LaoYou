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
public class InternetCafAdapter extends BaseAdapter {
    private List<CafCommentBean> list = null;
    private Context mContext;

    public InternetCafAdapter(Context mContext, List<CafCommentBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.internet_cat_comment_item, null);


        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView time_tv = OverallViewHolder.ViewHolder.get(view, R.id.time_tv);
        TextView comment_content = OverallViewHolder.ViewHolder.get(view, R.id.comment_content);
        TextView label_tv = OverallViewHolder.ViewHolder.get(view, R.id.label_tv);
        label_tv.setVisibility(position == 0 ? View.VISIBLE : View.GONE);


        nickname_tv.setText(list.get(position).getMcUserByUserId().getName());
        comment_content.setText(list.get(position).getInfo());
        time_tv.setText(list.get(position).getCreateTime());
        Glide.with(mContext).load(list.get(position).getMcUserByUserId().getHeadImgUrl()).apply(getGlideOptions()).into(head_img);

        return view;
    }

}
