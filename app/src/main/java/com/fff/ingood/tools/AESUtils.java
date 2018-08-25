package com.fff.ingood.tools;

import android.util.Base64;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ElminsterII on 2018/8/25.
 */
public class AESUtils {
    private final static String IV_AES = "1234567890ABCDEF" ;

    public static String encryptAES(String strSrc, String strKey) {
        try {
            AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(IV_AES.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(strKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, algorithmParameterSpec);

            return Base64.encodeToString(cipher.doFinal(strSrc.getBytes()), Base64.DEFAULT);
        }
        catch(Exception ex) {
            return null;
        }
    }

    public static String decryptAES(String strSrc, String strKey) {
        try {
            AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(IV_AES.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(strKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, algorithmParameterSpec);
            return new String(cipher.doFinal(Base64.decode(strSrc.getBytes(), Base64.DEFAULT)));
        }
        catch(Exception ex) {
            return null;
        }
    }
}
