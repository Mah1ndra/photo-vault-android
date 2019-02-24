package com.secure.calculatorp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import java.security.SecureRandom;


public class CommonUtils {


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 150);
        return noOfColumns;
    }

    public static byte[] generateRandom(int numBytes) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

}
