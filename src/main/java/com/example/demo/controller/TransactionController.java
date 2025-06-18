package com.example.demo.controller;

import com.example.demo.block.BlockChain;
import com.example.demo.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private BlockChain blockChain;

    /**
     * 接收签名后的交易（客户端负责签名）
     */
    @PostMapping("/send")
    public String sendTransaction(@RequestBody Transaction tx) {
        if (!tx.verifySignature()) {
            return "❌ 交易签名验证失败，交易未被接受";
        }

        blockChain.addTransaction(tx);
        return "✅ 交易已验签并加入交易池";
    }
}

