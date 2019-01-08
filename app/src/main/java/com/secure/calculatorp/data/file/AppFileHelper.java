package com.secure.calculatorp.data.file;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.provider.DocumentFile;
import android.webkit.MimeTypeMap;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoCipherManager;
import com.secure.calculatorp.di.ApplicationContext;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.FileUtil;
import com.secure.calculatorp.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;

import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by zakir on 06/01/2019.
 */

public class AppFileHelper implements FileHelper {


    private Context context;
    private CryptoCipherManager cryptoCipherManager;

    @Inject
    public AppFileHelper(@ApplicationContext Context context, CryptoCipherManager cryptoCipherManager) {
        this.context = context;
        this.cryptoCipherManager = cryptoCipherManager;
    }


    @Override
    public HashSet<Uri> getImageList() {
        HashSet<Uri> uriHashSet = new HashSet<>();

        File dir = getInternalImageDirectory();
        if(dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file :
                    files) {
                uriHashSet.add(Uri.parse(file.getAbsolutePath()));
            }
        }
        return uriHashSet;
    }

    @Override
    public HashSet<Integer> getVideoList() {
        return null;
    }

    @Override
    public HashSet<Integer> getDocumentList() {
        return null;
    }

    @Override
    public boolean deleteImage(Uri uri) {
        try {
            return DocumentFile.fromSingleUri(context, uri).delete();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean storeImage(Uri src, SecretKey secretKey, byte[] iv) {
        File des = new File(getInternalImageDirectory(), SystemClock.currentThreadTimeMillis() + "." + getExtension(src));
        try {
            FileInputStream inputStream = (FileInputStream) context.getContentResolver().openInputStream(src);
            byte[] encryptedBytes = cryptoCipherManager.encrypt(inputStream, secretKey, iv);
            return encryptedBytes != null && FileUtil.writeBytesToFile(encryptedBytes, des);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private File getInternalImageDirectory() {
        return context.getDir(AppConstants.LOCATION_INTERNAL_IMAGE, Context.MODE_PRIVATE);
    }

    @Override
    public boolean storeImage(ArrayList<Uri> uris, SecretKey secretKey, byte[] iv) {
        for (Uri uri :
                uris) {
            storeImage(uri, secretKey, iv);
        }
        return true;
    }

    private void copyFile(Uri src, File dst) throws IOException {
        FileInputStream fileInputStream = (FileInputStream) context.getContentResolver().openInputStream(src);
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));
        if(StringUtil.isNullOrEmpty(type)) {
            type = context.getString(R.string.jpg);
        }
        return type;
    }
}
