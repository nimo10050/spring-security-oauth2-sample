package com.nimo.oauth.utils;


import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

public class CryptUtils {

    private static String DEFAULT_CHARSET = "utf-8";

    public static String[] decodeBasicHeader(String basicHeader) throws Exception {
        byte[] base64Token = basicHeader.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new Exception("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new Exception("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    public static String md5DigestAsHex(String source) {
        try {
            return DigestUtils.md5DigestAsHex(source.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return DigestUtils.md5DigestAsHex(source.getBytes());
        }
    }

    public static String md5DigestAsHex(InputStream inputStream) {
        try {
            return DigestUtils.md5DigestAsHex(inputStream);
        } catch (IOException e) {
            return null;
        }
    }


    public static byte[] sha256(byte[] source) {
        MessageDigest messageDigest;
        byte[] encoder = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(source);
            encoder = messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoder;
    }

    public static byte[] sha256(String source, String charsetName) {
        try {
            return sha256(source.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            return sha256(source.getBytes());
        }
    }


    public static byte[] sha256(String source) {
        return sha256(source, DEFAULT_CHARSET);
    }



    public static String sha256AsHex(String source, String charsetName) {
        return byte2Hex(sha256(source, charsetName));
    }



    public static String sha256AsHex(String source) {
        return sha256AsHex(source, DEFAULT_CHARSET);
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    public static String base64(byte[] source) {
        return Base64Utils.encodeToString(source);
    }


    public static String base64(String source, String charsetName) {
        try {
            return Base64Utils.encodeToString(source.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            return Base64Utils.encodeToString(source.getBytes());
        }
    }

    public static String base64(String source) {
        return base64(source, DEFAULT_CHARSET);
    }

    public static String unbase64(String source, String charsetName) {

        try {
            byte[] decoderBytes =  Base64Utils.decode(source.getBytes(charsetName));
            return new String(decoderBytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            byte[] decoderBytes = Base64Utils.decodeFromString(source);
            return new String(decoderBytes);
        }
    }

    public static String unbase64(String source) {
        return unbase64(source, DEFAULT_CHARSET);
    }

    public static void main(String[] argv) {
//        String sha256Str = CryptUtils.sha256AsHex("123456");
//        byte[] sha256Bytes = CryptUtils.sha256("123456");
//        String auth = CryptUtils.base64(ByteUtils.merge("test:".getBytes(),sha256Bytes));
//        System.out.println(sha256Str);
//        System.out.println(auth);
//
//        Date date = new Date();
//        System.out.println(date.toString());
        String s = "asdfkj1234a";
        String str = CryptUtils.md5DigestAsHex(s);
        System.out.println(str);
        System.out.println(str.length());

    }

}
