package com.lxq.personalpractice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lxq.personalpractice.Bean.ProportionItem;
import com.lxq.personalpractice.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanxiaoqing on 2019-08-05.
 * 资产占比View
 */
public class CustomProportionView extends View {
    private List<ProportionItem> data;

    /**
     * 占比条高度
     */
    private float mBarHeight = 0f;

    /**
     * 方块高度
     */
    private float mSquareHeight = 0f;

    /**
     * 文字大小
     */
    private float mTextSize = 0;

    private int mTextXOffset = 6;
    private int mTextYOffset = -3;

    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;
    private Paint mPaint5;
    private Paint mTextPaint;

    private List<Paint> mPaints;

    public CustomProportionView(Context context) {
        this(context, null);
    }

    public CustomProportionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomProportionView);
        mBarHeight = array.getDimensionPixelSize(R.styleable.CustomProportionView_barHeight, 30);
        mSquareHeight = array.getDimensionPixelSize(R.styleable.CustomProportionView_squareHeight, 10);
        mTextSize = array.getDimensionPixelSize(R.styleable.CustomProportionView_textSize, 10);

        array.recycle();

        mPaints = new ArrayList<>();
        mPaint1 = new Paint();
        mPaint1.setColor(context.getColor(R.color.user_color_1));
        mPaint2 = new Paint();
        mPaint2.setColor(context.getColor(R.color.user_color_2));
        mPaint3 = new Paint();
        mPaint3.setColor(context.getColor(R.color.user_color_3));
        mPaint4 = new Paint();
        mPaint4.setColor(context.getColor(R.color.user_color_4));
        mPaint5 = new Paint();
        mPaint5.setColor(context.getColor(R.color.user_color_5));

        mTextPaint = new Paint();
        mTextPaint.setColor(context.getColor(R.color.color_text));
        mTextPaint.setTextSize(mTextSize);

        mPaints.add(mPaint1);
        mPaints.add(mPaint2);
        mPaints.add(mPaint3);
        mPaints.add(mPaint4);
        mPaints.add(mPaint5);
    }

    public CustomProportionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateData(List<ProportionItem> data) {
        this.data = data;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null && data.size() > 0) {
            float width = getMeasuredWidth();

            float left = 0;

            for (int i = 0; i < data.size(); i++) {

                if (i < 4) {
                    ProportionItem item = data.get(i);
                    float right = left + width * item.getValue();
                    canvas.drawRect(left, 0, right, mBarHeight, mPaints.get(i));
                    left = right;

                    float rectLeft = width / 4 * i;
                    canvas.drawRect(rectLeft, 2 * mBarHeight, rectLeft + mSquareHeight, 2 * mBarHeight + mSquareHeight,
                        mPaints.get(i));

                    canvas.drawText(formatNameNValue(item.getName(), item.getValue()),
                        rectLeft + mSquareHeight + mTextXOffset,
                        2 * mBarHeight + mSquareHeight + mTextYOffset,
                        mTextPaint);
                } else {
                    canvas.drawRect(left, 0, getMeasuredHeight(), mBarHeight, mPaints.get(mPaints.size() - 1));
                    break;
                }
            }

            if (data.size() > 4) {
                float other = 0;
                for (int i = 4; i < data.size(); i++) {
                    other += data.get(i).getValue();
                }

                canvas.drawRect(0, 2 * (mBarHeight + mSquareHeight), mSquareHeight,
                    2 * (mBarHeight + mSquareHeight) + mSquareHeight,
                    mPaints.get(mPaints.size() - 1));

                canvas.drawText(formatNameNValue("其他", other), mSquareHeight + mTextXOffset,
                    2 * (mBarHeight + mSquareHeight) + mSquareHeight + mTextYOffset, mTextPaint);
            }
        }
    }

    private static String formatNameNValue(String name, float value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("(").append(formattedDecimalToPercentage(value)).append(")");
        return stringBuilder.toString();
    }

    private static String formattedDecimalToPercentage(double decimal) {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(decimal);
    }
}
