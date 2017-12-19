package com.lxq.personalpractice.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public interface Api {

    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);
}
