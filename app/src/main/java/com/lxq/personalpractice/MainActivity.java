package com.lxq.personalpractice;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.OnClick;

import com.lxq.personalpractice.Bean.ProportionItem;
import com.lxq.personalpractice.activity.FirstActivity;
import com.lxq.personalpractice.activity.LayoutMoveActvity;
import com.lxq.personalpractice.activity.ScrollingActivity;
import com.lxq.personalpractice.activity.ToolbarActivity;
import com.lxq.personalpractice.base.BaseActivity;
import com.lxq.personalpractice.service.DownloadService;
import com.lxq.personalpractice.utils.UpdateManager;
import com.lxq.personalpractice.utils.UpdateManager.UpdateDownloadListener;
import com.lxq.personalpractice.view.CustomProportionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Created by lxq on 2017/12/18.
 */
public class MainActivity extends BaseActivity {

    public static final String downloadUrl = "https://wap.58coin.com/58COIN_V1.0.0_20171208.apk";
    //public static final String downloadUrl = "http://www.izis.cn/mygoedu/yztv_1.apk";

    public static final String filePath = Environment.getExternalStorageDirectory()
                                                     .getPath() + "/test.apk";

    //private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ProgressBar pbDownload;
    private TextView tvProgress;

    private CustomProportionView proportionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConcurrentHashMap ss = new ConcurrentHashMap();

        HashMap<Boolean, String> map = new HashMap();
        checkPermission();

        proportionView = findViewById(R.id.proportionView);

        initData();
    }

    private void initData() {
        List<ProportionItem> data = new ArrayList<>();
        data.add(new ProportionItem("BTC", 0.3f));
        data.add(new ProportionItem("ETC", 0.25f));
        data.add(new ProportionItem("ETH", 0.2f));
        data.add(new ProportionItem("LTC", 0.15f));
        data.add(new ProportionItem("EOS", 0.04f));
        data.add(new ProportionItem("TRX", 0.03f));
        data.add(new ProportionItem("RUFF", 0.03f));
        proportionView.updateData(data);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @OnClick ( R.id.moveView )
    public void move() {
        startActivity(new Intent(MainActivity.this, LayoutMoveActvity.class));
    }

    @OnClick ( R.id.btnScroll )
    public void scroll() {
        startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
    }

    @OnClick ( R.id.btnTool )
    public void tool() {
        startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
    }

    @OnClick ( R.id.btnForce )
    public void forceUpdate() {
        initPopupWindow();
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

    @OnClick ( R.id.toFirst )
    public void gotoFirst(View v) {
        startActivity(new Intent(MainActivity.this, FirstActivity.class));

        overridePendingTransition(R.anim.in_from_right, R.anim.alpha);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initPopupWindow() {

        View view = getLayoutInflater().inflate(R.layout.popup_download, null, false);
        pbDownload = (ProgressBar)view.findViewById(R.id.pbDownload);
        tvProgress = (TextView)view.findViewById(R.id.tvProgress);

        tvProgress.setText("0%");
        AlertDialog.Builder window = new Builder(this);
        window.setTitle("升级更新");

        window.setCancelable(false);
        window.setView(view);
        window.show();

        UpdateManager.download(this, downloadUrl, filePath,
            new UpdateDownloadListener() {
                @Override
                public void updateView(final int progress) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pbDownload.setProgress(progress);
                            tvProgress.setText(String.valueOf(progress) + "%");
                        }
                    });
                }
            });
    }
}
