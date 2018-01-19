package laoyou.com.laoyou.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2018/1/19.
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<ImageView> {

    //头像的最终大小
    private float mCustomFinalHeight;

    public CustomBehavior(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.getApplicationContext().obtainStyledAttributes(attrs, R.styleable.CustomBehavior);
            //获取缩小以后的大小
            mCustomFinalHeight = a.getDimension(R.styleable.CustomBehavior_finalHeight, 0);
            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        //告知监听的dependency是Button
        return dependency instanceof Toolbar;
    }

    @Override
    //当 dependency(Button)变化的时候，可以对child(TextView)进行操作
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {

        //让ImageView跟随toolbar垂直移动
        child.setY(dependency.getY() + dependency.getHeight() / 2 - mCustomFinalHeight / 2);

        //Log.e("wing","started x "+ mStartAvatarX + " currentX "+ x);
        child.setX(parent.getRight() - 200);

        return true;
    }
}
