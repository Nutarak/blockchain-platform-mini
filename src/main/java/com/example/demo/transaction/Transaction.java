package com.example.demo.transaction;

import com.example.demo.wallet.WalletUtils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 区块链交易类，包含发送者、接收者、金额和签名信息。
 */
public class Transaction {
    private String sender;           // 钱包地址（从公钥派生）
    private String receiver;         // 钱包地址
    private double amount;           // 金额

    private String signature;        // Base64签名
    private String publicKeyBase64;  // 发送者的公钥（用于验签）

    public Transaction() {}

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    /**
     * 获取用于签名/验签的原始数据
     */
    public String getRawData() {
        return sender + receiver + amount;
    }

    /**
     * 对当前交易进行签名（调用者需提供私钥和公钥）
     */
    public void sign(PrivateKey privateKey, PublicKey publicKey) {
        this.signature = WalletUtils.sign(getRawData(), privateKey);
        this.publicKeyBase64 = WalletUtils.publicKeyToBase64(publicKey);
    }

    /**
     * 验证当前交易签名是否有效
     */
    public boolean verifySignature() {
        if (signature == null || publicKeyBase64 == null) return false;
        PublicKey pubKey = WalletUtils.getPublicKeyFromBase64(publicKeyBase64);
        return WalletUtils.verify(getRawData(), signature, pubKey);
    }

    // ===== Getters & Setters =====
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    public void setPublicKeyBase64(String publicKeyBase64) {
        this.publicKeyBase64 = publicKeyBase64;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", signature='" + signature + '\'' +
                ", publicKeyBase64='" + publicKeyBase64 + '\'' +
                '}';
    }
}

