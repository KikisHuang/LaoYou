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
import laoyou.com.laoyou.bean.GameInfoBean;

/**
 * Created by lian on 2017/11/18.
 */
public class GameInformationAdapter extends BaseAdapter {
    private List<GameInfoBean> list = null;
    private Context mContext;

    public GameInformationAdapter(Context mContext, List<GameInfoBean> list) {
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
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.game_information_item, null);

        TextView information_titles_tv = (TextView) view.findViewById(R.id.information_titles_tv);
        TextView information_content_tv = (TextView) view.findViewById(R.id.information_content_tv);

        ImageView information_img = (ImageView) view.findViewById(R.id.information_img);

        information_titles_tv.setText(list.get(position).getTitle());
        information_content_tv.setText(list.get(position).getSummary());

        Glide.with(mContext).load(list.get(position).getLogo()).into(information_img);

        return view;
    }

}
