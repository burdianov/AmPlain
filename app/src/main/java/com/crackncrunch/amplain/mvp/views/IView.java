package com.crackncrunch.amplain.mvp.views;

/**
 * Created by Lilian on 20-Feb-17.
 */

public interface IView {
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();
}
