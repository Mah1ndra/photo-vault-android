package com.secure.calculatorp.data.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v4.provider.DocumentFile;
import android.webkit.MimeTypeMap;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.di.ApplicationContext;
import com.secure.calculatorp.threading.ThreadExecutor;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.CommonUtils;
import com.secure.calculatorp.util.StringUtil;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


public class AppCryptoFileHelper implements FileHelper {


    private Context context;
    private CryptoManager cryptoManager;
    private ThreadExecutor threadExecutor;

    @Inject
    public AppCryptoFileHelper(@ApplicationContext Context context, CryptoManager appCryptoOperationManager,
                               ThreadExecutor threadExecutor) {
        this.context = context;
        this.cryptoManager = appCryptoOperationManager;
        this.threadExecutor = threadExecutor;
    }


    @Override
    public HashSet<Uri> getTemporaryImages() {
        File dir = getInternalTempDirectory();
        return getFileListInDirectory(dir);
    }

    private HashSet<Uri> getEncryptedImages() {
        File dir = getInternalImageDirectory();
        return getFileListInDirectory(dir);
    }

    private HashSet<Uri> getFileListInDirectory(File dir) {
        HashSet<Uri> fileSet = new HashSet<>();
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file :
                    files) {
                fileSet.add(Uri.parse(file.getAbsolutePath()));
            }
        }
        return fileSet;
    }

    @Override
    public boolean storeEncryptedImage(FileModel src, SecretKey secretKey) throws IOException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException {
        File des = getEncryptedFile(src);
        FileInputStream inputStream = (FileInputStream) context.getContentResolver().openInputStream(src.getUri());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encryptFile(des, src, secretKey, inputStream, outputStream);
        deleteImageFromPublicStorage(src.getUri());
        return true;
    }

    private void encryptFile(File des, FileModel src, SecretKey secretKey,
                             FileInputStream inputStream, ByteArrayOutputStream outputStream)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
        cryptoManager.encrypt(inputStream, outputStream, secretKey, src.getIv());
        try (FileOutputStream fileOutputStream = new FileOutputStream(des)) {
            outputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
        }
        outputStream.close();
    }

    @Override
    public void createTemporaryImages(SecretKey secretKey) {
        HashSet<Uri> imageList = getEncryptedImages();
        threadExecutor.createPool();
        for (Uri uri : imageList) {

            Runnable runnable = () -> {
                try {
                    createDecryptionRunnable(uri, secretKey);
                } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException
                        | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            };

            threadExecutor.execute(runnable);
        }
        threadExecutor.shutdown();
        threadExecutor.awaitTermination();
    }

    private void createDecryptionRunnable(Uri uri, SecretKey secretKey)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        File tempFile = new File(generateTempFileName(uri));
        if (tempFile.exists()) {
            return;
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

    @Override
    public boolean restoreImageToPublicStorage(Uri uri) {
        try {
            File file = new File(uri.getPath());
            File fileEncrypted = new File(getInternalImageDirectory()
                    + File.separator + getFileNameFromUri(uri));
            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(getExternelImageDirectory()
                    + File.separator + getFileNameFromUri(uri));
            byte[] bytes = new byte[4096];
            int b;
            while ((b = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, b);
            }
            outputStream.close();
            inputStream.close();
            boolean tempFileDeleted = file.delete();
            boolean encryptedFileDeleted = fileEncrypted.delete();
            return tempFileDeleted && encryptedFileDeleted;
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean storeEncryptedImage(ArrayList<FileModel> fileModels, SecretKey secretKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        for (FileModel image :
                fileModels) {
            storeEncryptedImage(image, secretKey);
        }
        return true;
    }

    private File getEncryptedFile(FileModel src) {
        return new File(getInternalImageDirectory(), generateFileName(src));
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
    public boolean deleteImageFromPublicStorage(Uri uri) {
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

    private File getExternelImageDirectory() {
        return Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES);
    }

    @Override
    public void removeTemporaryImages() {
        try {
            FileUtils.deleteDirectory(getInternalTempDirectory());
        } catch (IOException | NoSuchMethodError e) {
            e.printStackTrace();
        }
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
