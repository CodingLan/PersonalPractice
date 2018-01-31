package com.lxq.personalpractice.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.lxq.personalpractice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lxq_workspace on 2018/1/31.
 */

public class NestView extends View {

    private int pointCount = 100;
    private int pointColor = 0xff0000;
    private int lineColor = 0x00ff00;
    private int pointSize = 5;

    private List<Point> mPoints;

    private Paint mPaint;

    public NestView(Context context) {
        super(context, null);
    }

    public NestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(pointColor);
        mPaint.setStrokeWidth(20.0f);
    }

    public NestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        initPoints(getMeasuredWidth(), getMeasuredHeight());
    }

    private void initPoints(int width, int height) {

        int temWidth = (int)(width * 0.5 / 10);
        int temHeight = (int)(height * 0.5 / 10);
        mPoints = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < pointCount; i++) {
            Point point = new Point();
            point.x = random.nextInt(width * 9 / 10) + temWidth;
            point.y = random.nextInt(height * 9 / 10) + temHeight;
            mPoints.add(point);
        }
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NestView);

        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.NestView_lineColor:
                    lineColor = array.getColor(attr, lineColor);
                    break;
                case R.styleable.NestView_pointColor:
                    pointColor = array.getColor(attr, pointColor);
                    break;
                case R.styleable.NestView_pointSize:
                    pointSize = array.getDimensionPixelSize(attr,
                        (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pointSize, displayMetrics));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);                  //白色背景

        mPaint.setColor(Color.BLACK);                    //设置画笔颜色

        for (int i = 0; i < pointCount; i++) {
            Point point = mPoints.get(i);

            canvas.drawPoint(point.x, point.y, mPaint);
        }

        for (int i = 0; i < pointSize; i++) {
            for (int j = 0; j < pointSize; j++) {
                if (i != j) {

                }
            }
        }
    }


}
