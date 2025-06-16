package com.example.blockchainplatform.service;

import com.example.blockchainplatform.entity.User;
import com.example.blockchainplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;
import org.web3j.crypto.ECKeyPair;

import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String plainPassword) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        // 密码加密
        String encryptedPassword = passwordEncoder.encode(plainPassword);

        // 生成 ETH 钱包地址（Web3j）
        ECKeyPair keyPair = Keys.createEcKeyPair(new SecureRandom());
        String walletAddress = "0x" + Keys.getAddress(keyPair.getPublicKey());

        User user = new User();
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setWalletAddress(walletAddress);

        return userRepository.save(user);
    }
}

