package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.FlashTypeIconBean;
import laoyou.com.laoyou.listener.FlashChatGambitListener;
import laoyou.com.laoyou.utils.GlideUtils;


/**
 * Created by wiky on 2015/12/25.
 */
public class FlashChatGambitAdapter extends RecyclerView.Adapter<FlashChatGambitAdapter.ViewHolder> {
    private static final String TAG = "FlashChatGambitAdapter";
    private List<FlashTypeIconBean> list;
    private Context context;
    private FlashChatGambitListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView name_tv;
        private CircleImageView gambit_img;
        private LinearLayout gambit_layout;

        private ViewHolder(View v) {
            super(v);
            name_tv = (TextView) v.findViewById(R.id.name_tv);
            gambit_img = (CircleImageView) v.findViewById(R.id.gambit_img);
            gambit_layout = (LinearLayout) v.findViewById(R.id.gambit_layout);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FlashChatGambitAdapter(Context context, List<FlashTypeIconBean> list, FlashChatGambitListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flash_chat_gambit_item, parent, false);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.name_tv.setText(list.get(position).getTitle());

        Glide.with(context).load(list.get(position).getUrl()).apply(GlideUtils.getGlideOptions()).into(holder.gambit_img);

        holder.gambit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
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
