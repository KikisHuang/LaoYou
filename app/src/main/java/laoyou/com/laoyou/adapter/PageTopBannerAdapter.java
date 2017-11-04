package laoyou.com.laoyou.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.listener.HomeListener;

import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;

/**
 * Created by kk on 2016/7/26.
 */
public class PageTopBannerAdapter extends PagerAdapter {
    private static final String TAG = "PageTopBannerAdapter";
    private List<PageTopBannerBean> list;
    private int tag;
    private HomeListener listener;
    public PageTopBannerAdapter(List<PageTopBannerBean> list, int tag, HomeListener listener) {
        this.list = list;
        this.tag = tag;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = list.get(position).getView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (tag) {
                        case 0:
//                          goBannerPage(context, list.get(position).getType(), list.get(position).getHttpUrl(), list.get(position).getValue());

                            listener.onOutSideGo(list.get(position).getHttpUrl());
                            break;
                        case 1:

                            break;

                    }


            }
        });
        container.addView(view);
        return list.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position).getView());
    }
}

