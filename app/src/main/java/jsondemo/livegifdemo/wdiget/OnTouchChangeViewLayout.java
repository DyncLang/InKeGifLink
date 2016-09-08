package jsondemo.livegifdemo.wdiget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Xiao_Bailong on 2016/9/8 0008.
 */

public class OnTouchChangeViewLayout extends ViewGroup {

    private View imgTop;
    private View imgContent;
    private View imgBottom;

    private Context mContext;
    private int mScreenHeight;
    private int mScreenWidth;
    private Scroller mScroller = null;

    private int mMaxTop = 0;
    private int mMeasuredHeight;
    private int mDelayMillis = 700; //窗口切换动画

    public OnTouchChangeViewLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public OnTouchChangeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    public OnTouchChangeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        imgTop = getChildAt(0);
        imgContent = getChildAt(1);
        imgBottom = getChildAt(2);

        imgTop.getLayoutParams().width = mScreenWidth;
        imgTop.getLayoutParams().height = mScreenHeight;
        imgContent.getLayoutParams().width = mScreenWidth;
        imgContent.getLayoutParams().height = mScreenHeight;
        imgBottom.getLayoutParams().width = mScreenWidth;
        imgBottom.getLayoutParams().height = mScreenHeight;
        measureChild(imgTop, widthMeasureSpec, heightMeasureSpec);
        measureChild(imgContent, widthMeasureSpec, heightMeasureSpec);
        measureChild(imgBottom, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(l + "", "onLayout: l---" + "t" + t + "r--" + r + "b" + b);
        if (changed) {
            imgTop.layout(0, -mScreenHeight, r, 0);
            imgContent.layout(l, t, r, b);
            imgBottom.layout(0, mScreenHeight, r, b + mScreenHeight);
        }

        mMeasuredHeight = imgContent.getMeasuredHeight();
    }

    private void init() {
        mScroller = new Scroller(mContext);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScreenWidth = outMetrics.widthPixels;
    }


    private float mLastMotionY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                }

                mLastMotionY = y;
                break;

            case MotionEvent.ACTION_MOVE:

                int detaY = (int) (mLastMotionY - y);

                scrollTo(0, detaY);

                break;

            case MotionEvent.ACTION_UP:

                ScrollView(0, (int) y);

                break;

            case MotionEvent.ACTION_CANCEL:

                break;
        }

        return true;
    }


    private void ScrollView(int velocityX, int velocityY) {

        int scrollY = getScrollY();
        int DY;


        if (scrollY > 0) {

            if (Math.abs(scrollY) > (mMeasuredHeight / 2)) {
                DY = mScreenHeight - Math.abs(scrollY);
                postOnChangeView(true);
            } else {
                DY = -scrollY;
            }

            mScroller.startScroll(0, scrollY, 0, DY, Math.abs(mDelayMillis));

        } else {

            if (Math.abs(scrollY) > (mMeasuredHeight / 2)) {
                DY = mScreenHeight - Math.abs(scrollY);
                postOnChangeView(false);
            } else {
                DY = scrollY;
            }
            mScroller.startScroll(0, scrollY, 0, -DY, Math.abs(1000));
        }


        invalidate();
    }

    /**
     * 回调下一个视图
     *
     * @param isNext true 下一个 false 上一个
     */
    public void postOnChangeView(final boolean isNext) {
        if (mOnChangeViewListener == null) {
            return;
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isNext) {
                    mOnChangeViewListener.onNextView();
                } else {
                    mOnChangeViewListener.onBackView();
                }
            }
        }, mDelayMillis);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    private OnChangeViewListener mOnChangeViewListener;

    /**
     * 切换布局监听
     */
    public interface OnChangeViewListener {
        void onNextView();

        void onBackView();
    }

    /**
     * 设置 视图切换回调监听；
     *
     * @param onChangeViewListner
     */
    public void setOnChangeViewListner(OnChangeViewListener onChangeViewListner) {
        mOnChangeViewListener = onChangeViewListner;
    }

}

