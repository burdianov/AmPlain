package com.crackncrunch.amplain.data.network;

import com.crackncrunch.amplain.data.network.res.ProductRes;
import com.crackncrunch.amplain.utils.ConstantsManager;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Lilian on 21-Feb-17.
 */

public interface RestService {
    @GET("products")
    Observable<Response<List<ProductRes>>> getProductResObs
    (@Header(ConstantsManager.IF_MODIFIED_SINCE_HEADER) String
             lastEntityUpdate);
}
