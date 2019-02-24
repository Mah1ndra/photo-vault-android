package com.secure.calculatorp.data.model;

import android.net.Uri;

public class FileModel {

    private Uri uri;

    public FileModel(Uri uri, byte[] iv) {
        this.uri = uri;
        this.iv = iv;
    }

    private byte[] iv;


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
}
