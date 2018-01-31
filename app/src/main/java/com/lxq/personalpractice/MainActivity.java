package com.lxq.personalpractice;

import android.Manifest.permission;
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
import com.lxq.personalpractice.base.BaseActivity;
import com.lxq.personalpractice.service.DownloadService;
import com.lxq.personalpractice.utils.UpdateManager;
import com.lxq.personalpractice.utils.UpdateManager.UpdateDownloadListener;
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
