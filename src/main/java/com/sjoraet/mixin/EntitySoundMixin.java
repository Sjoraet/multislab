package com.sjoraet.mixin;

import com.sjoraet.block.MultiSlabBlock;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntitySoundMixin {
    @Shadow
    private World world;

    @Inject(at = @At("HEAD"), method = "playStepSound", cancellable = true)
    public void playStepSound(BlockPos pos, BlockState state, CallbackInfo callbackInfo) {
        if (state.getBlock() instanceof MultiSlabBlock) {

            BlockState blockState = this.world.getBlockState(pos.up());
            MultiSlabBlockEntity entity = (MultiSlabBlockEntity)this.world.getBlockEntity(pos);
            if (entity == null) {
                return;
            }

            BlockState topState = Block.getStateFromRawId(entity.getTopBlockId());
            BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup() : topState.getSoundGroup();
            ((Entity)(Object)this).playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
            callbackInfo.cancel();
        }
    }
}
