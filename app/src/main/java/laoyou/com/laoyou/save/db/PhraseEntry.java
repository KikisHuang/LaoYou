package laoyou.com.laoyou.save.db;

/**
 * Created by lian on 2018/1/16.
 */
public class PhraseEntry {

    public static final String TABLE_NAME_PHRASE = "phrase";
    public static final String TABLE_NAME_FAVORITE = "favorite";
    public static final String TABLE_NAME_MSG_NOTICE = "msgnotice";
    public static final String TABLE_NAME_ACTIVE_GROUP = "activegroup";
    public static final String TABLE_NAME_BANNER = "banner";

    public static final String DATABASE_NAME = "hoop.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";
    public static final String SEP_COMMA = ",";
    public static final String WHERE = " where ";
    public static final String PRIMARYKEY = " PRIMARY KEY AUTOINCREMENT, ";
    public static final String L = " (";
    public static final String R = ")";


    public static final String TABLE_SCHEMA_PHRASE =
            PhraseEntry.CREATETABLE + TABLE_NAME_PHRASE + L +
                    PhraseEntry.ROW_ID + TYPE_INTEGER + PRIMARYKEY +
                    PhraseEntry.COLEUM_NAME_ID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NAME_CONTENT + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NAME_FAVORITE + TYPE_INTEGER +
                    R;

    public static final String TABLE_SCHEMA_FAVORITE =
            PhraseEntry.CREATETABLE + TABLE_NAME_FAVORITE + L +
                    PhraseEntry.ROW_ID + TYPE_INTEGER + PRIMARYKEY +
                    PhraseEntry.COLEUM_NAME_ID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NAME_CONTENT + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NAME_FAVORITE + TYPE_INTEGER +
                    R;
    /**
     * 消息通知状态表单创建;
     */
    public static final String TABLE_SCHEMA_MSG_NOTICE_STATUS =
            PhraseEntry.CREATETABLE + TABLE_NAME_MSG_NOTICE + L +
                    PhraseEntry.ROW_ID + TYPE_INTEGER + PRIMARYKEY +
                    PhraseEntry.COLEUM_NAME_ID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NOTICE_STATUS + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_NOTICE_C2C_STATUS + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_NOTICE_GROUP_STATUS + TYPE_INTEGER +
                    R;

    /**
     * 附近的闪聊表单创建;
     */
    public static final String TABLE_SCHEMA_ACTIVE_GROUP =
            PhraseEntry.CREATETABLE + TABLE_NAME_ACTIVE_GROUP + L +
                    PhraseEntry.ROW_ID + TYPE_INTEGER + PRIMARYKEY +
                    PhraseEntry.COLEUM_NAME_ID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_GROUPID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_TYPES + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_NAMES + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_INTRODUCTION + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_FACEURL + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_LASTMSGTIME + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_MEMBERNUM + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_MAXMEMBERNUM + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_APPLYJOINOPTION + TYPE_TEXT +
                    R;
    /**
     * 首页banner表单创建;
     */
    public static final String TABLE_SCHEMA_BEANNER =
            PhraseEntry.CREATETABLE + TABLE_NAME_BANNER + L +
                    PhraseEntry.ROW_ID + TYPE_INTEGER + PRIMARYKEY +
                    PhraseEntry.COLEUM_NAME_ID + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_HTTPURL + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_IMGURL + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_REMARKS + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_TYPE + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_UID + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_CLICKCOUNT + TYPE_INTEGER + SEP_COMMA +
                    PhraseEntry.COLEUM_INFO + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_TITLE + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_VALUE + TYPE_TEXT + SEP_COMMA +
                    PhraseEntry.COLEUM_SHOWPOSITION + TYPE_INTEGER +
                    R;


    public static final String ROW_ID = "rowid";
    public static final String COLEUM_NAME_ID = "id";

    public static final String COLEUM_HTTPURL = "httpUrl";
    public static final String COLEUM_IMGURL = "imgUrl";
    public static final String COLEUM_REMARKS = "remarks";
    public static final String COLEUM_TYPE = "type";
    public static final String COLEUM_TYPES = "Type";
    public static final String COLEUM_NAMES = "Name";
    public static final String COLEUM_INTRODUCTION = "Introduction";
    public static final String COLEUM_FACEURL = "FaceUrl";
    public static final String COLEUM_LASTMSGTIME = "LastMsgTime";
    public static final String COLEUM_MEMBERNUM = "MemberNum";
    public static final String COLEUM_MAXMEMBERNUM = "MaxMemberNum";
    public static final String COLEUM_GROUPID = "GroupId";
    public static final String COLEUM_APPLYJOINOPTION = "ApplyJoinOption";
    public static final String COLEUM_UID = "uid";
    public static final String COLEUM_CLICKCOUNT = "clickCount";
    public static final String COLEUM_INFO = "info";
    public static final String COLEUM_TITLE = "title";
    public static final String COLEUM_VALUE = "value";

    public static final String COLEUM_SHOWPOSITION = "showPosition";

    public static final String COLEUM_NOTICE_STATUS = "notice";
    public static final String COLEUM_NOTICE_C2C_STATUS = "chat";
    public static final String COLEUM_NOTICE_GROUP_STATUS = "chatgroups";
    public static final String COLEUM_NAME_CONTENT = "content";
    public static final String COLEUM_NAME_FAVORITE = "favorite";
    public static final String SELECTFROM = "select * from ";
    public static final String CREATETABLE = "CREATE TABLE ";


}
