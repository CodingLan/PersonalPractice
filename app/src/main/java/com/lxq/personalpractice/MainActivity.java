package com.lxq.personalpractice;

import android.os.Bundle;
import android.widget.TextView;

import com.lxq.personalpractice.base.BaseActivity;

import butterknife.BindView;

/**
 * @author Created by lxq on 2017/12/18.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tvTest)
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvTest.setText("dadsdsdsa");
    }
}
