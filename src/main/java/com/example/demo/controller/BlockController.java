package com.example.demo.controller;

import com.example.demo.block.Block;
import com.example.demo.block.BlockChain;
import com.example.demo.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blocks")
public class BlockController {

    @Autowired
    private BlockChain blockChain;

    /**
     * 获取当前区块链中的所有区块
     */
    @GetMapping
    public List<Block> getChain() {
        return blockChain.getChain();
    }

    /**
     * 添加一笔新的交易
     */
    @PostMapping("/transaction")
    public String addTransaction(@RequestBody Transaction transaction) {
        blockChain.addTransaction(transaction);
        return "✅ 交易已添加";
    }

    /**
     * 打包交易并生成新区块
     */
    @PostMapping("/mine")
    public Block mineBlock() {
        return blockChain.mineBlock();
    }

    /**
     * 验证当前区块链的完整性
     */
    @GetMapping("/validate")
    public Map<String, Object> validateChain() {
        boolean isValid = blockChain.isChainValid();
        Map<String, Object> result = new HashMap<>();
        result.put("valid", isValid);
        result.put("message", isValid ? "✅ 区块链有效" : "❌ 区块链已被篡改或不完整");
        return result;
    }
}

