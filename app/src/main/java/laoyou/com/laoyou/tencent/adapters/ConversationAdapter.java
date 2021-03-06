package laoyou.com.laoyou.tencent.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.tencent.model.Conversation;
import laoyou.com.laoyou.tencent.utils.TimeUtil;
import laoyou.com.laoyou.view.group.GroupCircularImageView;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * 会话界面adapter
 */
public class ConversationAdapter extends ArrayAdapter<Conversation> {

    private static final String TAG = "ConversationAdapter";
    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ConversationAdapter(Context context, int resource, List<Conversation> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.lastMessage = (TextView) view.findViewById(R.id.last_message);
            viewHolder.time = (TextView) view.findViewById(R.id.message_time);
            viewHolder.unread = (TextView) view.findViewById(R.id.unread_num);
            view.setTag(viewHolder);
        }
        final Conversation data = getItem(position);
        viewHolder.tvName.setText(data.getName());

        Log.i(TAG, " data.getAvatar() ====" + data.getAvatar());
        if (data.getAvatar() instanceof Integer)
            Glide.with(MyApplication.getContext()).load(data.getAvatar()).apply(getGlideOptions()).into(viewHolder.avatar);
        else if (data.getAvatar() instanceof String) {
            if (((String) data.getAvatar()).isEmpty())
                Glide.with(MyApplication.getContext()).load(R.drawable.head_me).apply(getGlideOptions()).into(viewHolder.avatar);
            else
                Glide.with(MyApplication.getContext()).load(data.getAvatar()).apply(getGlideOptions()).into(viewHolder.avatar);
        }
        if (!data.getLastMessageSummary().isEmpty())
            viewHolder.lastMessage.setText(data.getLastMessageSummary());

        if (!TimeUtil.getTimeStr(data.getLastMessageTime()).isEmpty())
            viewHolder.time.setText(TimeUtil.getTimeStr(data.getLastMessageTime()));

        long unRead = data.getUnreadNum();
        if (unRead <= 0) {
            viewHolder.unread.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.unread.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if (unRead < 10) {
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point1));
            } else {
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point2));
                if (unRead > 99) {
                    unReadStr = getContext().getResources().getString(R.string.time_more);
                }
            }
            viewHolder.unread.setText(unReadStr);
        }
        return view;
    }

    public class ViewHolder {
        public TextView tvName;
        public CircleImageView avatar;
        public GroupCircularImageView group_head;
        public TextView lastMessage;
        public TextView time;
        public TextView unread;

    }
}
