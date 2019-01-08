package com.secure.calculatorp.crypto.cipher;

import com.secure.calculatorp.crypto.CryptoConstant;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Created by zakir on 02/01/2019.
 */

public class StringCipher implements BaseCipher{


    public byte[] encrypt(String data, SecretKey secretKey, byte[] salt) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, salt);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(String data, SecretKey secretKey, byte[] salt) {
        GCMParameterSpec spec = new GCMParameterSpec(CryptoConstant.GCM_PARAM_LENGTH, salt);
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstant.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
