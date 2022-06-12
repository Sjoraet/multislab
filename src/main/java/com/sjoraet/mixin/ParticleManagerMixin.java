package com.sjoraet.mixin;

import com.sjoraet.block.MultiSlabBlock;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Shadow
    private ClientWorld world;

    @ModifyArg(method = "addBlockBreakingParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/BlockDustParticle;<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V"), index = 7)
    public BlockState addBlockBreakingParticles(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState state, BlockPos pos) {
        if (this.world.getBlockState(pos).getBlock() instanceof MultiSlabBlock multiSlabBlock) {
            BlockEntity entity = this.world.getBlockEntity(pos);
            if (entity != null && entity instanceof MultiSlabBlockEntity multiSlabEntity) {
                return Block.getStateFromRawId(multiSlabEntity.getTopBlockId());
            }
        }

        return state;
    }
}
