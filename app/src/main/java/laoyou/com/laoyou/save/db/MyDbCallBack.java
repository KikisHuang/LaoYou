package laoyou.com.laoyou.save.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.List;

import laoyou.com.laoyou.bean.ActiveBean;
import laoyou.com.laoyou.bean.Notice;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.Phrase;


/**
 * Created by lian on 2018/1/16.
 */
public class MyDbCallBack implements LouSQLite.ICallBack {

    public MyDbCallBack() {
    }

    @Override
    public List<String> createTablesSQL() {
        return Arrays.asList(
                PhraseEntry.TABLE_SCHEMA_PHRASE,
                PhraseEntry.TABLE_SCHEMA_FAVORITE,
                PhraseEntry.TABLE_SCHEMA_MSG_NOTICE_STATUS,
                PhraseEntry.TABLE_SCHEMA_BEANNER,
                PhraseEntry.TABLE_SCHEMA_ACTIVE_GROUP
        );
    }

    @Override
    public String getDatabaseName() {
        return PhraseEntry.DATABASE_NAME;
    }

    @Override
    public int getVersion() {
        return PhraseEntry.DATABASE_VERSION;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 0:
                db.execSQL(PhraseEntry.TABLE_SCHEMA_FAVORITE); // 升级操作；
            case 1:
                break;
            default:
                break;
        }
    }


    @Override
    public <T> void assignValuesByEntity(String tableName, T t, ContentValues values) {

        switch (tableName) {
            case PhraseEntry.TABLE_NAME_PHRASE:
                if (t instanceof Phrase) {
                    Phrase phrase = (Phrase) t;
                    values.put(PhraseEntry.COLEUM_NAME_ID, phrase.getId());
                    values.put(PhraseEntry.COLEUM_NAME_CONTENT, phrase.getContent());
                    values.put(PhraseEntry.COLEUM_NAME_FAVORITE, phrase.getFavorite());
                }
                break;
            case PhraseEntry.TABLE_NAME_FAVORITE:
                if (t instanceof Phrase) {
                    Phrase phrase = (Phrase) t;
                    values.put(PhraseEntry.COLEUM_NAME_ID, phrase.getId());
                    values.put(PhraseEntry.COLEUM_NAME_CONTENT, phrase.getContent());
                    values.put(PhraseEntry.COLEUM_NAME_FAVORITE, phrase.getFavorite());
                }
                break;
            case PhraseEntry.TABLE_NAME_MSG_NOTICE:
                if (t instanceof Notice) {
                    Notice notice = (Notice) t;
                    values.put(PhraseEntry.COLEUM_NAME_ID, notice.getId());
                    values.put(PhraseEntry.COLEUM_NOTICE_STATUS, notice.getNotice());
                    values.put(PhraseEntry.COLEUM_NOTICE_C2C_STATUS, notice.getC2c());
                    values.put(PhraseEntry.COLEUM_NOTICE_GROUP_STATUS, notice.getGroup());
                }
                break;
            case PhraseEntry.TABLE_NAME_BANNER:
                if (t instanceof PageTopBannerBean) {
                    PageTopBannerBean banner = (PageTopBannerBean) t;
                    values.put(PhraseEntry.COLEUM_NAME_ID, banner.getId());
                    values.put(PhraseEntry.COLEUM_HTTPURL, banner.getHttpUrl());
                    values.put(PhraseEntry.COLEUM_IMGURL, banner.getImgUrl());
                    values.put(PhraseEntry.COLEUM_REMARKS, banner.getRemarks());
                    values.put(PhraseEntry.COLEUM_TYPE, banner.getType());
                    values.put(PhraseEntry.COLEUM_UID, banner.getUid());
                    values.put(PhraseEntry.COLEUM_CLICKCOUNT, banner.getClickCount());
                    values.put(PhraseEntry.COLEUM_INFO, banner.getInfo());
                    values.put(PhraseEntry.COLEUM_TITLE, banner.getTitle());
                    values.put(PhraseEntry.COLEUM_VALUE, banner.getValue());
                    values.put(PhraseEntry.COLEUM_SHOWPOSITION, banner.getShowPosition());
                }
                break;
            case PhraseEntry.TABLE_NAME_ACTIVE_GROUP:
                if (t instanceof ActiveBean) {
                    ActiveBean ac = (ActiveBean) t;
                    values.put(PhraseEntry.COLEUM_NAME_ID, ac.getId());
                    values.put(PhraseEntry.COLEUM_GROUPID, ac.getGroupId());
                    values.put(PhraseEntry.COLEUM_TYPES, ac.getType());
                    values.put(PhraseEntry.COLEUM_NAMES, ac.getName());
                    values.put(PhraseEntry.COLEUM_INTRODUCTION, ac.getIntroduction());
                    values.put(PhraseEntry.COLEUM_FACEURL, ac.getFaceUrl());
                    values.put(PhraseEntry.COLEUM_LASTMSGTIME, ac.getLastMsgTime());
                    values.put(PhraseEntry.COLEUM_MEMBERNUM, ac.getMemberNum());
                    values.put(PhraseEntry.COLEUM_MAXMEMBERNUM, ac.getMaxMemberNum());
                    values.put(PhraseEntry.COLEUM_APPLYJOINOPTION, ac.getApplyJoinOption());
                }
                break;
        }
    }

    @Override
    public Object newEntityByCursor(String tableName, Cursor cursor) {
        switch (tableName) {
            case PhraseEntry.TABLE_NAME_PHRASE:
                return new Phrase(
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_CONTENT)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_FAVORITE))
                );
            case PhraseEntry.TABLE_NAME_FAVORITE:
                return new Phrase(
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_CONTENT)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_FAVORITE))
                );
            case PhraseEntry.TABLE_NAME_MSG_NOTICE:
                return new Notice(
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_ID)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_NOTICE_STATUS)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_NOTICE_C2C_STATUS)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_NOTICE_GROUP_STATUS))
                );
            case PhraseEntry.TABLE_NAME_BANNER:
                return new PageTopBannerBean(
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_HTTPURL)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_IMGURL)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_REMARKS)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_UID)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_CLICKCOUNT)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_INFO)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_TITLE)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_VALUE)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_SHOWPOSITION))
                );
            case PhraseEntry.TABLE_NAME_ACTIVE_GROUP:
                return new ActiveBean(
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_GROUPID)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_TYPES)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_NAMES)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_INTRODUCTION)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_FACEURL)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_LASTMSGTIME)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_MEMBERNUM)),
                        cursor.getInt(cursor.getColumnIndex(PhraseEntry.COLEUM_MAXMEMBERNUM)),
                        cursor.getString(cursor.getColumnIndex(PhraseEntry.COLEUM_APPLYJOINOPTION))
                );

        }

        return null;
    }
}
