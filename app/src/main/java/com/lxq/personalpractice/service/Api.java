package com.lxq.personalpractice.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public interface Api {

    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);
}
