package laoyou.com.laoyou.listener;

import java.util.List;

/**
 * Created by lian on 2017/12/20.
 */
public interface RecyclerViewOnItemClickListener {

    void RcOnItemClick(int pos, List<String> imgs);

    void LikeClick(String id);

    void GoPageHome(String userId);

    /**
     * @param id     帖子id;
     * @param userId @人id(空则为全局评论);
     * @param name   @人名字;
     * @param name   评论内容;
     */
    void GoCommentPage(String id, String userId, String name, String content);
}
