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
    public ImageView mImgUserIco;
    public TextView tvName;

    private TranslateAnimation inAnim;//礼物View出现的动画
    private AlphaAnimation outAnim;//礼物View消失的动画

    public static List<View> mRecycleViews = new ArrayList<>(); //回收的View

    public View mInflate;

    public MessageLayout(Context context) {

        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.live_red_in);
        outAnim = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.live_red_out);
        mInflate = LayoutInflater.from(context).inflate(R.layout.gift_item, null, false);

        mImgUserIco = (ImageView) mInflate.findViewById(R.id.img_user_ico);
        tvName = (TextView) mInflate.findViewById(R.id.tv_name);

    }

    public interface onAnimationListener {
        void onAnimationEnd(MessageLayout messageLayout);
    }

    onAnimationListener mOnAnimationListener;

    public void setonAnimationListener(onAnimationListener onAnimationListener) {
        mOnAnimationListener = onAnimationListener;
    }


    public void startView() {
        mInflate.startAnimation(inAnim);//播放礼物View出现的动

        inAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parent = (ViewGroup) mInflate.getParent();

                if (parent != null) {
                    parent.removeView(mInflate);
                    mRecycleViews.add(mInflate);
                }

                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationEnd(MessageLayout.this);
                }

                mInflate.startAnimation(outAnim);//播放礼物View出现的动
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
