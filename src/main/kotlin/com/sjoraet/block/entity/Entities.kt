package com.sjoraet.block.entity

import com.sjoraet.block.Blocks
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

class Entities {
    companion object {
        var MULTISLAB_BLOCK_ENTITY: BlockEntityType<MultiSlabBlockEntity> = BlockEntityType.Builder.create(::MultiSlabBlockEntity, Blocks.MULTI_SLAB.second).build(null);

        fun init() {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, Blocks.MULTI_SLAB.first, MULTISLAB_BLOCK_ENTITY);
        }
    }
}