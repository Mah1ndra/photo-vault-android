package com.secure.calculatorp.crypto.key;

import android.os.Build;

import com.secure.calculatorp.crypto.CryptoConstant;
import com.secure.calculatorp.util.CommonUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;



public class PBKDF2KeyGenerator implements KeyGen {


    @Inject
    public PBKDF2KeyGenerator() {
    }

    @Override
    public SecretKey generateKey(Config config)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            secretKeyFactory = SecretKeyFactory.getInstance(KeyAlgorithm.PBKDF_2_WITH_HMAC_SHA_1);
        } else {
            secretKeyFactory = SecretKeyFactory.getInstance(KeyAlgorithm.PBKDF_2_WITH_HMAC_SHA_256);
        }
        KeySpec keySpec = new PBEKeySpec(config.getPassPhrase(), config.getSalt(), config.getIterationCount(), config.getKeyLength());

        return secretKeyFactory.generateSecret(keySpec);
    }

}
