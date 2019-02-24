package com.secure.calculatorp.crypto.cipher;

import com.secure.calculatorp.crypto.CryptoConstant;
import com.secure.calculatorp.util.CommonUtils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.inject.Inject;


public class AESGCMCipherAlgorithm implements CipherAlgorithm {


    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_PARAM_LENGTH = 128;


    @Inject
    public AESGCMCipherAlgorithm() {

    }

    @Override
    public Cipher createEncryptionCipher(Key secretKey, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        GCMParameterSpec spec = new GCMParameterSpec(GCM_PARAM_LENGTH, iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey,spec);
        return cipher;
    }

    @Override
    public Cipher createDecryptionCipher(Key secretKey, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        GCMParameterSpec spec = new GCMParameterSpec(GCM_PARAM_LENGTH, iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey,spec);
        return cipher;
    }
}
