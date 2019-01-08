package com.secure.calculatorp.data.model;

import android.net.Uri;

/**
 * Created by zakir on 06/01/2019.
 */

public class Image {

    private Uri uri;

    public Image(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
