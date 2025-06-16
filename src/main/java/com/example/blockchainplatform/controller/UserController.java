package com.example.blockchainplatform.controller;

import com.example.blockchainplatform.entity.User;
import com.example.blockchainplatform.service.BlockchainService;
import com.example.blockchainplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlockchainService blockchainService;

    // ✅ 用户注册接口
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) throws Exception {
        String email = body.get("email");
        String password = body.get("password");

        User user = userService.registerUser(email, password);

        return Map.of(
                "email", user.getEmail(),
                "wallet", user.getWalletAddress()
        );
    }

    // ✅ 查询余额接口（已修复：加上 @RequestParam("address")）
    @GetMapping("/balance")
    public Map<String, String> getBalance(@RequestParam("address") String address) {
        BigDecimal balance = blockchainService.getEthBalance(address);
        return Map.of(
                "address", address,
                "balance", balance.toPlainString() + " ETH"
        );
    }
}

