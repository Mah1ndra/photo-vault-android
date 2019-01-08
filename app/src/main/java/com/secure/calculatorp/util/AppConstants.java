package com.secure.calculatorp.util;

/**
 * Created by zakir on 05/01/2019.
 */

public class AppConstants {
    public static final String PREF_NAME = "calculator_pref";
    public static final int PERMISSION_EXTERNAL_STORAGE_CODE = 100;
    public static final int REQUEST_CODE_FILE_SELECTION = 101;
    public static final String LOCATION_INTERNAL_IMAGE = "image";
    public static final String INTENT_PIN_EXTRA = "intent_pin_extra";

    public enum Fragment {
        PHOTO,
        VIDEO,
        DOCUMENT
    }
}
