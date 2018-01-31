package com.lxq.personalpractice.Bean;

import com.lxq.personalpractice.utils.RxBus;
import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.*;

import java.io.IOException;

/**
 * Created by lxq_workspace on 2017/12/19.
 */

public class FileResponseBody extends ResponseBody {

    private Response response;
    public FileResponseBody(Response responseBody) {
        this.response = responseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return response.body()
                       .contentType();
    }
    @Override
    public long contentLength() {
        return response.body()
                       .contentLength();
    }
    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(response.body()
                                                        .source()) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long read = super.read(sink, byteCount);

                bytesReaded += read == -1 ? 0 : read;

                //todo:   RxBus
                //通过RxBus发布进度信息
                RxBus.getDefault()
                         .send(new DownloadBean(contentLength(), bytesReaded));

                return read;
            }
        });
    }
}
