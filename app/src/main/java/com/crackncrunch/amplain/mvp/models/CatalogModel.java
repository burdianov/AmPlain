package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.network.error.ApiError;
import com.crackncrunch.amplain.data.network.error.NetworkAvailableError;
import com.crackncrunch.amplain.data.network.res.ProductRes;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.data.storage.dto.ProductLocalInfo;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

public class CatalogModel extends AbstractModel {

    public CatalogModel() {

    }

    public List<ProductDto> getProductList() {
        return mDataManager.getPreferencesManager().getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }

    public ProductDto getProductById(int productId) {
        return mDataManager.getPreferencesManager().getProductById(productId);
    }

    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }

    public Observable<ProductDto> getProductObs() {
        Observable<ProductRes> network = fromNetwork();
        Observable<ProductDto> disk = fromDisk();
        Observable<ProductLocalInfo> local = network.flatMap(productRes ->
                mDataManager.getProductLocalInfoObs(productRes));

        Observable<ProductDto> productFromNetwork =
                Observable.zip(network, local, ProductDto::new);

        return Observable.merge(disk, productFromNetwork) // склеиваем два потока данных из сети и диска
                .onErrorResumeNext(throwable -> ((throwable instanceof
                        NetworkAvailableError) || (throwable instanceof ApiError))
                        ? disk : Observable.error(throwable))
                .distinct(ProductDto::getId); // вернуть записи с диска если сетевая ошибка произошла, тут может быть проверка на тип ошибки можно переключать источники
    }

    @RxLogObservable
    public Observable<ProductRes> fromNetwork() {
        return mDataManager.getProductsObsFromNetwork();
    }

    @RxLogObservable
    public Observable<ProductDto> fromDisk() {
        return Observable.defer(() -> {
            List<ProductDto> diskData = mDataManager.fromDisk();
            return diskData == null ?
                    Observable.empty() :
                    Observable.from(diskData);
        });
    }

    public void updateProductLocalInfo(ProductLocalInfo pli) {
        mDataManager.getPreferencesManager().updateOrInsertLocalInfo(pli);
    }
}
