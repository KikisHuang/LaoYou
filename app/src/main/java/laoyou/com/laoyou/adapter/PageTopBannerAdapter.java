package laoyou.com.laoyou.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.listener.HomeListener;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * Created by kk on 2016/7/26.
 */
public class PageTopBannerAdapter extends PagerAdapter {
    private static final String TAG = "PageTopBannerAdapter";
    private List<PageTopBannerBean> list;
    private int tag;
    private HomeListener listener;
    private int mChildCount = 0;


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

        ImageView imageView = (ImageView) view.findViewById(R.id.top_img);
        Glide.with(MyApplication.getContext()).load(list.get(position).getImgUrl()).apply(getGlideOptions()).into(imageView);

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


    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}

