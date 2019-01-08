package com.secure.calculatorp.ui.vault;

import com.secure.calculatorp.ui.base.BaseMvpView;
import com.secure.calculatorp.util.AppConstants;

/**
 * Created by zakir on 06/01/2019.
 */

public interface VaultView extends BaseMvpView{

    void switchFragment(AppConstants.Fragment fragment);
    void openFileBrowser();
    String getPinCode();
}
