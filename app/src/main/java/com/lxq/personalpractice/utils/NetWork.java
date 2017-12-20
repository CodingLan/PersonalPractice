package com.lxq.personalpractice.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxq.personalpractice.Bean.FileResponseBody;
import com.lxq.personalpractice.Bean.RequestModel;
import com.lxq.personalpractice.Bean.ResponseModel;
import com.lxq.personalpractice.constrant.NetConfig;
import com.lxq.personalpractice.service.Api;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求工具类
 * Created by lxf on 2016/10/19.
 */
public class NetWork {
    private Api api;
    private volatile static NetWork INSTANCE;

    private NetWork() {
        if (api == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(NetConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            api = retrofit.create(Api.class);
        }
    }

    /**
     * 获取单例
     */
    public static NetWork getInstance() {
        if (INSTANCE == null) {
            synchronized (NetWork.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetWork();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 对结果进行预处理
     *//*
    private static <T> ObservableTransformer<ResponseModel, T> handleObject(final Class<T> cls) {
        return new ObservableTransformer<ResponseModel, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseModel> upstream) {
                return upstream
                        .flatMap(new Function<ResponseModel, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(ResponseModel responseModel) throws
                                Exception {
                                if (responseModel.getError() == 0) {
                                    return createObject(responseModel.getData(), cls);
                                } else {
                                    return Observable.error(new ServerException(responseModel.getMsg()));
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    *//**
     * 对结果进行预处理
     *//*
    private static <T> ObservableTransformer<ResponseModel, List<T>> handleList(final Class<T> cls) {
        return new ObservableTransformer<ResponseModel, List<T>>() {
            @Override
            public ObservableSource<List<T>> apply(Observable<ResponseModel> upstream) {
                return upstream
                        .flatMap(new Function<ResponseModel, ObservableSource<List<T>>>() {
                            @Override
                            public ObservableSource<List<T>> apply(ResponseModel responseModel) throws
                                Exception {
                                if (responseModel.getError() == 0) {
                                    return createList(responseModel.getData(), cls);
                                } else {
                                    return Observable.error(new ServerException(responseModel.getMsg()));
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    *//**
     * 创建成功的数据
     *//*
    private static <T> Observable<T> createObject(final String data, final Class<T> cls) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(GsonUtil.GsonToBean(data, cls));
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    *//**
     * 创建成功的数据
     *//*
    private static <T> Observable<List<T>> createList(final String data, final Class<T> cls) {
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> emitter) throws Exception {
                try {
                    emitter.onNext(GsonUtil.GsonToList(data, cls));
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public <T> Observable<T> getObject(String code, String info, int userid, final Class<T> cls) {

        Observable<ResponseModel> observable = api.get(new RequestModel(code, info, "xxxx", userid));

        return observable
                .compose(handleObject(cls));
    }

    public <T> Observable<List<T>> getList(String code, String info, int userid, final Class<T> cls) {

        Observable<ResponseModel> observable = api.get(new RequestModel(code, info, "xxxx", userid));

        return observable
                .compose(handleList(cls));
    }

    public <T> Observable<T> postObject(String code, String info, int userid, final Class<T> cls) {

        Observable<ResponseModel> observable = api.post(new RequestModel(code, info, "xxxx", userid));

        return observable
                .compose(handleObject(cls));
    }

    public <T> Observable<List<T>> postList(String code, String info, int userid, final Class<T> cls) {

        Observable<ResponseModel> observable = api.post(new RequestModel(code, info, "xxxx", userid));

        return observable
                .compose(handleList(cls));
    }
*/
    public Observable<ResponseBody> down(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
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

    /*public Observable<ResponseBody> down(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                            .newBuilder()
                            .body(new FileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
                            .build();
                    }
                })
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        return api.down(url);
    }*/
}
