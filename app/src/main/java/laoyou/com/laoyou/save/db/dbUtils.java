package laoyou.com.laoyou.save.db;

import android.util.Log;

import java.util.List;

import laoyou.com.laoyou.bean.ActiveBean;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.TopicTypeBean;

/**
 * Created by lian on 2018/1/17.
 */
public class dbUtils {

    private static final String TAG = "dbUtils";

    /**
     * 本地数据库缓存;
     */
    public static <T> void CacheDb(List<T> entities) {
        if (entities != null && entities.size() > 0) {
            if (entities.get(0) instanceof PageTopBannerBean)
                CachePageTopBanner((List<PageTopBannerBean>) entities);
            if (entities.get(0) instanceof ActiveBean)
                CachePageActiveGroup((List<ActiveBean>) entities);
            if (entities.get(0) instanceof TopicTypeBean)
                CachePageState((List<TopicTypeBean>) entities);
        }
    }

    /**
     * 首页在意的人;
     *
     * @param entities
     */
    private static void CachePageState(List<TopicTypeBean> entities) {

        List<TopicTypeBean> dds = LouSQLite.query(PhraseEntry.TABLE_NAME_STATE, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_STATE, null);
        Log.i(TAG, "TopicType ===" + dds.size());
        if (dds.size() > 0) {
            boolean del;
            for (TopicTypeBean dd : dds) {
                del = true;
                for (TopicTypeBean pt : entities) {
                    if (pt.getId().equals(dd.getId())) {
                        del = false;
                        break;
                    }
                }
                if (del)
                    LouSQLite.delete(PhraseEntry.TABLE_NAME_STATE, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{dd.getId()});
            }
            for (TopicTypeBean pt : entities) {

                if (LouSQLite.query(PhraseEntry.TABLE_NAME_STATE, PhraseEntry.SELECTFROM + " " + PhraseEntry.TABLE_NAME_STATE + PhraseEntry.WHERE + PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()}).size() > 0)
                    LouSQLite.update(PhraseEntry.TABLE_NAME_STATE, pt, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()});
                else
                    LouSQLite.insert(PhraseEntry.TABLE_NAME_STATE, pt);
            }
            Log.i(TAG, "State表单有旧数据，开始更新");
        } else {
            LouSQLite.insert(PhraseEntry.TABLE_NAME_STATE, entities);
            Log.i(TAG, "State表单没有数据，开始第一次填充");
        }
    }

    /**
     * 首页话题圈;
     *
     * @param entities
     */

    private static void CachePageActiveGroup(List<ActiveBean> entities) {

        List<ActiveBean> dds = LouSQLite.query(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_ACTIVE_GROUP, null);
        Log.i(TAG, "Active ===" + dds.size());
        if (dds.size() > 0) {
            boolean del;
            for (ActiveBean dd : dds) {
                del = true;
                for (ActiveBean pt : entities) {
                    if (pt.getId().equals(dd.getId())) {
                        del = false;
                        break;
                    }
                }
                if (del)
                    LouSQLite.delete(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{dd.getId()});
            }
            for (ActiveBean pt : entities) {

                if (LouSQLite.query(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, PhraseEntry.SELECTFROM + " " + PhraseEntry.TABLE_NAME_ACTIVE_GROUP + PhraseEntry.WHERE + PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()}).size() > 0)
                    LouSQLite.update(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, pt, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()});
                else
                    LouSQLite.insert(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, pt);
            }
            Log.i(TAG, "Topic表单有旧数据，开始更新");
        } else {
            LouSQLite.insert(PhraseEntry.TABLE_NAME_ACTIVE_GROUP, entities);
            Log.i(TAG, "Topic表单没有数据，开始第一次填充");
        }
    }

    /**
     * 首页banner;
     *
     * @param toplist
     */
    private static void CachePageTopBanner(List<PageTopBannerBean> toplist) {
        List<PageTopBannerBean> dds = LouSQLite.query(PhraseEntry.TABLE_NAME_BANNER, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_BANNER, null);
        Log.i(TAG, "Banner ===" + dds.size());
        if (dds.size() > 0) {
            boolean del;
            for (PageTopBannerBean dd : dds) {
                del = true;
                for (PageTopBannerBean pt : toplist) {
                    if (pt.getId().equals(dd.getId())) {
                        del = false;
                        break;
                    }
                }
                if (del)
                    LouSQLite.delete(PhraseEntry.TABLE_NAME_BANNER, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{dd.getId()});
            }
            for (PageTopBannerBean pt : toplist) {

                if (LouSQLite.query(PhraseEntry.TABLE_NAME_BANNER, PhraseEntry.SELECTFROM + " " + PhraseEntry.TABLE_NAME_BANNER + PhraseEntry.WHERE + PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()}).size() > 0)
                    LouSQLite.update(PhraseEntry.TABLE_NAME_BANNER, pt, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{pt.getId()});
                else
                    LouSQLite.insert(PhraseEntry.TABLE_NAME_BANNER, pt);
            }
            Log.i(TAG, "Banner表单有旧数据，开始更新");
        } else {
            LouSQLite.insert(PhraseEntry.TABLE_NAME_BANNER, toplist);
            Log.i(TAG, "Banner表单没有数据，开始第一次填充");
        }
    }
}
