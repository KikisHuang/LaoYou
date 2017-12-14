package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.LikeListener;
import laoyou.com.laoyou.utils.OverallViewHolder;
import laoyou.com.laoyou.view.SwipeListLayout;

/**
 * Created by lian on 2017/11/24.
 */
public class LikeGameAdapter extends BaseAdapter {
    private List<GameBean> list = null;
    private Context mContext;
    private LikeListener listener;

    public LikeGameAdapter(Context mContext, List<GameBean> list, LikeListener listener) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.like_game_item, null);

        final SwipeListLayout layout = OverallViewHolder.ViewHolder.get(view, R.id.swipeListLayout);
        ImageView game_icon = OverallViewHolder.ViewHolder.get(view, R.id.game_icon);
        TextView game_name = OverallViewHolder.ViewHolder.get(view, R.id.game_name);
        TextView agency_name_tv = OverallViewHolder.ViewHolder.get(view, R.id.agency_name_tv);
        TextView num_tv = OverallViewHolder.ViewHolder.get(view, R.id.num_tv);
        TextView tv_delete = OverallViewHolder.ViewHolder.get(view, R.id.tv_delete);

        Glide.with(mContext).load(R.drawable.test_head_icon).bitmapTransform(new CenterCrop(mContext), new RoundedCornersTransformation(mContext, 15, 0, RoundedCornersTransformation.CornerType.ALL)).into(game_icon);


        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setStatus(SwipeListLayout.Status.Close, true);
                listener.onDeleteGame(position);
            }
        });


        return view;

    }
}
