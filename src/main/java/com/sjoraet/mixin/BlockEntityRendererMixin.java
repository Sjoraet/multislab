package com.sjoraet.mixin;

import com.sjoraet.block.entity.Entities;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import com.sjoraet.render.MultiSlabRenderer;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockEntityRendererFactories.class)
public class BlockEntityRendererMixin {
    @Shadow()
    @Final
    private static Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> FACTORIES;

    @Inject(at = @At("RETURN"), method = "<clinit>*")
    private static void init(CallbackInfo ci) {
        BlockEntityRendererRegistryImpl.setup(((t, factory) -> FACTORIES.put(t, factory)));
    }

    private static <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {
        FACTORIES.put(type, factory);
    }

    static {
        register(Entities.Companion.getMULTISLAB_BLOCK_ENTITY(), (s) -> new MultiSlabRenderer());
    }
}