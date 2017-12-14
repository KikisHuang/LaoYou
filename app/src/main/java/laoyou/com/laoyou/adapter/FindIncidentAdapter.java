package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.NearbyBean;

/**
 * Created by lian on 2017/11/18.
 */
public class FindIncidentAdapter extends BaseAdapter {
    private List<NearbyBean>list = null;
    private Context mContext;

    public FindIncidentAdapter(Context mContext, List<NearbyBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.find_incident_item, null);

        TextView content_tv = (TextView) view.findViewById(R.id.content_tv);
        TextView name = (TextView) view.findViewById(R.id.name_tv);
        TextView topic_title_tv = (TextView) view.findViewById(R.id.age_tv);
        TextView time_tv = (TextView) view.findViewById(R.id.distance_tv);
        TextView like_tv = (TextView) view.findViewById(R.id.user_head_img);
        TextView comment_tv = (TextView) view.findViewById(R.id.sex_img);
        TextView check_comment_tv = (TextView) view.findViewById(R.id.tag_layout);
        TextView title_tv = (TextView) view.findViewById(R.id.title_tv);

        ImageView head_img = (ImageView) view.findViewById(R.id.head_img);
        ImageView more_tv = (ImageView) view.findViewById(R.id.more_tv);
        LinearLayout topic_layout = (LinearLayout) view.findViewById(R.id.topic_layout);
        LinearLayout comment_layout = (LinearLayout) view.findViewById(R.id.comment_layout);

        return view;
    }

}
