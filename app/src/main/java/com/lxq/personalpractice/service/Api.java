package com.lxq.personalpractice.service;

import com.lxq.personalpractice.Bean.RequestModel;
import com.lxq.personalpractice.Bean.ResponseModel;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public interface Api {

    @POST ("getdataserver")
    Observable<ResponseModel> get(@Body RequestModel requestModel);

    @POST("postdataserver")
    Observable<ResponseModel> post(@Body RequestModel requestModel);

    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);
}
