package laoyou.com.laoyou.utils;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by lian on 2017/5/18.
 * 动画工具类;
 */
public class AnimationUtil {
    private static final String TAG = "AnimationUtil";

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }


    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 从控件所在位置移动到控件的顶部
     *
     * @return
     */
    public static TranslateAnimation moveToViewTop() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 从控件的顶部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation1() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 欢迎界面平移动画;
     *
     * @return
     */
    public static TranslateAnimation WelcomeAnima() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -2.15f);
        mHiddenAction.setDuration(1000);
        mHiddenAction.setFillAfter(true);
        return mHiddenAction;
    }

    /**
     * 欢迎界面旋转动画;
     *
     * @return
     */

    public static RotateAnimation WelcomeAnimatwo() {
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(1300);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * 欢迎界面缩放动画;
     * 从IMG中心放大;
     *
     * @return
     */

    public static ScaleAnimation WelcomeAnimathree() {

        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(15000);
        return animation;
    }

    /**
     * 标题缩放动画;
     * 从view中心缩小;
     *
     * @return
     */
    public static ScaleAnimation TitleZoomAnima() {

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.7f, 1, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //3秒完成动画
        scaleAnimation.setDuration(200);
        return scaleAnimation;
    }

    /**
     * 闪烁动画
     *
     * @param view
     */
    public static void TwinkleAnima(View view) {
        //闪烁
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation1.setDuration(200);
        alphaAnimation1.setRepeatCount(1);
        view.setAnimation(alphaAnimation1);
        view.startAnimation(alphaAnimation1);
//        alphaAnimation1.setRepeatMode(Animation.REVERSE);
    }

    /**
     * 抖动动画
     *
     * @param view
     */
    public static ObjectAnimator ShakeAnima(View view) {
        return tada(view, 1f);
    }

    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(350);
    }

    public enum AnimationState {
        STATE_SHOW,
        STATE_HIDDEN
    }

    /**
     * 渐隐渐现动画
     *
     * @param view     需要实现动画的对象
     * @param view     需要实现显示的对象
     * @param state    需要实现的状态
     * @param duration 动画实现的时长（ms）
     */
    public static void showAndHiddenAnimation(final View view, final View v, AnimationState state, long duration) {
        float start = 0f;
        float end = 0f;
        if (state == AnimationState.STATE_SHOW) {
            end = 1f;
            view.setVisibility(View.VISIBLE);
        } else if (state == AnimationState.STATE_HIDDEN) {
            start = 1f;
            view.setVisibility(View.INVISIBLE);
        }
        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                if (v != null)
                    v.setVisibility(View.VISIBLE);
            }
        });
        view.setAnimation(animation);
        animation.start();
    }
}
