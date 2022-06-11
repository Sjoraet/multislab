package com.sjoraet.render

import com.sjoraet.block.entity.MultiSlabBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.enums.SlabType
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.BlockRenderManager
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties

class MultiSlabRenderer: BlockEntityRenderer<MultiSlabBlockEntity> {
    override fun render(entity: MultiSlabBlockEntity?, tickDelta: Float, matrices: MatrixStack?, vertexConsumers: VertexConsumerProvider?, light: Int, overlay: Int) {
        if (entity == null) {
            return;
        }
        if (vertexConsumers == null) {
            return;
        }

        val world = entity.world ?: return;

        val bottomBlock = Block.getStateFromRawId(entity.bottomBlockId).with(Properties.SLAB_TYPE, SlabType.BOTTOM);
        val topBlock = Block.getStateFromRawId(entity.topBlockId).with(Properties.SLAB_TYPE, SlabType.TOP);

        val manager = MinecraftClient.getInstance().blockRenderManager;
        val layer = vertexConsumers.getBuffer(RenderLayer.getSolid())
        manager.modelRenderer.render(world, manager.getModel(bottomBlock), bottomBlock, entity.pos, matrices, layer, false, world.random, 0, OverlayTexture.DEFAULT_UV )
        manager.modelRenderer.render(world, manager.getModel(topBlock), bottomBlock, entity.pos, matrices, layer, false, world.random, 0, OverlayTexture.DEFAULT_UV )
    }
}