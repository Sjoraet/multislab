package com.sjoraet.block

import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameter
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MultiSlabBlock(settings: Settings): Block(settings), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        if (pos != null && state != null) {
            return MultiSlabBlockEntity(pos, state);
        }

        return null;
    }

    override fun getDroppedStacks(state: BlockState?, builder: LootContext.Builder?): MutableList<ItemStack> {
        val blockEntity = builder?.get(LootContextParameters.BLOCK_ENTITY) as MultiSlabBlockEntity;

        val list = ArrayList<ItemStack>()
        if (blockEntity != null) {
            list.addAll(Block.getStateFromRawId(blockEntity.topBlockId).getDroppedStacks(builder));
            list.addAll(Block.getStateFromRawId(blockEntity.bottomBlockId).getDroppedStacks(builder));

        }
        return list;
    }
}