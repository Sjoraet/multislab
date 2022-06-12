package com.sjoraet.util

import com.sjoraet.block.Blocks
import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SlabBlock
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class UseBlockMixin {
    companion object {
        fun handle(player: PlayerEntity, world: World, hand: Hand, hitResult: BlockHitResult): ActionResult {
            val context = ItemPlacementContext(player, hand, player.getStackInHand(hand), hitResult);
            val canReplace = canReplace(world.getBlockState(hitResult.blockPos), context);
            val blockPos = if (!canReplace) {
                hitResult.blockPos.offset(hitResult.side);
            } else {
                hitResult.blockPos
            };

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

                if (!player.isCreative) {
                    handStack.decrement(1);
                }

                world.playSound(player, blockPos, handBlock.defaultState.soundGroup.placeSound, SoundCategory.BLOCKS, (handBlock.defaultState.soundGroup.volume + 1.0f) / 2.0f, handBlock.defaultState.soundGroup.pitch * 0.8f)

                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        }

        private fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
            if (state.block !is SlabBlock || Block.getBlockFromItem(context.stack.item) !is SlabBlock) {
                return false;
            }

            val slabType = state.get(SlabBlock.TYPE) as SlabType
            if (slabType == SlabType.DOUBLE) {
                return false;
            }
            val bl = context.hitPos.y - context.blockPos.y.toDouble() > 0.5
            val direction = context.side
            return if (slabType == SlabType.BOTTOM) {
                direction == Direction.UP || bl && direction.axis.isHorizontal
            } else {
                direction == Direction.DOWN || !bl && direction.axis.isHorizontal
            }
        }
    }
}