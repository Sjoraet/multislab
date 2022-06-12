package com.sjoraet.mixin;

import com.sjoraet.block.MultiSlabBlock;
import com.sjoraet.block.entity.MultiSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.entity.BlockEntity;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public class EntitySoundMixin {
    @Shadow
    private World world;

    @ModifyVariable(method = "playStepSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "HEAD"), ordinal = 0)
    public BlockState playStepSound(BlockState state, BlockPos pos) {
        if (state.getBlock() instanceof MultiSlabBlock) {
            BlockEntity entity = this.world.getBlockEntity(pos);
            if (entity != null && entity instanceof MultiSlabBlockEntity slabEntity) {
                return Block.getStateFromRawId(slabEntity.getTopBlockId());
            }
        }

        return state;
    }
}
