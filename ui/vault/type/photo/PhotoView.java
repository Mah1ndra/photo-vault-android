package com.lionroarsrk.scapp.ui.vault.type.photo;

import android.net.Uri;

import com.lionroarsrk.scapp.ui.base.BaseMvpView;


import java.util.HashSet;

import javax.crypto.SecretKey;


public interface PhotoView extends BaseMvpView {

    void updateList(HashSet<Uri> imageList);
    void showFullScreenImageView(Uri uri);
}
