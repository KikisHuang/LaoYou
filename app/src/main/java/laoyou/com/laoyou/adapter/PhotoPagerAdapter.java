package laoyou.com.laoyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import laoyou.com.laoyou.fragment.PhotoViewerFragment;

/**
 * Created by lian on 2017/7/17.
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> list;

    public PhotoPagerAdapter(FragmentManager fm, List<String> urlList) {
        super(fm);
        this.list = urlList;
    }

    @Override
    public Fragment getItem(int position) {

        return PhotoViewerFragment.newInstance(list.get(position));//返回展示不同网络图片的
    }

    @Override
    public int getCount() {
        return list.size();//指定ViewPager的总页数
    }
}
