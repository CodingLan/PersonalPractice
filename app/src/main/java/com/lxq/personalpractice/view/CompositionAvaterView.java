package com.lxq.personalpractice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lxq.personalpractice.R;

/**
 * @author Created by lxq on 2017/12/24.
 *         Description
 */

public class CompositionAvaterView extends View {


    private int imgId = R.drawable.auctioneer_images;

    private Bitmap bitmap;
    Paint paint;

    public CompositionAvaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.auctioneer_images);
        paint = new Paint();
    }

    public CompositionAvaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setTextSize(40);


        canvas.save();
        canvas.rotate(90);

        canvas.drawText("测试文章", 20, 10, paint);

        canvas.restore();

        canvas.drawText("我是大哥", 10, 10, paint);

    }
}
