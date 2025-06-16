package com.example.blockchainplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class BlockchainService {

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ 使用你的 Etherscan API Key
    private final String apiKey = "UFFWTJUPCMM7WVSIR461K4D186NCE353QU";

    public BigDecimal getEthBalance(String address) {
        // 构造查询 URL
        String url = UriComponentsBuilder.fromHttpUrl("https://api.etherscan.io/api")
                .queryParam("module", "account")
                .queryParam("action", "balance")
                .queryParam("address", address)
                .queryParam("tag", "latest")
                .queryParam("apikey", apiKey)
                .toUriString();

        try {
            EtherscanResponse response = restTemplate.getForObject(url, EtherscanResponse.class);

            if (response != null && "1".equals(response.status)) {
                BigDecimal wei = new BigDecimal(new BigInteger(response.result));
                return wei.divide(BigDecimal.TEN.pow(18)); // wei → ETH
            } else {
                throw new RuntimeException("查询失败: " + (response != null ? response.message : "未知错误"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用 Etherscan 接口失败，请检查网络或 API Key 是否有效");
        }
    }

    // 内部类用于接收 Etherscan 返回的 JSON 数据结构
    private static class EtherscanResponse {
        public String status;
        public String message;
        public String result;
    }
}

