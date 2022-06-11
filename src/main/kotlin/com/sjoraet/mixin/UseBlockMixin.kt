package com.sjoraet.mixin

import com.sjoraet.block.Blocks
import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World

class UseBlockMixin {
    companion object {
        fun handle(player: PlayerEntity, world: World, hand: Hand, hitResult: BlockHitResult): ActionResult {
            var blockPos = hitResult.blockPos;
            var state = world.getBlockState(blockPos);
            var groundBlock = state.block;
            var handStack = player.getStackInHand(hand);
            var handBlock = Block.getBlockFromItem(handStack.item);

            if (groundBlock is SlabBlock && handBlock is SlabBlock && groundBlock != handBlock && state.get(Properties.SLAB_TYPE) != SlabType.DOUBLE) {
                world.setBlockState(blockPos, Blocks.MULTI_SLAB.second.defaultState);
                if (state.get(Properties.SLAB_TYPE) == SlabType.TOP) {
                    (world.getBlockEntity(blockPos) as MultiSlabBlockEntity).bottomBlockId = Block.getRawIdFromState(handBlock.defaultState.with(Properties.SLAB_TYPE, SlabType.BOTTOM));
                    (world.getBlockEntity(blockPos) as MultiSlabBlockEntity).topBlockId = Block.getRawIdFromState(state)
                } else {
                    (world.getBlockEntity(blockPos) as MultiSlabBlockEntity).topBlockId = Block.getRawIdFromState(handBlock.defaultState.with(Properties.SLAB_TYPE, SlabType.BOTTOM));
                    (world.getBlockEntity(blockPos) as MultiSlabBlockEntity).bottomBlockId = Block.getRawIdFromState(state)
                }

                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        }
    }
}