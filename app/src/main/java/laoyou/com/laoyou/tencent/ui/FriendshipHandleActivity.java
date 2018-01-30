package laoyou.com.laoyou.tencent.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.TIMFriendResult;
import com.tencent.TIMValueCallBack;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.view.CircleImageView;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

public class FriendshipHandleActivity extends Activity implements View.OnClickListener {

    private String id;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship_handle);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        id = getIntent().getStringExtra("id");
        Glide.with(MyApplication.getContext()).load( getIntent().getStringExtra("face_url)")).apply(getGlideOptions()).into(avatar);
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(id);
        String word = getIntent().getStringExtra("word");
        TextView tvWord = (TextView) findViewById(R.id.word);
        tvWord.setText(word);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReject:
                FriendshipManagerPresenter.refuseFriendRequest(id, new TIMValueCallBack<TIMFriendResult>() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(FriendshipHandleActivity.this, getString(R.string.operate_fail), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(TIMFriendResult timFriendResult) {
                        Intent intent = new Intent();
                        intent.putExtra("operate", false);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
            case R.id.btnAgree:
                FriendshipManagerPresenter.acceptFriendRequest(id, new TIMValueCallBack<TIMFriendResult>() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(FriendshipHandleActivity.this, getString(R.string.operate_fail), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(TIMFriendResult timFriendResult) {
                        Intent intent = new Intent();
                        intent.putExtra("operate", true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
        }
    }
}
