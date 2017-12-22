package laoyou.com.laoyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import laoyou.com.laoyou.bean.GameTypeBean;
import laoyou.com.laoyou.fragment.CommentFragment;
import laoyou.com.laoyou.fragment.FindSonFragment;
import laoyou.com.laoyou.fragment.GameInformationFragment;
import laoyou.com.laoyou.fragment.TopicTypeDetailsFragment;
import laoyou.com.laoyou.utils.Fields;

/**
 * Created by lian on 2017/5/25.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> list;
    private List<GameTypeBean> games;
    private int flag = Fields.ISGAME;
    private String id = "";

    public TabPagerAdapter(FragmentManager fm, List<String> strings, int flag) {
        super(fm);
        this.list = strings;
        this.flag = flag;
    }

    public TabPagerAdapter(FragmentManager fm, List<String> strings, int flag, String id) {
        super(fm);
        this.list = strings;
        this.flag = flag;
        this.id = id;
    }

    public TabPagerAdapter(FragmentManager fm, List<GameTypeBean> strings) {
        super(fm);
        this.games = strings;
    }

    @Override
    public Fragment getItem(int position) {
        switch (flag) {
            case Fields.ISFIND:
                return FindSonFragment.setTag(position);
            case Fields.ISGAME:
                return GameInformationFragment.setTag(position, games.get(position).getId());
            case Fields.ISTOPIC:
                return TopicTypeDetailsFragment.setTag(position,id);
            default:
                return CommentFragment.setTag(position, flag);
        }
    }

    @Override
    public int getCount() {
        return flag == Fields.ISGAME ? games.size() : list.size();//指定ViewPager的总页数
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public CharSequence getPageTitle(int position) {
        return flag == Fields.ISGAME ? games.get(position).getName() : list.get(position);
    }
}
