package com.secure.calculatorp.ui.vault.type.photo;

import android.net.Uri;

import com.secure.calculatorp.ui.base.BaseMvpView;


import java.util.HashSet;

import javax.crypto.SecretKey;

/**
 * Created by zakir on 08/01/2019.
 */

public interface PhotoView extends BaseMvpView {

    void updateList(HashSet<Uri> imageList);
    void showFullScreenImageView(Uri uri);
}
