package jsondemo.livegifdemo.wdiget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Xiao_Bailong on 2016/9/8 0008.
 */

public class OnTouchChangeViewLayout extends FrameLayout {

    String TAG = this.getClass().getSimpleName();

    private ViewDragHelper mDragger;

    private View mDragView;

    private View mBrackView;


    private Point mAutoBackOriginPos = new Point();

    public OnTouchChangeViewLayout(Context context) {
        super(context);
    }

    public OnTouchChangeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.e(TAG, "onViewReleased: " + releasedChild + "xvel" + xvel + "yvel" + yvel);
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == mBrackView) {
                    mDragView.scrollTo(0, 0);

                    invalidate();
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout: l--" + l + "t" + t + "r--" + r + "b--" + b);

        mAutoBackOriginPos.x = mBrackView.getLeft();
        mAutoBackOriginPos.y = mBrackView.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mBrackView = getChildAt(1);
    }
}
