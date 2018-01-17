package laoyou.com.laoyou.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.Phrase;
import laoyou.com.laoyou.save.db.LouSQLite;
import laoyou.com.laoyou.save.db.PhraseEntry;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private Button query_bt, delete_bt, update_bt, insert_bt;

    @Override
    protected void click() {
        query_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        update_bt.setOnClickListener(this);
        insert_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        query_bt = f(R.id.query_bt);
        delete_bt = f(R.id.delete_bt);
        update_bt = f(R.id.update_bt);
        insert_bt = f(R.id.insert_bt);
        // 初始化

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_bt:
                query();
                break;
            case R.id.delete_bt:
                delete();
                break;
            case R.id.update_bt:
                updata();
                break;
            case R.id.insert_bt:
                insert();
                break;

        }
    }

    private void insert() {
        // 插入一个数据到数据库
        Phrase phrase = new Phrase("青青子衿,悠悠我心");
        LouSQLite.insert(PhraseEntry.TABLE_NAME_PHRASE, phrase);
    }

    private void updata() {

        List<Phrase> ids = LouSQLite.query(PhraseEntry.TABLE_NAME_PHRASE, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_PHRASE, null);
        // 更新到数据库
        ids.get(0).setContent(ids.get(0).getContent() + " 嘿嘿嘿");
        LouSQLite.update(PhraseEntry.TABLE_NAME_PHRASE, ids.get(0), PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{ids.get(0).getId()});
    }

    private void delete() {
        List<Phrase> lists = LouSQLite.query(PhraseEntry.TABLE_NAME_PHRASE, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_PHRASE, null);
        // 从数据库中删除
        LouSQLite.delete(PhraseEntry.TABLE_NAME_PHRASE, PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{lists.get(0).getId()});
    }

    private void query() {
        //  查找
        List<Phrase> lists = LouSQLite.query(PhraseEntry.TABLE_NAME_PHRASE, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_PHRASE, null);

        List<Phrase> lis = LouSQLite.query(PhraseEntry.TABLE_NAME_PHRASE, PhraseEntry.SELECTFROM + " " + PhraseEntry.TABLE_NAME_PHRASE + " where " + PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{lists.get(0).getId()});

        if (lists.size() <= 0)
            Log.e(TAG, "phrase null ");
        else {
            for (Phrase rase : lists) {
                Log.e(TAG, "phrase content " + rase.getContent());
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
