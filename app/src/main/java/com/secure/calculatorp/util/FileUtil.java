package com.secure.calculatorp.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;


public class FileUtil {

    public static boolean writeBytesToFile(byte[] bFile, File destFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            fileOutputStream.write(bFile);
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
