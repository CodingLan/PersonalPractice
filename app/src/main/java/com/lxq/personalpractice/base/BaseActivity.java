package com.lxq.personalpractice.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * @author Created by lxq on 2017/12/18.
 * 基类
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 引入布局
     */
    protected abstract int initLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();
}
