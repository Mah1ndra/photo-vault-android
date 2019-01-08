package com.secure.calculatorp.crypto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zakir on 02/01/2019.
 */

@Singleton
public class CryptoCipherManager {


    @Inject
    public CryptoCipherManager() {
    }

    public byte[] encrypt(String data, SecretKey secretKey, byte[] initializationVector) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] encrypt(FileInputStream fileInputStream, SecretKey secretKey, byte[] initializationVector) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] bytes = cipher.doFinal(getBytesFromFile(cipherInputStream));
            return bytes;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException
                | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(FileInputStream fileInputStream,
                        SecretKey secretKey, byte[] initializationVector) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] bytes = cipher.doFinal(getBytesFromFile(cipherInputStream));
            return bytes;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(String data, SecretKey secretKey, byte[] initializationVector) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }


    private byte[] getBytesFromFile(CipherInputStream cipherInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] bytes = null;
        byte[] buffer = new byte[4096];
        try {
            while ((len = cipherInputStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
