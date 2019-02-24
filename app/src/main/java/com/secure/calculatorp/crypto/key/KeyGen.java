package com.secure.calculatorp.crypto.key;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;



public interface KeyGen {

    Key generateKey(Config config) throws NoSuchAlgorithmException, InvalidKeySpecException;

    class Config {
        private int keyLength;
        private int iterationCount;
        private byte[] salt;
        private char[] passPhrase;

        public Config(int keyLength, int iterationCount, byte[] salt, char[] passPhrase) {
            this.keyLength = keyLength;
            this.iterationCount = iterationCount;
            this.salt = salt;
            this.passPhrase = passPhrase;
        }

        public int getIterationCount() {
            return iterationCount;
        }

        public void setIterationCount(int iterationCount) {
            this.iterationCount = iterationCount;
        }

        public int getKeyLength() {
            return keyLength;
        }

        public void setKeyLength(int keyLength) {
            this.keyLength = keyLength;
        }

        public byte[] getSalt() {
            return salt;
        }

        public void setSalt(byte[] salt) {
            this.salt = salt;
        }

        public char[] getPassPhrase() {
            return passPhrase;
        }

        public void setPassPhrase(char[] passPhrase) {
            this.passPhrase = passPhrase;
        }
    }
}
