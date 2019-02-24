package com.secure.calculatorp.crypto;

import com.secure.calculatorp.crypto.cipher.CipherAlgorithm;
import com.secure.calculatorp.crypto.key.KeyGen;
import com.secure.calculatorp.crypto.key.KeyManager;
import com.secure.calculatorp.crypto.operation.CryptoOperation;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;



public class AppCryptoManager implements CryptoManager {


    private final CryptoOperation cryptoOperation;
    private final KeyManager keyManager;

    @Inject
    public AppCryptoManager(CryptoOperation cryptoOperation, KeyManager keyManager) {

        this.cryptoOperation = cryptoOperation;
        this.keyManager = keyManager;
    }


    @Override
    public Key generateKey(KeyGen.Config config) throws InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException {
        return keyManager.generateKey(config);
    }

    @Override
    public Key retrieveKey(char[] passPhrase) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException {
        return keyManager.retrieveKey(passPhrase);
    }

    @Override
    public byte[] encrypt(byte[] data, Key secretKey, byte[] iv)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        return cryptoOperation.encrypt(data, secretKey, iv);
    }

    @Override
    public byte[] decrypt(byte[] data, Key secretKey, byte[] iv)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        return cryptoOperation.encrypt(data, secretKey, iv);
    }

    @Override
    public void encrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        cryptoOperation.encrypt(fileInputStream, arrayOutputStream, key, iv);
    }

    @Override
    public void decrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException, IOException {
        cryptoOperation.decrypt(fileInputStream, arrayOutputStream, key, iv);
    }
}
