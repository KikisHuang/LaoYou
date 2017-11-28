package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/24.
 */
public class InfoAdapter extends BaseAdapter {
    private List<AddressBean> list = null;
    private Context mContext;

    public InfoAdapter(Context mContext, List<AddressBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.add_info_item, null);

        ImageView head = OverallViewHolder.ViewHolder.get(view, R.id.head_img);

        Glide.with(mContext).load(list.get(position).getIdentifier() == null || list.get(position).getIdentifier().isEmpty() ? R.mipmap.head_img_icon : list.get(position).getFaceUrl().isEmpty() ? R.drawable.head_other : list.get(position).getFaceUrl()).into(head);

        return view;

    }
}
