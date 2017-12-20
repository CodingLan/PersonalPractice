package com.lxq.personalpractice;

import android.Manifest;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.TextView;

import butterknife.OnClick;
import com.lxq.personalpractice.Bean.DownloadBean;
import com.lxq.personalpractice.base.BaseActivity;

import butterknife.BindView;
import com.lxq.personalpractice.service.DownloadService;
import com.lxq.personalpractice.service.MyIntentService;

import java.security.Permission;
import java.security.Permissions;

/**
 * @author Created by lxq on 2017/12/18.
 */
public class MainActivity extends BaseActivity {

    //public static final String downloadUrl = "https://wap.58coin.com/58COIN_V1.0.0_20171208.apk";
    public static final String downloadUrl = "http://www.izis.cn/mygoedu/yztv_1.apk";



    public static final String filePath = Environment.getExternalStorageDirectory()
                                                     .getPath() + "/test.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @OnClick ( R.id.btnForce )
    public void forceUpdate() {
        MyIntentService.startUpdateService(MainActivity.this, downloadUrl, filePath);

    }

    @OnClick ( R.id.btnNormal )
    public void normalUpdate() {
        DownloadService.startIntentService(MainActivity.this, downloadUrl, filePath);
    }

    private void checkPermission() {
        if (PackageManager.PERMISSION_GRANTED != this.checkSelfPermission(
            permission.WRITE_EXTERNAL_STORAGE)) {
            String[] strings = new String[] {permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(strings, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
