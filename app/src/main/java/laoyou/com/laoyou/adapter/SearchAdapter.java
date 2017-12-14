package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.listener.AddressBookListener;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/11/24.
 */
public class SearchAdapter extends BaseAdapter {
    private List<AddressBean> list = null;
    private Context mContext;
    private AddressBookListener listener;

    public SearchAdapter(Context mContext, List<AddressBean> list, AddressBookListener listener) {
        this.mContext = mContext.getApplicationContext();
        this.list = list;
        this.listener = listener;
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
        final AddressBean user = list.get(position);
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.create_group_item, null);


        ImageView head = OverallViewHolder.ViewHolder.get(view, R.id.head_img);

        Glide.with(mContext).load(list.get(position).getIdentifier() == null || list.get(position).getIdentifier().isEmpty() ? R.mipmap.head_img_icon : list.get(position).getFaceUrl().isEmpty() ? R.drawable.head_other : list.get(position).getFaceUrl()).into(head);
        TextView catalog = OverallViewHolder.ViewHolder.get(view, R.id.catalog);
        TextView name = OverallViewHolder.ViewHolder.get(view, R.id.name);
        TextView line_tv = OverallViewHolder.ViewHolder.get(view, R.id.line_tv);
        String n = list.get(position).getNickName();

        name.setText(n == null || n.isEmpty() ? list.get(position).getIdentifier() : list.get(position).getNickName());

        //根据position获取首字母作为目录catalog
        String catalogs = list.get(position).getLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(catalogs)) {
            line_tv.setVisibility(View.VISIBLE);

            catalog.setVisibility(View.VISIBLE);
            catalog.setText(user.getLetter().toUpperCase());
        } else
            catalog.setVisibility(View.GONE);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(list, 10293);
            }
        });
        return view;
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }

}
