package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.LocationAddressBean;
import laoyou.com.laoyou.listener.moveListener;
import laoyou.com.laoyou.utils.OverallViewHolder;

/**
 * Created by lian on 2017/9/16.
 */
public class IcSearchAdapter extends BaseAdapter {
    private List<LocationAddressBean> data;
    private LayoutInflater li;
    private moveListener move;

    public IcSearchAdapter(Context context, moveListener move) {
        this.move = move;
        li = LayoutInflater.from(context.getApplicationContext());
    }

    /**
     * 设置数据集
     *
     * @param data
     */
    public void setData(List<LocationAddressBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if (root == null)
            root = li.inflate(R.layout.address_item, null);

        LinearLayout move_layout = OverallViewHolder.ViewHolder.get(root, R.id.move_layout);
        TextView title = OverallViewHolder.ViewHolder.get(root, R.id.item_title);
        TextView text = OverallViewHolder.ViewHolder.get(root, R.id.item_text);

        title.setText(data.get(position).getTitle());
        text.setText(data.get(position).getText());
        move_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.onMove(data.get(position).getLatitude(), data.get(position).getLongitude(), data.get(position).getTitle());
            }
        });
        return root;
    }
}

