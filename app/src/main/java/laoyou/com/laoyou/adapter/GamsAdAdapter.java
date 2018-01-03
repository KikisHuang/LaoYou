package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.listener.QueryListener;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.SynUtils.WswitchWay;

/**
 * Created by lian on 2017/11/18.
 */
public class GamsAdAdapter extends BaseAdapter {
    private List<PageTopBannerBean> list = null;
    private Context mContext;
    private QueryListener listener;

    public GamsAdAdapter(Context mContext, List<PageTopBannerBean> list, QueryListener listener) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.gams_ad_item, null);

        ImageView gams_img = OverallViewHolder.ViewHolder.get(view, R.id.gams_img);
        TextView tag_tv = OverallViewHolder.ViewHolder.get(view, R.id.tag_tv);
        TextView name_tv = OverallViewHolder.ViewHolder.get(view, R.id.name_tv);
        TextView info_tv = OverallViewHolder.ViewHolder.get(view, R.id.info_tv);
        TextView number_tv = OverallViewHolder.ViewHolder.get(view, R.id.number_tv);
        Button come_in_bt = OverallViewHolder.ViewHolder.get(view, R.id.come_in_bt);

        tag_tv.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

        Glide.with(mContext).load(list.get(position).getImgUrl()).into(gams_img);
        info_tv.setText(list.get(position).getTitle());
        name_tv.setText(list.get(position).getInfo());

        number_tv.setText(WswitchWay(list.get(position).getClickCount()) + "人在玩");
        come_in_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoOutSide(list.get(position).getHttpUrl());
            }
        });
        return view;
    }

}
