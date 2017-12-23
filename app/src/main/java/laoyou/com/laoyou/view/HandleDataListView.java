package laoyou.com.laoyou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lian on 2017/12/23.
 */
public class HandleDataListView extends ListView {

    private DataChangedListener dataChangedListener;

    public HandleDataListView(Context context) {
        super(context);
    }

    public HandleDataListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HandleDataListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleDataChanged() {
        super.handleDataChanged();
        dataChangedListener.onDataChangedSuccess();
    }

    public void setDataChangedListener(DataChangedListener dataChangedListener) {
        this.dataChangedListener = dataChangedListener;
    }

    public interface DataChangedListener {
        public void onDataChangedSuccess();
    }
}
