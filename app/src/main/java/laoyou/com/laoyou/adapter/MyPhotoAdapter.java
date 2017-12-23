package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.listener.MyPhotoListener;
import laoyou.com.laoyou.utils.DeviceUtils;

/**
 * Created by lian on 2017/11/15.
 */
public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.MyViewHolder> {
    private Context context;
    private List<PhotoBean> list;
    private MyPhotoListener listener;

    public MyPhotoAdapter(Context context, List<PhotoBean> list, MyPhotoListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.my_photo_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowWidth(context) * 1 / 3.3));

        lp.topMargin = DeviceUtils.dip2px(context, 5);
//        if (position != 0 && position % 2 == 0) {
        lp.rightMargin = DeviceUtils.dip2px(context, 5);
//        }

        holder.photo_img.setLayoutParams(lp);

        if (position == 0 && list.get(0) == null)
            Glide.with(context).load(R.mipmap.photo_add_icon).centerCrop().into(holder.photo_img);
        else
            Glide.with(context).load(list.get(position).getUrl()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 15, 0, RoundedCornersTransformation.CornerType.ALL)).into(holder.photo_img);
        holder.photo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    listener.onAddPhoto();
                else
                    listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo_img;

        public MyViewHolder(View view) {
            super(view);
            photo_img = (ImageView) view.findViewById(R.id.photo_img);
        }
    }
}
