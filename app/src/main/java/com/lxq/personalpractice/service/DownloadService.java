package com.lxq.personalpractice.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.lxq.personalpractice.R;
import com.lxq.personalpractice.utils.UpdateManager;
import com.lxq.personalpractice.utils.UpdateManager.UpdateDownloadListener;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public class DownloadService extends IntentService {

    public static final String DOWNLOAD_URL = "downloadUrl";
    public static final String FILEPATH = "filePath";
    public static final String ACTION_DOWNLOAD = "downloadService.action";

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DownloadService() {
        super("DownloadService");
    }

    public static void startIntentService(Context context, String url, String path) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(DOWNLOAD_URL, url);
        intent.putExtra(FILEPATH, path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                builder = new Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText("开始下载")
                    .setAutoCancel(true)
                    .setContentTitle("版本更新");

                manager.notify(0, builder.build());

                String url = intent.getStringExtra(DOWNLOAD_URL);
                String path = intent.getStringExtra(FILEPATH);

                UpdateManager.download(this, url, path,
                    new UpdateDownloadListener() {
                        @Override
                        public void updateView(int progress) {
                            builder.setContentInfo(String.valueOf(progress) + "%")
                                   .setProgress(100, progress, false);
                            manager.notify(0, builder.build());

                            if (progress == 100) {
                                manager.cancel(0);
                            }
                        }
                    });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestory", "DownLoadService");
    }
}
