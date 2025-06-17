package com.example.demo.block;

import com.example.demo.transaction.Transaction;

import java.time.Instant;
import java.util.List;

public class Block {
    private int index;
    private long timestamp;
    private List<Transaction> transactions;
    private String previousHash;
    private String hash;

    public Block(int index, List<Transaction> transactions, String previousHash, String hash) {
        this.index = index;
        this.timestamp = Instant.now().toEpochMilli();
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

