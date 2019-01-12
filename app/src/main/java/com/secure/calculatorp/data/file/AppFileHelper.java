package com.secure.calculatorp.data.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.provider.DocumentFile;
import android.webkit.MimeTypeMap;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.di.ApplicationContext;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.CommonUtils;
import com.secure.calculatorp.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by zakir on 06/01/2019.
 */

public class AppFileHelper implements FileHelper {


    private Context context;
    private CryptoManager cryptoManager;

    @Inject
    public AppFileHelper(@ApplicationContext Context context, CryptoManager appCryptoOperationManager) {
        this.context = context;
        this.cryptoManager = appCryptoOperationManager;
    }


    @Override
    public HashSet<Uri> getTempImages() {
        HashSet<Uri> uriHashSet = new HashSet<>();

        File dir = getInternalTempDirectory();
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file :
                    files) {
                uriHashSet.add(Uri.parse(file.getAbsolutePath()));
            }
        }
        return uriHashSet;
    }

    private HashSet<Uri> getEncryptedImages() {
        HashSet<Uri> uriHashSet = new HashSet<>();

        File dir = getInternalImageDirectory();
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file :
                    files) {
                uriHashSet.add(Uri.parse(file.getAbsolutePath()));
            }
        }
        return uriHashSet;
    }

    @Override
    public boolean storeImage(FileModel src, SecretKey secretKey) throws IOException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException {
        File des = new File(getInternalImageDirectory(), generateFileName(src));
        FileInputStream inputStream = (FileInputStream) context.getContentResolver().openInputStream(src.getUri());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        cryptoManager.encrypt(inputStream, outputStream, secretKey, src.getIv());
        try (FileOutputStream fileOutputStream = new FileOutputStream(des)) {
            outputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
        }
        outputStream.close();
        return true;
    }

    @Override
    public void createTempImages(SecretKey secretKey) throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {

        HashSet<Uri> imageList = getEncryptedImages();
        for (Uri uri : imageList) {
            File tempFile = new File(generateTempFileName(uri));
            if (tempFile.exists()) {
                continue;
            }

            String ivSrc = getFileNameWithoutExtension(uri);
            byte[] iv = StringUtil.hexStringToByteArray(ivSrc);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            FileInputStream file = new FileInputStream(uri.getPath());
            cryptoManager.decrypt(file, arrayOutputStream, secretKey, iv);
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                arrayOutputStream.writeTo(fileOutputStream);
                fileOutputStream.close();
            }
            arrayOutputStream.close();
        }
    }

    private String generateFileName() {
        return Arrays.toString(CommonUtils.generateRandom(16));
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

    public String generateFileName(FileModel fileModel) {
        return "" + StringUtil.byteArrayToHexString(fileModel.getIv()) +
                "." + getExtension(fileModel.getUri());
    }

    private File getInternalImageDirectory() {
        return context.getDir(AppConstants.LOCATION_INTERNAL_IMAGE, Context.MODE_PRIVATE);
    }

    private File getInternalTempDirectory() {
        return context.getDir(AppConstants.LOCATION_INTERNAL_TEMP, Context.MODE_PRIVATE);
    }

    @Override
    public boolean storeImage(ArrayList<FileModel> fileModels, SecretKey secretKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        for (FileModel image :
                fileModels) {
            storeImage(image, secretKey);
        }
        return true;
    }

    private boolean alreadyExist(Uri enc, Uri temp) {
        String encryptedFile = new File(enc.getPath()).getName();
        String tempFile = new File(temp.getPath()).getName();
        return encryptedFile.equals(tempFile);
    }

    private String getFileNameWithoutExtension(Uri uri) {
        if (uri != null) {
            if (uri.getScheme() != null && uri.getScheme().equals(AppConstants.FILE_FROM_FILE)) {
                uri.getLastPathSegment();
            } else if (uri.getScheme() != null && uri.getScheme().equals(AppConstants.FILE_FROM_CONTENT)) {
                Cursor cursor = context.getContentResolver().
                        query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String str = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    cursor.close();
                    return str;
                }
            } else {
                String path = uri.getPath();
                String fileName = path != null ? path.substring(path.lastIndexOf(File.separator) + 1) : generateFileName();
                return fileName.split("\\.")[0];
            }
        }
        return "";
    }

    private String generateTempFileName(Uri uri) {
        return getInternalTempDirectory() + "/" + getFileNameFromUri(uri);
    }

    private String getFileNameFromUri(Uri uri) {
        if (uri != null) {
            if (uri.getScheme() != null && uri.getScheme().equals(AppConstants.FILE_FROM_FILE)) {
                uri.getLastPathSegment();
            } else if (uri.getScheme() != null && uri.getScheme().equals(AppConstants.FILE_FROM_CONTENT)) {
                Cursor cursor = context.getContentResolver().
                        query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String str = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    cursor.close();
                    return str;
                }
            } else {
                String path = uri.getPath();
                return path != null ? path.substring(path.lastIndexOf(File.separator) + 1) : generateFileName();
            }
        }
        return generateFileName();
    }

    @Override
    public void removeTempImages() {

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
        if (StringUtil.isNullOrEmpty(type)) {
            type = context.getString(R.string.jpg);
        }
        return type;
    }
}
