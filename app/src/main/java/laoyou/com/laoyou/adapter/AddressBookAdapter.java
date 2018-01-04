package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.content.Intent;
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
import laoyou.com.laoyou.tencent.ui.ProfileActivity;
import laoyou.com.laoyou.utils.OverallViewHolder;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by lian on 2017/11/24.
 */
public class AddressBookAdapter extends BaseAdapter {
    private List<AddressBean> list = null;
    private Context mContext;

    public AddressBookAdapter(Context mContext, List<AddressBean> list) {
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
        final AddressBean user = list.get(position);
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.addressbook_item, null);

        TextView name = OverallViewHolder.ViewHolder.get(view, R.id.name);
        TextView line_tv = OverallViewHolder.ViewHolder.get(view, R.id.line_tv);
        TextView catalog = OverallViewHolder.ViewHolder.get(view, R.id.catalog);
        ImageView head = OverallViewHolder.ViewHolder.get(view, R.id.head_img);
        line_tv.setVisibility(View.GONE);
        click(view, position);

        //根据position获取首字母作为目录catalog
        String catalogs = list.get(position).getLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(catalogs)) {
            line_tv.setVisibility(View.VISIBLE);

            catalog.setVisibility(View.VISIBLE);
            catalog.setText(user.getLetter().toUpperCase());
        } else
            catalog.setVisibility(View.GONE);


        name.setText(this.list.get(position).getNickName());
        Glide.with(mContext).load(list.get(position).getFaceUrl() == null || list.get(position).getFaceUrl().isEmpty() ? R.drawable.head_other : list.get(position).getFaceUrl()).apply(getGlideOptions()).into(head);

        return view;

    }

    private void click(View view, final int pos) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("identify", list.get(pos).getIdentifier());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
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
