package laoyou.com.laoyou.view;

/**
 * Created by lian on 2017/12/27.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bumptech.glide.Glide;

public class CustomImageView extends RoundAngleImageView {

    private String url;
    private boolean isAttachedToWindow;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (drawableUp != null) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onAttachedToWindow() {
        if (!isAttachedToWindow) {
            isAttachedToWindow = true;
            setImageUrl(url);
        }
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
//        Glide.with(getContext()).load(url).into(this);
//        isAttachedToWindow = false;
//        setImageBitmap(null);
        super.onDetachedFromWindow();
    }

    public void setImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            if (isAttachedToWindow) {
//                Picasso.with(getContext()).load(url).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(this);
                Glide.with(getContext()).load(url).into(this);
            }
        }
    }
}