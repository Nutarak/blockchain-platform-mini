package com.example.demo.transaction;

import java.security.*;
import java.util.Base64;

public class TransactionUtils {

    // 使用私钥对原始交易数据进行签名
    public static String signTransaction(String rawData, PrivateKey privateKey) throws Exception {
        Signature signer = Signature.getInstance("SHA256withECDSA");
        signer.initSign(privateKey);
        signer.update(rawData.getBytes());
        byte[] signature = signer.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    // 使用公钥验证交易签名
    public static boolean verifySignature(String rawData, String signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withECDSA");
        verifier.initVerify(publicKey);
        verifier.update(rawData.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return verifier.verify(signatureBytes);
    }

    // 将Base64字符串还原为公钥对象
    public static PublicKey decodePublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(keyBytes));
    }

    // 将Base64字符串还原为私钥对象
    public static PrivateKey decodePrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(keyBytes));
    }
}

