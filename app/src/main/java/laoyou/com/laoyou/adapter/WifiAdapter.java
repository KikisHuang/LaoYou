package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/11/18.
 */
public class WifiAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ScanResult> list;
    private Context context;

    public WifiAdapter(Context context, List<ScanResult> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = null;
        view = inflater.inflate(R.layout.wifi_item, null);
        ScanResult scanResult = list.get(position);
        TextView textView = (TextView) view.findViewById(R.id.wifi_name);
        textView.setText(scanResult.SSID);

        ImageView signalStrenth = (ImageView) view.findViewById(R.id.wifi_icon);


        //判断信号强度，显示对应的指示图标
        if (Math.abs(scanResult.level) > 100) {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level1));
        } else if (Math.abs(scanResult.level) > 80) {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level2));
        } else if (Math.abs(scanResult.level) > 70) {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level3));
        } else if (Math.abs(scanResult.level) > 60) {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level3));
        } else if (Math.abs(scanResult.level) > 50) {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level4));
        } else {
            signalStrenth.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_level4));
        }

        return view;
    }

}
