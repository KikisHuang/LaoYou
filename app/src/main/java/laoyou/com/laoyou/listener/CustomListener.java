package laoyou.com.laoyou.listener;

import java.util.List;

/**
 * Created by lian on 2018/1/27.
 */
public interface CustomListener {
    <T> void onCustomResult(List<T> obj);

}
