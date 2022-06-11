package com.sjoraet.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.PillarBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos

class MultiSlabBlockEntity(position: BlockPos, state: BlockState): BlockEntity(Entities.MULTISLAB_BLOCK_ENTITY, position, state) {
    var bottomBlockId: Int = 0;
    var topBlockId: Int = 0;
}