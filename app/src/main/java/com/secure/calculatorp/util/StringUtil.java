package com.secure.calculatorp.util;

import android.content.Context;
import android.util.Base64;

import com.secure.calculatorp.R;

/**
 * Created by zakir on 02/01/2019.
 */

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
}
