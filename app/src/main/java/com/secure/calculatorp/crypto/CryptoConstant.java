package com.secure.calculatorp.crypto;

/**
 * Created by zakir on 29/12/2018.
 */

public class CryptoConstant {
    public static final String PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";
    public static final String PBKDF_2_WITH_HMAC_SHA_1 = "PBKDF2withHmacSHA1";
    public static final String KEY_STORE = "/vaultKeyStore.keystore";
    public static final String SECRET_KEY_ALIAS = "secretKeyAlias";
    public static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
    public static final String ENCRYPTION_ALGORITHM_TEST = "AES";
    public static final int MIN_PIN_LENGTH = 4;
}
