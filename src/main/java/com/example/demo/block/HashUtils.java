package com.example.demo.block;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * 用于生成 SHA-256 哈希值的工具类
 */
public class HashUtils {

    /**
     * 计算输入字符串的 SHA-256 哈希
     * @param input 原始字符串
     * @return 64位的 SHA-256 十六进制哈希字符串
     */
    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将 byte[] 转为十六进制字符串
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                String hexPart = Integer.toHexString(0xff & b);
                if (hexPart.length() == 1) hex.append('0');
                hex.append(hexPart);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 hashing failed", e);
        }
    }
}

