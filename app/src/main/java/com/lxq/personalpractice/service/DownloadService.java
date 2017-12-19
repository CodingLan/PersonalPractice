package com.lxq.personalpractice.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import com.lxq.personalpractice.R;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public class DownloadService extends IntentService {

    public static final String DOWNLOAD_URL = "downloadUrl";
    public static final String FILEPATH = "filePath";
    public static final String ACTION_DOWNLOAD = "downloadService.action";

    private Notification notification;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

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
                    .setContentTitle("版本升级");

                notification = builder.build();

                manager.notify(0, notification);
            }
        }
    }
}
