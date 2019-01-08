package com.secure.calculatorp.crypto;

import android.content.Context;
import android.os.Build;

import com.secure.calculatorp.di.ApplicationContext;
import com.secure.calculatorp.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;


/**
 * Created by zakir on 28/12/2018.
 */


public class CryptoKeyManager {

    private Context context;


    @Inject
    public CryptoKeyManager(@ApplicationContext Context context) {
        this.context = context;
    }

    public SecretKey generateKey(char[] passphraseOrPin)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            secretKeyFactory = SecretKeyFactory.getInstance(CryptoConstant.PBKDF_2_WITH_HMAC_SHA_1);
        } else {
            secretKeyFactory = SecretKeyFactory.getInstance(CryptoConstant.PBKDF_2_WITH_HMAC_SHA_256);
        }
        KeySpec keySpec = new PBEKeySpec(passphraseOrPin, generateRandom(32), CryptoConstant.ITERATION_KEY_GENERATOR, CryptoConstant.KEY_LENGTH);
        return secretKeyFactory.generateSecret(keySpec);
    }

    public byte[] generateRandom(int numBytes) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    public String generateIV(int numBytes) {
        byte[] bytes = generateRandom(numBytes);
        return StringUtil.getIvStringFromByteArray(bytes);
    }

    public void storeKey(char[] password, SecretKey secretKey) throws KeyStoreException {
        final KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        try {
            ks.load(null, password);
        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        KeyStore.ProtectionParameter protectionParameter =
                new KeyStore.PasswordProtection(password);

        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(secretKey);
        ks.setEntry(CryptoConstant.SECRET_KEY_ALIAS, skEntry, protectionParameter);
//        try {
//            ks.store(new FileOutputStream(context.getFilesDir() + "/dd.keystore"), password);
//        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
//            e.printStackTrace();
//        }

        try (FileOutputStream fos = new FileOutputStream(context.getFilesDir() + CryptoConstant.KEY_STORE)) {
            ks.store(fos, password);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public SecretKey getSecretKey(char[] password) throws KeyStoreException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        try (FileInputStream fis = new FileInputStream(context.getFilesDir() + CryptoConstant.KEY_STORE)) {
            ks.load(fis, password);
        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
            return null;
        }

        KeyStore.ProtectionParameter protectionParameter =
                new KeyStore.PasswordProtection(password);

        KeyStore.SecretKeyEntry skEntry;
        try {
            skEntry = (KeyStore.SecretKeyEntry) ks.getEntry(CryptoConstant.SECRET_KEY_ALIAS,
                    protectionParameter);

            return skEntry.getSecretKey();
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
