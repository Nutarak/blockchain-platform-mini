package com.example.demo.transaction;

import com.example.demo.wallet.WalletUtils;

import java.security.PrivateKey;

public class TransactionTest {

    public static void main(String[] args) {
        // ======== 示例交易参数 ========
        String sender = "3zRXaYgP4DAURU4qOZq24s5C8Qi/2qJ4";
        String receiver = "RECEIVER_ADDRESS";  // ✅ TODO: 替换成实际接收者地址
        double amount = 5.0;

        String privateKeyBase64 = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgwa/w9Gb9Sk/8k/PDWc79BRdvotZ+yxdfqss6Za5cd+qgCgYIKoZIzj0DAQehRANCAAROssM4GUzuu/dHG4Qzy6HTOEFVpueUItmKsc1d3c0uHBBwrKvPuwLA1N7sPl8LClEEON5rFU7V6QeLU50tS36s";

        try {
            // 构造原始数据并签名
            String rawData = sender + receiver + amount;
            PrivateKey privateKey = WalletUtils.getPrivateKeyFromBase64(privateKeyBase64);
            String signature = WalletUtils.sign(rawData, privateKey);

            System.out.println("✅ 签名成功:");
            System.out.println("sender: " + sender);
            System.out.println("receiver: " + receiver);
            System.out.println("amount: " + amount);
            System.out.println("signature (Base64): " + signature);
        } catch (Exception e) {
            System.out.println("❌ 签名失败: " + e.getMessage());
        }
    }
}

