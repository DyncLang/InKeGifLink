package jsondemo.livegifdemo.wdiget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jsondemo.livegifdemo.R;


/**
 * Created by Xiao_Bailong on 2016/9/6 0006.
 */
public class MessageLayout {
    private String TAG = this.getClass().getSimpleName();
    public ImageView mImgUserIco;
    public TextView tvName;

    private TranslateAnimation inAnim;//礼物View出现的动画
    private AlphaAnimation outAnim;//礼物View消失的动画

    public static List<MessageLayout> mRecycleViews = new ArrayList<>(); //回收的View

    public View mShowMessageLayout; //显示红包消息的布局

    public interface onAnimationListener {
        void onAnimationEnd(MessageLayout messageLayout);
    }

    onAnimationListener mOnAnimationListener;

    public void setonAnimationListener(onAnimationListener onAnimationListener) {
        mOnAnimationListener = onAnimationListener;
    }


    public MessageLayout(Context context) {

        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.live_red_in);
        outAnim = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.live_red_out);
        mShowMessageLayout = LayoutInflater.from(context).inflate(R.layout.gift_item, null, false);

        mImgUserIco = (ImageView) mShowMessageLayout.findViewById(R.id.img_user_ico);
        tvName = (TextView) mShowMessageLayout.findViewById(R.id.tv_name);

        mShowMessageLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                startView();
            }
        });

    }

    public static MessageLayout buildMessageLayout(Context context) {

        MessageLayout messageLayout = getMessageLayout();

        if (messageLayout == null) {
            messageLayout = new MessageLayout(context);
            mRecycleViews.add(messageLayout);
        }

        return messageLayout;
    }

    /**
     * 获取到回收的控件
     */
    private static MessageLayout getMessageLayout() {

        for (MessageLayout layout : mRecycleViews) {
            if (layout.mShowMessageLayout.getParent() == null) {
                return layout;
            }
        }

        return null;
    }


    public void startView() {
        mShowMessageLayout.startAnimation(inAnim);//播放礼物View出现的动

        inAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parent = (ViewGroup) mShowMessageLayout.getParent();

                if (parent != null) {
                    parent.removeView(mShowMessageLayout);
                }

                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationEnd(MessageLayout.this);
                }

                mShowMessageLayout.startAnimation(outAnim);//播放礼物View出现的动
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
