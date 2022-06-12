package com.sjoraet.mixin;

import com.sjoraet.block.Blocks;
import com.sjoraet.block.MultiSlabBlock;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(WorldRenderer.class)
public class BreakMixin {
    @Shadow
    private ClientWorld world;

    @ModifyVariable(method = "processWorldEvent(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V", at = @At(value = "HEAD"), ordinal = 1)
    public int processWorldEvent(int data, PlayerEntity source, int eventId, BlockPos pos) {
        if (eventId == 2001 && Block.getStateFromRawId(data).getBlock() instanceof MultiSlabBlock multiSlabBlock) {
            BlockEntity entity = this.world.getBlockEntity(pos);
            if (entity != null && entity instanceof MultiSlabBlockEntity slabEntity) {
                return ((MultiSlabBlockEntity) entity).getTopBlockId();
            }
        }

        return data;
    }
}
