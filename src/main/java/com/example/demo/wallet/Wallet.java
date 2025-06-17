package com.example.demo.wallet;

public class Wallet {
    private String address;
    private String publicKey;
    private String privateKey;

    public Wallet(String address, String publicKey, String privateKey) {
        this.address = address;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getAddress() {
        return address;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}

