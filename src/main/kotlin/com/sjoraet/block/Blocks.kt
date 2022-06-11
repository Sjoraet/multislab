package com.sjoraet.block

import com.sjoraet.MultiSlabMod.Companion.MODID
import net.minecraft.block.AbstractBlock
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class Blocks {
    companion object {
        var MULTI_SLAB = Pair(Identifier(MODID, "multi_slab"), MultiSlabBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.STONE_GRAY).strength(1.0F)));

        fun init() {
            Registry.register(Registry.BLOCK, MULTI_SLAB.first, MULTI_SLAB.second);
        }
    }
}