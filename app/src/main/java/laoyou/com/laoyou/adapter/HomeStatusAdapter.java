package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.NearbyBean;

/**
 * Created by lian on 2017/11/15.
 */
public class HomeStatusAdapter extends RecyclerView.Adapter<HomeStatusAdapter.MyViewHolder> {
    private Context context;
    private List<NearbyBean> list;

    public HomeStatusAdapter(Context context, List<NearbyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.home_status_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.name_tv.setText(list.get(position).getName());
//        Glide.with(context).load(list.get(position).getHeadImgUrl()).bitmapTransform(new CenterCrop(context),new RoundedCornersTransformation(context, 15, 0, RoundedCornersTransformation.CornerType.ALL)).into(holder.user_head_img);
//
//        if (list.get(position).getDistance() > 1000)
//            holder.distance_tv.setText(getDistanceKM(list.get(position).getDistance() / 1000 ));
//        else
//            holder.distance_tv.setText(list.get(position).getDistance() + "m");

        if (position == 0)
            holder.title_tv.setVisibility(View.VISIBLE);
        else
            holder.title_tv.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, topic_title_tv, content_tv, time_tv, like_tv, comment_tv, check_comment_tv, title_tv;
        private ImageView head_img, more_tv;
        private LinearLayout topic_layout, comment_layout;

        public MyViewHolder(View view) {
            super(view);
            content_tv = (TextView) view.findViewById(R.id.content_tv);
            name = (TextView) view.findViewById(R.id.name_tv);
            topic_title_tv = (TextView) view.findViewById(R.id.age_tv);
            time_tv = (TextView) view.findViewById(R.id.distance_tv);
            like_tv = (TextView) view.findViewById(R.id.user_head_img);
            comment_tv = (TextView) view.findViewById(R.id.sex_img);
            check_comment_tv = (TextView) view.findViewById(R.id.tag_layout);
            title_tv = (TextView) view.findViewById(R.id.title_tv);

            head_img = (ImageView) view.findViewById(R.id.head_img);
            more_tv = (ImageView) view.findViewById(R.id.more_tv);
            topic_layout = (LinearLayout) view.findViewById(R.id.topic_layout);
            comment_layout = (LinearLayout) view.findViewById(R.id.comment_layout);

        }
    }
}
