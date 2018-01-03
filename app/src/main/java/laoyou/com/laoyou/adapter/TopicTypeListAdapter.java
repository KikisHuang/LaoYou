package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/18.
 */
public class TopicTypeListAdapter extends BaseAdapter {
    private List<TopicBean> list = null;
    private Context mContext;

    public TopicTypeListAdapter(Context mContext, List<TopicBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.topic_type_list_item, null);

        TextView name = OverallViewHolder.ViewHolder.get(view, R.id.topic_type_name);

        name.setText(list.get(position).getName());

        return view;
    }

}
