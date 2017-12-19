package com.lxq.personalpractice.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxq.personalpractice.Bean.FileResponseBody;
import com.lxq.personalpractice.constrant.NetConfig;
import com.lxq.personalpractice.service.Api;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Streaming;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public class NetWorkUtil {

    private Api api;

    private volatile static NetWorkUtil INSTANCE;

    public NetWorkUtil() {
        if (api == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.MICROSECONDS)
                .build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                                         .create();
            Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

            api = retrofit.create(Api.class);
        }
    }

    public static NetWorkUtil getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (NetWorkUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetWorkUtil();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<ResponseBody> down(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.MICROSECONDS)
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response.newBuilder()
                                   .body(new FileResponseBody(response))
                                   .build();
                }
            })
            .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                                     .create();
        Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(NetConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        Api api = retrofit.create(Api.class);
        return api.down(url);
    }
}
