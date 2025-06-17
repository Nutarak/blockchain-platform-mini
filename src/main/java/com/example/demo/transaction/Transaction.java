package com.example.demo.transaction;

/**
 * 区块链交易类，包含发送者、接收者、金额和签名信息。
 */
public class Transaction {
    private String sender;       // 发送者地址（公钥）
    private String receiver;     // 接收者地址（公钥）
    private double amount;       // 转账金额
    private String signature;    // 交易签名

    public Transaction() {
    }

    public Transaction(String sender, String receiver, double amount, String signature) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.signature = signature;
    }

    // ===== Getter & Setter =====
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

    /**
     * 获取用于签名或验签的原始数据（不含签名字段）
     */
    public String getRawData() {
        return sender + receiver + amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", signature='" + signature + '\'' +
                '}';
    }
}

