package com.lionroarsrk.scapp.ui.vault;

import com.lionroarsrk.scapp.ui.base.BaseMvpView;
import com.lionroarsrk.scapp.util.AppConstants;



public interface VaultView extends BaseMvpView{

    void switchFragment(AppConstants.Fragment fragment);
    void openFileBrowser();
    void encryptionErrorDialog();
    void destroyActivity();
    void showLoading();
    void hideLoading();
}
