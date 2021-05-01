package com.iridium.iridiumskyblock.multiversion;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.ChunkSnapshot;

public class V1_15_R1 implements Multiversion{
    @Override
    public XMaterial getBlock(ChunkSnapshot chunk, int x, int y, int z) {
        return XMaterial.matchXMaterial(chunk.getBlockType(x, y, z));
    }
}