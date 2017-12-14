package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/18.
 */
public class MyHeartValueAdapter extends BaseAdapter {
    private List<String> list = null;
    private Context mContext;

    public MyHeartValueAdapter(Context mContext, List<String> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.heart_value_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        TextView nickname_tv = OverallViewHolder.ViewHolder.get(view, R.id.nickname_tv);
        TextView num_tv = OverallViewHolder.ViewHolder.get(view, R.id.num_tv);


        return view;
    }

}
