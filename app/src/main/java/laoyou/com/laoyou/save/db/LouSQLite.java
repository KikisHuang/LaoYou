package laoyou.com.laoyou.save.db;

/**
 * Created by lian on 2018/1/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个通用的SQLite，通过简单的配置快速搭建一个数据库存储的方案；
 */

public final class LouSQLite extends SQLiteOpenHelper {

    private static final String TAG = "LouSQLite";
    private static LouSQLite INSTANCE;
    private final ICallBack callBack;


    private LouSQLite(Context context, ICallBack callBack) {
        super(context, callBack.getDatabaseName(), null, callBack.getVersion());
        this.callBack = callBack;
    }

    public synchronized static void init(@NonNull Context context, @NonNull ICallBack callBack) {
        if (INSTANCE == null)
            INSTANCE = new LouSQLite(context, callBack);
    }


    public static <T> void insert(String tableName, T entity) {
        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            INSTANCE.callBack.assignValuesByEntity(tableName, entity, values);
            db.insert(tableName, null, values);
            values.clear();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();

        }
    }


    public static <T> void insert(String tableName, List<T> entities) {
        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (T entity : entities) {
                INSTANCE.callBack.assignValuesByEntity(tableName, entity, values);
                db.insert(tableName, null, values);
                values.clear();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();

        }
    }

    public static <T> void update(String tableName, T entity, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            INSTANCE.callBack.assignValuesByEntity(tableName, entity, values);
            db.update(tableName, values, whereClause, whereArgs);
            values.clear();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();

        }
    }


    public static <T> List<T> query(String tableName, @NonNull String queryStr, @Nullable String[] whereArgs) {
        SQLiteDatabase db = INSTANCE.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, whereArgs);
        try {
            List<T> lists = new ArrayList<>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    T entity = INSTANCE.callBack.newEntityByCursor(tableName, cursor);
                    if (entity != null) {
                        lists.add(entity);
                    }
                } while (cursor.moveToNext());
            }
            return lists;
        } finally {
            cursor.close();
            db.close();

        }

    }

    public static void deleteFrom(String tableName) {

        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "DELETE FROM " + tableName;
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // delete的适用场合是涉及到删除的对象数量较少时。
    // 当删除多条数据时（例如：500条），通过循环的方式来一个一个的删除需要12s，而使用execSQL语句结合(delete from table id in("1", "2", "3"))的方式只需要50ms
    public static void delete(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(tableName, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();

        }
    }

    /*
     * 当操作数据较多时，直接使用sql语句或许效率更高
     *
     * 执行sql语句（例如: String sql = "delete from tableName where mac in ('24:71:89:0A:DD:82', '24:71:89:0A:DD:83','24:71:89:0A:DD:84')"）
     * 注意：db.execSQL文档中有说明"the SQL statement to be executed. Multiple statements separated by semicolons are not supported."，
     * 也就是说通过分号分割的多个statement操作是不支持的。
     *
     */
    public static void execSQL(String sql) {
        SQLiteDatabase db = INSTANCE.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static boolean deleteDatabase(Context context) {
        return context.deleteDatabase(PhraseEntry.DATABASE_NAME);
    }

    /**
     * 这个方法
     * 1、在第一次打开数据库的时候才会走
     * 2、在清除数据之后再次运行-->打开数据库，这个方法会走
     * 3、没有清除数据，不会走这个方法
     * 4、数据库升级的时候这个方法不会走
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> tablesSQL = callBack.createTablesSQL();
        for (String create_table : tablesSQL) {
            db.execSQL(create_table);
            Log.d(TAG, "create table " + "[ \n" + create_table + "\n ]" + " successful! ");
        }
    }

    /**
     * 1、第一次创建数据库的时候，这个方法不会走
     * 2、清除数据后再次运行(相当于第一次创建)这个方法不会走
     * 3、数据库已经存在，而且版本升高的时候，这个方法才会调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        callBack.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        Log.d(TAG, "oldVersion == " + oldVersion + "    newVersion == " + newVersion);
        //版本迭代升级;
        for (int j = oldVersion; j <= newVersion; j++) {
            switch (j) {
                case 2:
                    onCreate(sqLiteDatabase);
                    break;
            }
        }
    }

    /**
     * 执行数据库的降级操作
     * 1、只有新版本比旧版本低的时候才会执行
     * 2、如果不执行降级操作，会抛出异常
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onDowngrade(db, oldVersion, newVersion);
        Log.d(TAG, "onDowngrade == " + oldVersion + " newVersion == " + newVersion);
    }

    public interface ICallBack {
        String getDatabaseName();

        int getVersion();

        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

        List<String> createTablesSQL();

        <T> void assignValuesByEntity(String tableName, T entity, ContentValues values);

        <T> T newEntityByCursor(String tableName, Cursor cursor);
    }
}
