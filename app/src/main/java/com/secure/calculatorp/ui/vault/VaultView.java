package com.secure.calculatorp.ui.vault;

import com.secure.calculatorp.ui.base.BaseMvpView;
import com.secure.calculatorp.util.AppConstants;



public interface VaultView extends BaseMvpView{

    void switchFragment(AppConstants.Fragment fragment);
    void openFileBrowser();
    void encryptionErrorDialog();
    void destroyActivity();
    void showLoading();
    void hideLoading();
}
