package com.crackncrunch.amplain.mvp.presenters;

public interface IAccountPresenter {
    void onClickAddress();
    void switchViewState();

    void switchOrder(boolean isChecked);
    void switchPromo(boolean isChecked);

    void takePhoto();
    void chooseCamera();
    void chooseGallery();
}
