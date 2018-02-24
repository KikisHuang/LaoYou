package laoyou.com.laoyou.tencent.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFutureFriendType;
import com.tencent.TIMValueCallBack;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.tencent.model.FriendFuture;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.view.CircleImageView;

import static laoyou.com.laoyou.tencent.ui.FriendshipManageMessageActivity.getFriendPresenter;
import static laoyou.com.laoyou.utils.DateUtils.IsToday;
import static laoyou.com.laoyou.utils.DateUtils.timedate;
import static laoyou.com.laoyou.utils.DateUtils.timedateHHMM;
import static laoyou.com.laoyou.utils.DateUtils.timedateMMdd;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

/**
 * 好友关系链管理消息adapter
 */
public class FriendManageMessageAdapter extends ArrayAdapter<FriendFuture> {

    private static final String TAG = "FriendManageMessageAdapter";
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
    public FriendManageMessageAdapter(Context context, int resource, List<FriendFuture> objects) {
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
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.des = (TextView) view.findViewById(R.id.description);
            viewHolder.status = (TextView) view.findViewById(R.id.status);
            viewHolder.consent_tv = (TextView) view.findViewById(R.id.consent_tv);
            viewHolder.neglect_tv = (TextView) view.findViewById(R.id.neglect_tv);
            viewHolder.request_layout = (LinearLayout) view.findViewById(R.id.request_layout);
            viewHolder.add_time = (TextView) view.findViewById(R.id.add_time);

            view.setTag(viewHolder);
        }



        Resources res = getContext().getResources();
        final FriendFuture data = getItem(position);

        if (data.getFaceUrl() == null || data.getFaceUrl().equals(""))
            viewHolder.avatar.setImageResource(R.drawable.head_other);
        else
            Glide.with(MyApplication.getContext()).load(data.getFaceUrl()).apply(getGlideOptions()).into(viewHolder.avatar);

        if (IsToday(timedate(data.getAddTime())))
            viewHolder.add_time.setText(timedateHHMM(String.valueOf(data.getAddTime())));
        else
            viewHolder.add_time.setText(timedateMMdd(String.valueOf(data.getAddTime())));

        viewHolder.name.setText(data.getName());
        viewHolder.des.setText(data.getMessage());
        switch (data.getType()) {
            case TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE:
                viewHolder.request_layout.setVisibility(View.VISIBLE);
                viewHolder.consent_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FriendshipManagerPresenter.acceptFriendRequest(data.getIdentify(), new TIMValueCallBack<TIMFriendResult>() {
                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onSuccess(TIMFriendResult timFriendResult) {
                                data.setType(TIMFutureFriendType.TIM_FUTURE_FRIEND_DECIDE_TYPE);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

                viewHolder.neglect_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FriendshipManagerPresenter.refuseFriendRequest(data.getIdentify(), new TIMValueCallBack<TIMFriendResult>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(getContext(), getContext().getString(R.string.operate_fail), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(TIMFriendResult timFriendResult) {
                                getFriendPresenter().getFriendshipMessage();
                            }
                        });

                    }
                });

                break;
            case TIM_FUTURE_FRIEND_PENDENCY_OUT_TYPE:
                viewHolder.request_layout.setVisibility(View.GONE);
                viewHolder.status.setText(res.getString(R.string.newfri_wait));
                viewHolder.status.setTextColor(res.getColor(R.color.gray1));
                break;
            case TIM_FUTURE_FRIEND_DECIDE_TYPE:
                viewHolder.request_layout.setVisibility(View.GONE);
                viewHolder.status.setText(res.getString(R.string.added));
                viewHolder.status.setTextColor(res.getColor(R.color.green4));
                break;
        }
        return view;
    }


    public class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView neglect_tv;
        TextView add_time;
        TextView consent_tv;
        TextView des;
        TextView status;
        LinearLayout request_layout;
    }

}
