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

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/18.
 */
public class TopicCircletempAdapter extends BaseAdapter {
    private List<TopicBean> list = null;
    private Context mContext;

    public TopicCircletempAdapter(Context mContext, List<TopicBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.topic_circle_head, null);

        TextView name_tv = OverallViewHolder.ViewHolder.get(view, R.id.title_tv);
        TextView info_tv = OverallViewHolder.ViewHolder.get(view, R.id.info_tv);

        if (list.get(position).getChatThemeCount() != null && list.get(position).getFollowCount() != null)
            info_tv.setText(list.get(position).getFollowCount() + gets(R.string.participant) + " | " + list.get(position).getChatThemeCount() + gets(R.string.status_num));
        else
            info_tv.setText("0" + gets(R.string.participant) + " | " + "0" + gets(R.string.status_num));


        name_tv.setText(list.get(position).getName());

        return view;
    }

}
