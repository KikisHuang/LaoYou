package laoyou.com.laoyou.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by lian on 2017/7/24.
 * ViewHolder工具类;
 */
public class OverallViewHolder {
    private static final String TAG = "OverallViewHolder";

    public static class ViewHolder {
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }

            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }

}
