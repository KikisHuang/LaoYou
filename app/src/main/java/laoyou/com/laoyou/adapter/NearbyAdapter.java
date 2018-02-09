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
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.SynUtils.getDistanceKM;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/15.
 */
public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.MyViewHolder> {
    private Context context;
    private List<NearbyBean> list;
    private RecyclerViewOnItemClickListener listener;

    public NearbyAdapter(Context context, List<NearbyBean> list, RecyclerViewOnItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.nearby_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name_tv.setText(list.get(position).getName());
        holder.content_tv.setText(list.get(position).getAutograph());

        holder.sex_img.setText(list.get(position).getSex() == 1 ? gets(R.string.man) : gets(R.string.woman));
        holder.sex_img.setBackgroundResource(list.get(position).getSex() == 1 ? R.drawable.blue_corners : R.drawable.pink_corners);

        if (list.get(position).getGameImgs() != null && list.get(position).getImgsList() != null) {

            switch (list.get(position).getImgsList().size()) {

                case 1:
                    Glide.with(context).load(list.get(position).getImgsList().get(0)).into(holder.tags_one);
                    holder.tags_one.setVisibility(View.VISIBLE);
                    holder.tags_two.setVisibility(View.GONE);
                    holder.tags_three.setVisibility(View.GONE);
                    break;
                case 2:
                    Glide.with(context).load(list.get(position).getImgsList().get(0)).into(holder.tags_one);
                    Glide.with(context).load(list.get(position).getImgsList().get(1)).into(holder.tags_two);
                    holder.tags_one.setVisibility(View.VISIBLE);
                    holder.tags_two.setVisibility(View.VISIBLE);
                    holder.tags_three.setVisibility(View.GONE);
                    break;
                case 3:
                    Glide.with(context).load(list.get(position).getImgsList().get(0)).into(holder.tags_one);
                    Glide.with(context).load(list.get(position).getImgsList().get(1)).into(holder.tags_two);
                    Glide.with(context).load(list.get(position).getImgsList().get(2)).into(holder.tags_three);

                    holder.tags_one.setVisibility(View.VISIBLE);
                    holder.tags_two.setVisibility(View.VISIBLE);
                    holder.tags_three.setVisibility(View.VISIBLE);
                    break;
            }

        } else {
            holder.tags_one.setVisibility(View.GONE);
            holder.tags_two.setVisibility(View.GONE);
            holder.tags_three.setVisibility(View.GONE);
        }

        Glide.with(context).load(list.get(position).getHead_img_url()).apply(getGlideOptions()).into(holder.user_head_img);

        if (list.get(position).getDistance() > 1000)
            holder.distance_tv.setText(getDistanceKM(list.get(position).getDistance()));
        else if (list.get(position).getDistance() > 1 && list.get(position).getDistance() < 100)
            holder.distance_tv.setText(String.valueOf(list.get(position).getDistance()) + "m");
        else
            holder.distance_tv.setText(String.valueOf((int) (list.get(position).getDistance() * 0.001)) + "km");


        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.GoPageHome(list.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView content_tv, name_tv, age_tv, distance_tv, sex_img;
        private ImageView user_head_img, tags_one, tags_two, tags_three;
        private LinearLayout tag_layout, item_layout;

        public MyViewHolder(View view) {
            super(view);
            content_tv = (TextView) view.findViewById(R.id.content_tv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            age_tv = (TextView) view.findViewById(R.id.age_tv);
            distance_tv = (TextView) view.findViewById(R.id.distance_tv);
            user_head_img = (ImageView) view.findViewById(R.id.user_head_img);

            tags_one = (ImageView) view.findViewById(R.id.tags_one);
            tags_two = (ImageView) view.findViewById(R.id.tags_two);
            tags_three = (ImageView) view.findViewById(R.id.tags_three);

            sex_img = (TextView) view.findViewById(R.id.sex_img);
            tag_layout = (LinearLayout) view.findViewById(R.id.tag_layout);
            item_layout = (LinearLayout) view.findViewById(R.id.item_layout);

        }
    }
}
