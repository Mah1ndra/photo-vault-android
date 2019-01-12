package com.secure.calculatorp.crypto.operation;

import com.secure.calculatorp.crypto.CryptoConstant;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.crypto.cipher.CipherAlgorithm;
import com.secure.calculatorp.crypto.key.KeyGen;
import com.secure.calculatorp.crypto.key.KeyManager;
import com.secure.calculatorp.util.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zakir on 02/01/2019.
 */

@Singleton
public class AppCryptoOperation implements CryptoOperation {


    private final CipherAlgorithm cipherAlgorithm;


    @Inject
    public AppCryptoOperation(CipherAlgorithm cipherAlgorithm) {

        this.cipherAlgorithm = cipherAlgorithm;
    }

//    @Override
//    protected byte[] encrypt(byte[] data, SecretKey secretKey, byte[] initializationVector) {
//        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
//        try {
//            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
//            return cipher.doFinal(data);
//        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public void encrypt(FileInputStream fileInputStream, SecretKey secretKey, OutputStream fileOutputStream, byte[] iv) {
//        try {
//            Cipher cipher = createEncryptionCipher(secretKey, iv);
//            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
//            int b;
//            byte[] d = new byte[4096];
//            while ((b = fileInputStream.read(d)) != -1) {
//                cipherOutputStream.write(d, 0, b);
//            }
//            cipherOutputStream.flush();
//            cipherOutputStream.close();
//            fileInputStream.close();
//        } catch (InvalidAlgorithmParameterException
//                | InvalidKeyException
//                | NoSuchPaddingException
//                | NoSuchAlgorithmException
//                | IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private Cipher createEncryptionCipher(SecretKey secretKey, byte[] iv) throws NoSuchPaddingException,
//            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
//        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, iv);
//        Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
//        return cipher;
//    }
//
//    public void decrypt(FileInputStream fileInputStream,
//                        SecretKey secretKey, byte[] initializationVector, FileOutputStream fileOutputStream) {
//        try {
//            GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
//            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
//            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
//            int b;
//            byte[] d = new byte[4096];
//            while ((b = cipherInputStream.read(d)) != -1) {
//                fileOutputStream.write(d, 0, b);
//            }
//            fileOutputStream.flush();
//            cipherInputStream.close();
//            fileInputStream.close();
//            cipher.doFinal();
//        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public byte[] decrypt(byte[] bytes, SecretKey secretKey, byte[] initializationVector) {
//        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, initializationVector);
//        try {
//            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
//            return cipher.doFinal(bytes);
//        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//
//    private byte[] getBytesFromFile(CipherInputStream cipherInputStream) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        int len;
//        byte[] bytes = null;
//        byte[] buffer = new byte[4096];
//        try {
//            while ((len = cipherInputStream.read(buffer, 0, buffer.length)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, len);
//            }
//            byteArrayOutputStream.flush();
//            bytes = byteArrayOutputStream.toByteArray();
//            byteArrayOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return bytes;
//    }

//    @Override
//    public void setSecretKey(KeyGen.Config config) throws
//            InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException {
//        keyManager.generateKey(config);
//    }
//
//    @Override
//    public Key getSecretKey(char[] passPhrase)
//            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
//        return keyManager.retrieveKey(passPhrase);
//    }

    @Override
    public byte[] encrypt(byte[] data, Key secretKey, byte[] iv) throws
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = cipherAlgorithm.createEncryptionCipher(secretKey, iv);
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, Key secretKey, byte[] iv) throws
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = cipherAlgorithm.createEncryptionCipher(secretKey, iv);
        return cipher.doFinal(data);
    }

    @Override
    public void encrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        Cipher cipher = cipherAlgorithm.createEncryptionCipher(key, iv);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(arrayOutputStream, cipher);
        int b;
        byte[] d = new byte[4096];
        while ((b = fileInputStream.read(d)) != -1) {
            cipherOutputStream.write(d, 0, b);
        }
        cipherOutputStream.flush();
        cipherOutputStream.close();
        fileInputStream.close();
    }

    @Override
    public void decrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
        Cipher cipher = cipherAlgorithm.createDecryptionCipher(key, iv);
        CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
        int b;
        byte[] d = new byte[4096];
        while ((b = cipherInputStream.read(d)) != -1) {
            arrayOutputStream.write(d, 0, b);
        }
        arrayOutputStream.flush();
        cipherInputStream.close();
        fileInputStream.close();
    }
}
