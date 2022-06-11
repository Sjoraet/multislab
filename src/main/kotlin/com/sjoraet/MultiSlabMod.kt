package com.sjoraet

import com.sjoraet.block.Blocks
import com.sjoraet.block.MultiSlabBlock
import com.sjoraet.block.entity.Entities
import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.block.enums.SlabType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.quiltmc.qsl.base.api.event.Event

class MultiSlabMod : ModInitializer {
    companion object {
        val MODID = "multislab";
    }
    override fun onInitialize(mod: ModContainer?) {
        Blocks.init();
        Entities.init();

        UseBlockCallback.EVENT.register() { player, world, hand, hitResult ->
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

                ActionResult.SUCCESS;
            } else {
                ActionResult.PASS
            }
        }
    }
}