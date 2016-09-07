package jsondemo.livegifdemo.wdiget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jsondemo.livegifdemo.R;


/**
 * Created by Xiao_Bailong on 2016/9/5 0005.
 */
public class MessageQueue implements MessageLayout.onAnimationListener {

    private RelativeLayout rootView;

    private FrameLayout flMessage1;
    private FrameLayout flMessage2;
    private FrameLayout flMessage3;


    private Context mContext;

    private List<FrameLayout> mFrameLayouts = new ArrayList<>(); //可以添加消息的布局
    private List<MessageLayout> mMessageLayouts = new ArrayList<>(); //消息布局List


    public MessageQueue(Context context, RelativeLayout viewGroup) {
        rootView = viewGroup;
        mContext = context;

        init();
    }

    public void init() {

        flMessage1 = (FrameLayout) rootView.findViewById(R.id.fl_message1);
        flMessage2 = (FrameLayout) rootView.findViewById(R.id.fl_message2);
        flMessage3 = (FrameLayout) rootView.findViewById(R.id.fl_message3);

        mFrameLayouts.add(flMessage1);
        mFrameLayouts.add(flMessage2);
        mFrameLayouts.add(flMessage3);
    }

    public MessageLayout buiidMessagLayout() {
        //回收
        MessageLayout messageLayout = new MessageLayout(mContext);

        messageLayout.setonAnimationListener(this);
        messageLayout.mInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
            }
        });
        return messageLayout;
    }

    /**
     * 添加一个Viwe
     */
    public void addView(MessageLayout messageLayout) {
        if (messageLayout == null) {
            return;
        }

        FrameLayout flMessage = getFlMessage();

        if (flMessage == null) {

            if (!mMessageLayouts.contains(messageLayout)) {
                mMessageLayouts.add(messageLayout);
            }

        } else {

            flMessage.removeView(messageLayout.mInflate);
            flMessage.addView(messageLayout.mInflate);
            messageLayout.startView();

        }
    }


    /**
     *返回可以加入红包消息的控件
     * @return
     */
    public FrameLayout getFlMessage() {
        if (mFrameLayouts.size() < 0) {
            return null;
        }

        for (int i = mFrameLayouts.size() - 1; i >= 0; i--) {
            if (mFrameLayouts.get(i).getChildCount() == 0) {
                return mFrameLayouts.get(i);
            }
        }

        return null;

    }


    @Override
    public void onAnimationEnd(MessageLayout messate) {
        if (mMessageLayouts.contains(messate)) {
            mMessageLayouts.remove(messate);
        }

        if (mMessageLayouts.size() > 0) {
            MessageLayout messageLayout = mMessageLayouts.get(0);
            mMessageLayouts.remove(0);
            addView(messageLayout);

        }
    }


}
