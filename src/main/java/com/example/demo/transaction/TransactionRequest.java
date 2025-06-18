package com.example.demo.transaction;

/**
 * 用于接收前端或客户端提交的交易数据
 */
public class TransactionRequest {

    private String sender;       // 发送方地址（钱包地址）
    private String receiver;     // 接收方地址（钱包地址）
    private double amount;       // 转账金额
    private String publicKey;    // 发送方的公钥（Base64编码）
    private String signature;    // 签名（Base64编码）

    public TransactionRequest() {
        // 默认构造函数用于反序列化
    }

    // Getters 和 Setters
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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

