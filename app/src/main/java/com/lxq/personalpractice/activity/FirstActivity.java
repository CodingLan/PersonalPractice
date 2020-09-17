package com.lxq.personalpractice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.lxq.personalpractice.R;

/**
 * Created by lanxiaoqing on 2018/12/21.
 */
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.toSecond).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));

                overridePendingTransition(R.anim.slide_bottom_in, R.anim.alpha);
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha,R.anim.out_from_right);
    }
}
