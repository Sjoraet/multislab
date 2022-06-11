package com.sjoraet

import com.sjoraet.block.entity.Entities
import com.sjoraet.render.MultiSlabRenderer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.mixin.client.rendering.MixinBlockEntityRenderers
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import org.quiltmc.qsl.command.impl.client.ClientInitializer

class MultiSlabModClient: ClientModInitializer {
    override fun onInitializeClient(mod: ModContainer?) {
        BlockEntityRendererRegistry.register(Entities.MULTISLAB_BLOCK_ENTITY) { _ ->
            MultiSlabRenderer()
        }
    }
}