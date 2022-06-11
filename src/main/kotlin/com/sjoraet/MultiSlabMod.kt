package com.sjoraet

import com.sjoraet.block.Blocks
import com.sjoraet.block.MultiSlabBlock
import com.sjoraet.block.entity.Entities
import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.mixin.event.interaction.MixinClientPlayerInteractionManager
import net.fabricmc.fabric.mixin.event.interaction.MixinServerPlayerInteractionManager
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
    }
}