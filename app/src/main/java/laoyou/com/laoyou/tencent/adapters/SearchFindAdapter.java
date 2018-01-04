package laoyou.com.laoyou.tencent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.sdk.SearchBean;
import com.tencent.qcloud.ui.CircleImageView;

import java.util.List;

import laoyou.com.laoyou.R;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * 好友或群等资料摘要列表的adapter
 */
public class SearchFindAdapter extends BaseAdapter {


    private static final String TAG = "ProfileSummaryAdapter";
    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private List<SearchBean> list;
    private Context context;

    public SearchFindAdapter(Context context, int resource, List<SearchBean> objects) {
        this.context = context.getApplicationContext();
        resourceId = resource;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.des = (TextView) view.findViewById(R.id.description);
            view.setTag(viewHolder);
        }

        if (list.get(position).getHeadImgUrl() == null || list.get(position).getHeadImgUrl().isEmpty())
            viewHolder.avatar.setImageResource(R.drawable.head_other);
        else
            Glide.with(context).load(list.get(position).getHeadImgUrl()).apply(getGlideOptions()).into(viewHolder.avatar);

        viewHolder.name.setText(list.get(position).getName());
        return view;
    }


    public class ViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView des;
    }
}
