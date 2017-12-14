package laoyou.com.laoyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import laoyou.com.laoyou.fragment.CommentFragment;
import laoyou.com.laoyou.fragment.FindSonFragment;
import laoyou.com.laoyou.fragment.GameInformationFragment;
import laoyou.com.laoyou.utils.Fields;

/**
 * Created by lian on 2017/5/25.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> list;
    private int flag;

    public TabPagerAdapter(FragmentManager fm, List<String> strings, int flag) {
        super(fm);
        this.list = strings;
        this.flag = flag;
    }

    @Override
    public Fragment getItem(int position) {
        switch (flag) {
            case Fields.ISFIND:
                return FindSonFragment.setTag(position);
            case Fields.ISGAME:
                return GameInformationFragment.setTag(position);

            default:
                return CommentFragment.setTag(position, flag);
        }
//        return flag == Fields.ISFIND ? FindSonFragment.setTag(position) : CommentFragment.setTag(position, flag);
    }

    @Override
    public int getCount() {
        return list.size();//指定ViewPager的总页数
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
