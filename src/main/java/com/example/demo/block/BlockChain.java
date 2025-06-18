package com.example.demo.block;

import com.example.demo.transaction.Transaction;
import com.example.demo.utils.HashUtils;
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
     * 打包交易并创建新区块（包含签名验证）
     */
    public Block mineBlock() {
        List<Transaction> validTxs = new ArrayList<>();
        for (Transaction tx : pendingTransactions) {
            if (tx.verifySignature()) {
                validTxs.add(tx);
            } else {
                System.out.println("❌ 交易签名无效，已跳过: " + tx);
            }
        }

        if (validTxs.isEmpty()) {
            System.out.println("⚠️ 没有有效交易，区块未创建");
            return null;
        }

        Block previous = getLatestBlock();
        int index = chain.size();
        String previousHash = previous.getHash();

        // 创建新区块
        String rawData = index + previousHash + validTxs.toString();
        String hash = HashUtils.sha256(rawData);
        Block newBlock = new Block(index, validTxs, previousHash, hash);

        // 写入区块链 & 清空交易池中有效交易
        chain.add(newBlock);
        pendingTransactions.removeAll(validTxs);

        System.out.println("✅ 区块 #" + index + " 已创建，交易数: " + validTxs.size());
        return newBlock;
    }

    /**
     * ✅ 验证整条区块链是否完整（哈希正确 + 链条连续）
     */
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // 重算当前区块的哈希
            String expectedHash = HashUtils.sha256(
                current.getIndex() + current.getPreviousHash() + current.getTransactions().toString()
            );

            if (!current.getHash().equals(expectedHash)) {
                System.out.println("❌ 区块 " + current.getIndex() + " 哈希不匹配！");
                return false;
            }

            if (!current.getPreviousHash().equals(previous.getHash())) {
                System.out.println("❌ 区块 " + current.getIndex() + " 前一个哈希不匹配！");
                return false;
            }
        }
        System.out.println("✅ 区块链完整性验证通过！");
        return true;
    }

    /**
     * 使用 SHA-256 计算区块哈希
     */
    private String calculateHash(int index, List<Transaction> txs, String prevHash) {
        String rawData = index + prevHash + txs.toString();
        return HashUtils.sha256(rawData);  // ✅ 安全哈希
    }
}

