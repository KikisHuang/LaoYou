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
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/18.
 */
public class UserListAdapter extends BaseAdapter {
    private List<LikeListBean> list = null;
    private Context mContext;
    private int tag;

    public UserListAdapter(Context mContext, List<LikeListBean> list, int tag) {
        this.mContext = mContext.getApplicationContext();
        this.list = list;
        this.tag = tag;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, null);

        TextView title_name_tv = OverallViewHolder.ViewHolder.get(view, R.id.title_name_tv);
        TextView user_name = OverallViewHolder.ViewHolder.get(view, R.id.user_name);
        ImageView user_icon = OverallViewHolder.ViewHolder.get(view, R.id.user_icon);

        if (position == 0) {
            title_name_tv.setVisibility(View.VISIBLE);
            title_name_tv.setText(tag == 1 ? gets(R.string.player_user_list) : gets(R.string.like_list));
        } else
            title_name_tv.setVisibility(View.GONE);


        user_name.setText(list.get(position).getName());
        Glide.with(mContext).load(list.get(position).getHeadImgUrl()).apply(getGlideOptions()).into(user_icon);

        return view;
    }

}
