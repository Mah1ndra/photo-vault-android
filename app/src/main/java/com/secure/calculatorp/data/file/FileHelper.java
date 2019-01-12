package com.secure.calculatorp.data.file;

import android.net.Uri;

import com.secure.calculatorp.data.model.FileModel;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by zakir on 06/01/2019.
 */

public interface FileHelper {

    boolean deleteImage(Uri uri);

    boolean storeImage(FileModel src, SecretKey secretKey)
            throws IOException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException;

    boolean storeImage(ArrayList<FileModel> imageFiles, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException;

    void createTempImages(SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException;

    void removeTempImages();
    HashSet<Uri> getTempImages();
    HashSet<Integer> getVideoList();
    HashSet<Integer> getDocumentList();
}
