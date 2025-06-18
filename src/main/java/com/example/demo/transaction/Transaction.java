package com.example.demo.transaction;

import com.example.demo.wallet.WalletUtils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 区块链交易类，包含发送者、接收者、金额和签名信息。
 */
public class Transaction {
    private String sender;
    private String receiver;
    private double amount;
    private String signature;
    private String publicKeyBase64;

    public Transaction() {}

    // ✅ 控制器使用的完整构造函数（含签名和公钥）
    public Transaction(String sender, String receiver, double amount, String publicKeyBase64, String signature) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.publicKeyBase64 = publicKeyBase64;
        this.signature = signature;
    }

    // ✅ 用于后端签名的构造函数
    public Transaction(String sender, String receiver, double amount, String signature) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.signature = signature;
    }

    public String getRawData() {
        return sender + receiver + amount;
    }

    public void sign(PrivateKey privateKey, PublicKey publicKey) {
        this.signature = WalletUtils.sign(getRawData(), privateKey);
        this.publicKeyBase64 = WalletUtils.publicKeyToBase64(publicKey);
    }

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

