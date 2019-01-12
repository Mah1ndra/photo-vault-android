package com.secure.calculatorp;

import android.net.Uri;

import com.secure.calculatorp.crypto.cipher.AESGCMCipherAlgorithm;
import com.secure.calculatorp.crypto.operation.AppCryptoOperation;
import com.secure.calculatorp.data.AppDataManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.data.file.AppFileHelper;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.util.CommonUtils;
import com.secure.calculatorp.util.StringUtil;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        AppFileHelper appFileHelper = new AppFileHelper(null, new AppCryptoOperation(new AESGCMCipherAlgorithm()));

        byte[] iv = CommonUtils.generateRandom(16);

        String s = StringUtil.byteArrayToHexString(iv);
        byte[] bytes = StringUtil.hexStringToByteArray(s);
        assertEquals(StringUtil.byteArrayToHexString(bytes), StringUtil.byteArrayToHexString(iv));
    }
}