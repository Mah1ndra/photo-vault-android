package com.secure.calculatorp.crypto.key;

import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by zakir on 10/01/2019.
 */

public interface KeyManager {

    Key generateKey(KeyGen.Config config) throws InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException;
    Key retrieveKey(char[] passPhrase) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException;
}
