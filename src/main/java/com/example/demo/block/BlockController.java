package com.example.demo.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocks")
public class BlockController {

    @Autowired
    private BlockChain blockChain;

    // ✅ 查看当前链中所有区块
    @GetMapping
    public List<Block> getAllBlocks() {
        return blockChain.getChain();
    }

    // ✅ 打包交易为新区块
    @PostMapping("/mine")
    public Block mineBlock() {
        return blockChain.mineBlock();
    }
}

