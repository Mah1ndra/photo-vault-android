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


public interface FileHelper {

    boolean deleteImageFromPublicStorage(Uri uri);
    boolean restoreImageToPublicStorage(Uri uri);

    boolean storeEncryptedImage(FileModel src, SecretKey secretKey)
            throws IOException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException;

    boolean storeEncryptedImage(ArrayList<FileModel> imageFiles, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException;

    void createTemporaryImages(SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException;

    void removeTemporaryImages();
    HashSet<Uri> getTemporaryImages();
    HashSet<Integer> getVideoList();
    HashSet<Integer> getDocumentList();
}
