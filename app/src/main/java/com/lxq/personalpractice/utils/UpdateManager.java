package com.lxq.personalpractice.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.INotificationSideChannel;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public class UpdateManager {

    public static void downloadApk(final Context context, final String url, final String path, final
    CompositeDisposable disposable) {
        //NetWorkUtil.getDefault()
        NetWork.getInstance()
                   .down(url)
                   .map(new Function<ResponseBody, BufferedSource>() {
                       @Override
                       public BufferedSource apply(ResponseBody responseBody) throws Exception {
                           return responseBody.source();
                       }
                   })
                   .subscribeOn(Schedulers.io())
                   .observeOn(Schedulers.io())
                   .subscribe(new Observer<BufferedSource>() {
                       @Override
                       public void onSubscribe(Disposable d) {
                           disposable.add(d);
                       }
                       @Override
                       public void onNext(BufferedSource bufferedSource) {

                           try {
                               writeFile(bufferedSource, new File(path));
                           }
                           catch (IOException e) {
                               e.printStackTrace();
                           }
                       }

                       @Override
                       public void onError(Throwable e) {
                           unSubscribe(disposable);
                       }
                       @Override
                       public void onComplete() {
                           Intent intent = new Intent(Intent.ACTION_VIEW);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           intent.setDataAndType(Uri.fromFile(new File(path)),
                               "application/vnd.android.package-archive");
                           context.startActivity(intent);

                           unSubscribe(disposable);
                       }
                   });
    }

    private static void unSubscribe(CompositeDisposable d) {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }

    private static void writeFile(BufferedSource bufferedSource, File file) throws IOException {

        if (!file.getParentFile()
                 .exists()) {
            file.getParentFile()
                .mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeAll(bufferedSource);
        bufferedSink.close();
        bufferedSource.close();
    }
}
