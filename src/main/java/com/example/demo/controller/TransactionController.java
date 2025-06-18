package com.example.demo.controller;

import com.example.demo.block.BlockChain;
import com.example.demo.transaction.Transaction;
import com.example.demo.transaction.TransactionRequest;
import com.example.demo.wallet.WalletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private BlockChain blockChain;

    @PostMapping
    public String sendTransaction(@RequestBody TransactionRequest request) {
        try {
            PublicKey senderKey = WalletUtils.getPublicKeyFromBase64(request.getPublicKey());
            String rawData = request.getSender() + request.getReceiver() + request.getAmount();

            boolean valid = WalletUtils.verify(rawData, request.getSignature(), senderKey);
            if (!valid) {
                return "签名无效，交易被拒绝";
            }

            Transaction tx = new Transaction(
                    request.getSender(),
                    request.getReceiver(),
                    request.getAmount(),
                    request.getSignature(),
                    request.getPublicKey()
            );
            blockChain.addTransaction(tx);
            return "交易已提交";
        } catch (Exception e) {
            return "处理交易时出错: " + e.getMessage();
        }
    }
}

