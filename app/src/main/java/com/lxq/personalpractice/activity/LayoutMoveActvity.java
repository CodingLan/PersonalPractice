package com.lxq.personalpractice.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;

import com.lxq.personalpractice.R;
import com.lxq.personalpractice.view.CustomeView;

/**
 * Created by lanxiaoqing on 2019-07-31.
 */
public class LayoutMoveActvity extends Activity {

    CustomeView mCustomeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_move_view);
        mCustomeView = findViewById(R.id.customView);

        findViewById(R.id.toView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomeView.smoothScrollTo(100, 0);

            }
        });

        AnimationSet set;
    }

    static AsyncTask<String, String, String> asyncTask;

    private void test() {
        asyncTask = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                publishProgress("");
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }
        };
        asyncTask.execute("");
    }
}
