package com.crackncrunch.amplain.mvp.views;

/**
 * Created by Lilian on 21-Feb-17.
 */
public interface IRootView extends IView {
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();
}
