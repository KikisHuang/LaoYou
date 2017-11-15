package laoyou.com.laoyou.utils;


import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lian on 2017/11/14.
 */
public class AnimaImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        modifyAvatarDependingDependencyState(child, dependency);
        return true;
    }

    private void modifyAvatarDependingDependencyState(
            CircleImageView avatar, View dependency) {
//          avatar.setY(dependency.getY());
//          avatar.setBlahBlah(dependency.blah / blah);
    }
}
