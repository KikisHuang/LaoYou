package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.CustomListener;

import static laoyou.com.laoyou.utils.SynUtils.gets;


/**
 * Created by wiky on 2015/12/25.
 */
public class TopicCircleAdapter extends RecyclerView.Adapter<TopicCircleAdapter.ViewHolder> {
    private static final String TAG = "FlashChatGambitAdapter";
    private List<TopicBean> list = null;
    private Context context;
    private CustomListener listener;
    private View v;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView name_tv, info_tv;

        private ViewHolder(View v) {
            super(v);
            name_tv = (TextView) v.findViewById(R.id.title_tv);
            info_tv = (TextView) v.findViewById(R.id.info_tv);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TopicCircleAdapter(Context context, List<TopicBean> list, CustomListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_circle_head, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (list.get(position).getChatThemeCount() != null && list.get(position).getFollowCount() != null)
            holder.info_tv.setText(list.get(position).getFollowCount() + gets(R.string.participant) + " | " + list.get(position).getChatThemeCount() + gets(R.string.status_num));
        else
            holder.info_tv.setText("0" + gets(R.string.participant) + " | " + "0" + gets(R.string.status_num));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> temp = new ArrayList<>();
                temp.add(list.get(position).getId());
                temp.add(list.get(position).getName());
                temp.add(list.get(position).getFollowCount());
                temp.add(list.get(position).getChatThemeCount());
                temp.add(list.get(position).getImgUrl());
                listener.onCustomResult(temp);
            }
        });

        holder.name_tv.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
