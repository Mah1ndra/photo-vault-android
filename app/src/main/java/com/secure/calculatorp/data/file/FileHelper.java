package com.secure.calculatorp.data.file;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.crypto.SecretKey;

/**
 * Created by zakir on 06/01/2019.
 */

public interface FileHelper {

    HashSet<Uri> getImageList();
    HashSet<Integer> getVideoList();
    HashSet<Integer> getDocumentList();
    boolean deleteImage(Uri uri);
    boolean storeImage(Uri src, SecretKey secretKey, byte[] iv);
    boolean storeImage(ArrayList<Uri> uri, SecretKey secretKey, byte[] iv);
}
