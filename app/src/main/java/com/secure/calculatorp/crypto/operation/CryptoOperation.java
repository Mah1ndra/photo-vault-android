package com.secure.calculatorp.crypto.operation;

import com.secure.calculatorp.crypto.key.KeyGen;

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
import javax.crypto.SecretKey;

/**
 * Created by zakir on 10/01/2019.
 */

public interface CryptoOperation {

    byte[] encrypt(byte[] data, Key secretKey, byte[] iv) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException;
    byte[] decrypt(byte[] data, Key secretKey, byte[] iv) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException;
    void encrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException;
    void decrypt(FileInputStream fileInputStream, ByteArrayOutputStream arrayOutputStream, Key key, byte[] iv) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException;

}
