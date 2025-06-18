package com.example.demo.wallet;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.MessageDigest;

/**
 * 钱包工具类：生成密钥、签名、验签、公钥地址派生等
 */
public class WalletUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成 ECDSA 密钥对（椭圆曲线加密）
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
            keyGen.initialize(256, new SecureRandom());
            return keyGen.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("❌ 密钥生成失败", e);
        }
    }

    /**
     * 私钥对数据签名，返回 Base64 编码签名串
     */
    public static String sign(String data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] sigBytes = signature.sign();
            return Base64.getEncoder().encodeToString(sigBytes);
        } catch (Exception e) {
            throw new RuntimeException("❌ 签名失败", e);
        }
    }

    /**
     * 验证签名是否正确
     */
    public static boolean verify(String data, String signatureStr, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            byte[] sigBytes = Base64.getDecoder().decode(signatureStr);
            return signature.verify(sigBytes);
        } catch (Exception e) {
            System.out.println("⚠️ 验签异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * Base64 -> 公钥对象
     */
    public static PublicKey getPublicKeyFromBase64(String base64) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("EC", "BC");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("❌ 公钥解析失败", e);
        }
    }

    /**
     * Base64 -> 私钥对象（用于签名恢复）
     */
    public static PrivateKey getPrivateKeyFromBase64(String base64) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("EC", "BC");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("❌ 私钥解析失败", e);
        }
    }

    /**
     * 公钥 -> Base64 字符串
     */
    public static String publicKeyToBase64(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 私钥 -> Base64 字符串
     */
    public static String privateKeyToBase64(PrivateKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 任意 Key -> Base64（兼容旧接口）
     */
    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 公钥 -> 钱包地址（模拟版，截断 SHA256 哈希）
     */
    public static String getAddress(PublicKey publicKey) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(publicKey.getEncoded());
            return Base64.getEncoder().encodeToString(hash).substring(0, 32);
        } catch (Exception e) {
            return Base64.getEncoder().encodeToString(publicKey.getEncoded()).substring(0, 32);
        }
    }
}

