package laoyou.com.laoyou.activity;

import android.app.Activity;
import android.os.Bundle;

import laoyou.com.laoyou.save.SPreferences;

import static laoyou.com.laoyou.utils.IntentUtils.goAdvertisementPage;
import static laoyou.com.laoyou.utils.IntentUtils.goMainPage;
import static laoyou.com.laoyou.utils.IntentUtils.goWelcomePage;

/**
 * Created by lian on 2018/1/30.
 * 引导页；
 */
public class GuideActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPreferences.getFirstStart())
            goWel();
        else
            goAdv();
    }

    private void goAdv() {
        goMainPage(this);
        goAdvertisementPage(this);
        finish();
    }

    private void goWel() {
        goWelcomePage(this);
        finish();
    }

}
