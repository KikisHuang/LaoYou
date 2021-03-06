package laoyou.com.laoyou.tencent.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.TIMConversationType;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.Message;
import laoyou.com.laoyou.tencent.view.CircleImageView;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;

/**
 * 聊天界面adapter
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private TIMConversationType type;
    private String C2CImg;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     * @param type
     */
    public ChatAdapter(Context context, int resource, List<Message> objects, TIMConversationType type) {
        super(context.getApplicationContext(), resource, objects);
        resourceId = resource;
        this.type = type;
        C2CImg = SPreferences.getTemporaryImg() != null ? SPreferences.getTemporaryImg() : "";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = (RelativeLayout) view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = (RelativeLayout) view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = (RelativeLayout) view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = (RelativeLayout) view.findViewById(R.id.rightPanel);
            viewHolder.sending = (ProgressBar) view.findViewById(R.id.sending);
            viewHolder.error = (ImageView) view.findViewById(R.id.sendError);
            viewHolder.sender = (TextView) view.findViewById(R.id.sender);
            viewHolder.rightDesc = (TextView) view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = (TextView) view.findViewById(R.id.systemMessage);

            viewHolder.rightAvatar = (CircleImageView) view.findViewById(R.id.rightAvatar);
            viewHolder.leftAvatar = (CircleImageView) view.findViewById(R.id.leftAvatar);

            view.setTag(viewHolder);
        }
        if (position < getCount()) {
            final Message data = getItem(position);
            data.showMessage(viewHolder, getContext());
            if (data.isSelf()) {
                if (SPreferences.getUserHeadImg() == null || SPreferences.getUserHeadImg().equals("")) {
                    Glide.with(MyApplication.getContext()).load(R.drawable.head_me).apply(getGlideOptions()).into(viewHolder.rightAvatar);
//                    viewHolder.rightAvatar.setImageResource(R.drawable.head_me);
                    Log.i(TAG, "SenderProfile 为空");
                } else {
                    Glide.with(MyApplication.getContext()).load(SPreferences.getUserHeadImg()).apply(getGlideOptions()).into(viewHolder.rightAvatar);
                }
                viewHolder.rightAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goHomePage(getContext(), "", false);
                    }
                });
            } else {
                if (type == TIMConversationType.C2C) {
                    if (!C2CImg.isEmpty())
                        Glide.with(MyApplication.getContext()).load(C2CImg).apply(getGlideOptions()).into(viewHolder.leftAvatar);
                    else
                        Glide.with(MyApplication.getContext()).load(R.drawable.head_other).apply(getGlideOptions()).into(viewHolder.leftAvatar);
//                        viewHolder.leftAvatar.setImageResource(R.drawable.head_other);
                } else {
                    if (data.getMessage().getSenderProfile() == null)
                        Glide.with(MyApplication.getContext()).load(R.drawable.head_other).apply(getGlideOptions()).into(viewHolder.leftAvatar);
//                        viewHolder.leftAvatar.setImageResource(R.drawable.head_other);
                    else {
                        Glide.with(MyApplication.getContext()).load(data.getMessage().getSenderProfile().getFaceUrl()).apply(getGlideOptions()).into(viewHolder.leftAvatar);
//                        Log.i(TAG, " Group Face === " + data.getMessage().getSenderProfile().getFaceUrl());
                    }

                }

                viewHolder.leftAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goHomePage(getContext(), data.getMessage().getSenderProfile().getIdentifier(), true);
                    }
                });
            }

        }
        return view;
    }


    public class ViewHolder {
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
        public CircleImageView rightAvatar, leftAvatar;
    }
}
