package com.lxq.personalpractice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.lxq.personalpractice.R;

/**
 * Created by lanxiaoqing on 2018/12/21.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.toSecond).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void finish() {
        super.finish();

       // overridePendingTransition(R.anim.alpha, R.anim.slide_bottom_out);
    }
}
