package com.crackncrunch.amplain.mvp.views;

/**
 * Created by Lilian on 19-Feb-17.
 */

public interface IAuthView extends IView {

    void showLoginBtn();
    void hideLoginBtn();

    void showCatalogScreen();

    String getUserEmail();
    String getUserPassword();

    boolean isIdle();

    void setCustomState(int state);
}
