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
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/24.
 */
public class AddLikeGameAdapter extends BaseAdapter {
    private List<GameBean> list = null;
    private Context mContext;
    //1注册多选，0修改单选;
    private int tag;

    public AddLikeGameAdapter(Context mContext, List<GameBean> list, int tag) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.add_like_game_item, null);

        ImageView game_icon = OverallViewHolder.ViewHolder.get(view, R.id.game_icon);
        ImageView add_game_img = OverallViewHolder.ViewHolder.get(view, R.id.add_game_img);
        TextView game_name = OverallViewHolder.ViewHolder.get(view, R.id.game_name);
        TextView agency_name_tv = OverallViewHolder.ViewHolder.get(view, R.id.agency_name_tv);

        Glide.with(mContext).load(list.get(position).getImgUrl()).apply(getGlideOptions()).into(game_icon);
        game_name.setText(list.get(position).getName().isEmpty() ? "" : list.get(position).getName());
        agency_name_tv.setText(list.get(position).getInfo().isEmpty() ? "" : list.get(position).getInfo());

        if (tag == 0) {
            if (!list.get(position).isSlector())
                Glide.with(mContext).load(R.mipmap.add_blue_icon).into(add_game_img);
            else
                Glide.with(mContext).load(R.mipmap.reduce_icon).into(add_game_img);
        }

        return view;

    }
}
