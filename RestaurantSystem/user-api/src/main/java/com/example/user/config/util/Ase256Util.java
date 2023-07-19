package com.example.user.config.util;


;
import com.example.user.config.common.ReadFile;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Ase256Util {

    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String KEY = ReadFile.readFile(new File("D:\\Ase256UtilKey.txt"));

    private static final String IV = KEY.substring(0,16);

    public static String encrypt(String text){
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        }catch (Exception e){
            return null;
        }
    }

    public static String decrypted(String cipherText){
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(),"AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivParameterSpec);
            byte[] decryptedBytes = Base64.decodeBase64(cipherText);
            byte[] decrypted = cipher.doFinal(decryptedBytes);
            return new String(decrypted,StandardCharsets.UTF_8);
        } catch (Exception e){
            return null;
        }
    }
}
