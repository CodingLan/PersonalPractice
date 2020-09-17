package com.lxq.personalpractice.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by lanxiaoqing on 2019-07-31.
 */
public class CustomeView extends View {

    private int lastX, lastY;

    private Scroller mScroller;

    public CustomeView(Context context) {
        super(context);
        mScroller = new Scroller(context);

        ArrayBlockingQueue arrayBlockingQueue=new ArrayBlockingQueue(11);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension();
        //ScrollView scrollView;
        //LinearLayout linearLayout;
    }

    public CustomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            //((View)getParent()).
                                   scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int x, int y) {
        mScroller.startScroll(getScrollX(), 0, x - getScrollX(), 0, 400);
        invalidate();
    }

    public CustomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = x;
            lastY = y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int xOffset = x - lastX;
            int yOffset = y - lastY;

            this.layout(getLeft() + xOffset, getTop() + yOffset, getRight() + xOffset, getBottom() + yOffset);

            //offsetLeftAndRight(xOffset);
            //offsetTopAndBottom(yOffset);
        }

        return true;
    }
}
