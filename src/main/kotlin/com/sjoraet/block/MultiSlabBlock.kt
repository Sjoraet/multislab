package com.sjoraet.block

import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class MultiSlabBlock(settings: Settings): Block(settings), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        if (pos != null && state != null) {
            return MultiSlabBlockEntity(pos, state);
        }

        return null;
    }

}