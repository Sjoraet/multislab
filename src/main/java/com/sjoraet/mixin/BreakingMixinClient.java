package com.sjoraet.mixin;

import com.sjoraet.block.MultiSlabBlock;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import com.sjoraet.util.UseBlockMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class BreakingMixinClient {
    @Shadow
    private MinecraftClient client;

    @ModifyVariable(at = @At(value = "STORE"), method = "updateBlockBreakingProgress")
    public BlockSoundGroup breakingBlock(BlockSoundGroup group, BlockPos pos) {
        var state = this.client.world.getBlockState(pos);
        if (state.getBlock() instanceof MultiSlabBlock multiSlabBlock) {
            BlockEntity entity = this.client.world.getBlockEntity(pos);
            if (entity != null && entity instanceof MultiSlabBlockEntity slabEntity) {
                BlockState actualState = Block.getStateFromRawId(slabEntity.getTopBlockId());
                return actualState.getBlock().getSoundGroup(actualState);
            }
        }

        return group;
    }
}
