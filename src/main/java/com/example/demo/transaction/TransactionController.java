package com.example.demo.transaction;

import com.example.demo.block.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.security.PublicKey;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private BlockChain blockChain;

    // ✅ 主接口：提交交易（会自动验签）
    @PostMapping
    public String addTransaction(@RequestBody Transaction tx) {
        try {
            String rawData = tx.getRawData();
            String publicKeyBase64 = tx.getSender();

            PublicKey publicKey = TransactionUtils.decodePublicKey(publicKeyBase64);
            boolean isValid = TransactionUtils.verifySignature(rawData, tx.getSignature(), publicKey);

            if (!isValid) {
                return "Invalid signature. Transaction rejected.";
            }

            blockChain.addTransaction(tx);
            return "Transaction added successfully.";
        } catch (Exception e) {
            return "Transaction error: " + e.getMessage();
        }
    }

    // ✅ 用于签名交易（测试接口）
    @PostMapping("/sign")
    public Transaction signTransaction(@RequestBody Transaction tx, @RequestParam String privateKeyBase64) throws Exception {
        PrivateKey privateKey = TransactionUtils.decodePrivateKey(privateKeyBase64);
        String rawData = tx.getRawData();
        String signature = TransactionUtils.signTransaction(rawData, privateKey);
        tx.setSignature(signature);
        return tx;
    }

    // ✅ 用于验证签名是否正确（测试接口）
    @PostMapping("/verify")
    public boolean verifyTransaction(@RequestBody Transaction tx, @RequestParam String publicKeyBase64) throws Exception {
        PublicKey publicKey = TransactionUtils.decodePublicKey(publicKeyBase64);
        String rawData = tx.getRawData();
        return TransactionUtils.verifySignature(rawData, tx.getSignature(), publicKey);
    }
}

