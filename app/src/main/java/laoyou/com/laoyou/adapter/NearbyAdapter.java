package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.NearbyBean;

import static laoyou.com.laoyou.utils.SynUtils.getDistanceKM;

/**
 * Created by lian on 2017/11/15.
 */
public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.MyViewHolder> {
    private Context context;
    private List<NearbyBean> list;

    public NearbyAdapter(Context context, List<NearbyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.nearby_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name_tv.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getHeadImgUrl()).into(holder.user_head_img);

        if (list.get(position).getDistance() > 1000)
            holder.distance_tv.setText(getDistanceKM(list.get(position).getDistance() / 1000));
        else
            holder.distance_tv.setText(list.get(position).getDistance() + "m");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView content_tv, name_tv, age_tv, distance_tv, sex_img;
        private ImageView user_head_img;
        private LinearLayout tag_layout;

        public MyViewHolder(View view) {
            super(view);
            content_tv = (TextView) view.findViewById(R.id.content_tv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            age_tv = (TextView) view.findViewById(R.id.age_tv);
            distance_tv = (TextView) view.findViewById(R.id.distance_tv);
            user_head_img = (ImageView) view.findViewById(R.id.user_head_img);
            sex_img = (TextView) view.findViewById(R.id.sex_img);
            tag_layout = (LinearLayout) view.findViewById(R.id.tag_layout);

        }
    }
}
