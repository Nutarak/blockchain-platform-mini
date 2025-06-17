package com.example.demo.block;

import com.example.demo.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 区块链核心类，负责链的维护、交易打包与新区块生成
 */
@Component  // ✅ 关键注解：注册为 Spring Bean
public class BlockChain {
    private final List<Block> chain = new ArrayList<>();
    private final List<Transaction> pendingTransactions = new ArrayList<>();

    public BlockChain() {
        // 创建创世区块
        Block genesis = new Block(0, new ArrayList<>(), "0", "GENESIS_HASH");
        chain.add(genesis);
    }

    /**
     * 获取当前链上的所有区块
     */
    public List<Block> getChain() {
        return chain;
    }

    /**
     * 获取最新的区块
     */
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    /**
     * 添加一个新的交易到交易池
     */
    public void addTransaction(Transaction tx) {
        pendingTransactions.add(tx);
    }

    /**
     * 打包交易并创建新区块
     */
    public Block mineBlock() {
        Block previous = getLatestBlock();
        int index = chain.size();
        String previousHash = previous.getHash();

        // 创建新区块
        List<Transaction> transactions = new ArrayList<>(pendingTransactions);
        String hash = calculateHash(index, transactions, previousHash);
        Block newBlock = new Block(index, transactions, previousHash, hash);

        // 写入区块链 & 清空交易池
        chain.add(newBlock);
        pendingTransactions.clear();

        return newBlock;
    }

    /**
     * 使用 SHA-256 计算区块哈希
     */
    private String calculateHash(int index, List<Transaction> txs, String prevHash) {
        String rawData = index + prevHash + txs.toString();
        return HashUtils.sha256(rawData);  // ✅ 安全哈希
    }
}

