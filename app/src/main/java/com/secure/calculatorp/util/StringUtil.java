package com.secure.calculatorp.util;

import android.content.Context;
import android.util.Base64;

import com.secure.calculatorp.R;

import java.nio.charset.Charset;


public class StringUtil {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


    public static boolean isOperatorSelected(String s, Context context) {
        return s.contains(context.getString(R.string.str_plus))
                || s.contains(context.getString(R.string.str_minus))
                || s.contains(context.getString(R.string.str_multiply))
                || s.contains(context.getString(R.string.str_division));
    }

    public static String getIvStringFromByteArray(byte[] iv) {
        return Base64.encodeToString(iv, Base64.DEFAULT);
    }

    public static byte[] getIvBytesFromString(String iv) {
        return Base64.decode(iv, Base64.DEFAULT);
    }

    public static String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
