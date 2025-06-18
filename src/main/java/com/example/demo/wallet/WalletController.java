package com.example.demo.wallet;

import org.springframework.web.bind.annotation.*;

import java.security.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @PostMapping
    public Wallet createWallet() throws Exception {
        KeyPair keyPair = WalletUtils.generateKeyPair();
        String publicKey = WalletUtils.publicKeyToBase64(keyPair.getPublic());   // ✅ 修正这里
        String privateKey = WalletUtils.privateKeyToBase64(keyPair.getPrivate()); // ✅ 修正这里
        String address = WalletUtils.getAddress(keyPair.getPublic());
        return new Wallet(address, publicKey, privateKey);
    }

    @GetMapping("/generate") // ✅ 方便 curl 测试
    public Wallet generateWallet() throws Exception {
        return createWallet();
    }
}

