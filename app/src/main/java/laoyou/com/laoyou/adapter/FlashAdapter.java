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
import laoyou.com.laoyou.bean.GroupBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/18.
 */
public class FlashAdapter extends BaseAdapter {
    private List<GroupBean.GroupInfo> list = null;
    private Context mContext;

    public FlashAdapter(Context mContext, List<GroupBean.GroupInfo> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.flash_chat_item, null);

        ImageView head_img = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        TextView name = OverallViewHolder.ViewHolder.get(view, R.id.name_tv);
        TextView flash_num_tv = OverallViewHolder.ViewHolder.get(view, R.id.flash_num_tv);

        flash_num_tv.setText(gets(R.string.produce_msg) + ((int) list.get(position).getNextMsgSeq() - 1) + gets(R.string.info));
        name.setText(list.get(position).getName());

        Glide.with(mContext).load(list.get(position).getFaceUrl() == null || list.get(position).getFaceUrl().isEmpty() ? R.drawable.head_group : list.get(position).getFaceUrl()).into(head_img);

        return view;
    }

}
