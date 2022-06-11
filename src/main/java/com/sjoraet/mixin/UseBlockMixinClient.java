package com.sjoraet.mixin;

import com.sjoraet.util.UseBlockMixin;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class UseBlockMixinClient {
    @Shadow
    private ClientPlayNetworkHandler networkHandler;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;", ordinal = 0), method = "interactBlock", cancellable = true)
    public void interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<ActionResult> info) {
        System.out.println("FOO");

        ActionResult result = UseBlockMixin.Companion.handle(player, world, hand, blockHitResult);

        if (result != ActionResult.PASS) {
            if (result == ActionResult.SUCCESS) {
                this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, blockHitResult));
            }

            info.setReturnValue(result);
        }
    }
}
