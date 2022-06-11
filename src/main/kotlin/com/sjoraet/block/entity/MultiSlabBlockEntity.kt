package com.sjoraet.block.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.PillarBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos

class MultiSlabBlockEntity(position: BlockPos, state: BlockState): BlockEntity(Entities.MULTISLAB_BLOCK_ENTITY, position, state) {
    var bottomBlockId: Int = Block.getRawIdFromState(Blocks.STONE_SLAB.defaultState);
    var topBlockId: Int = Block.getRawIdFromState(Blocks.STONE_SLAB.defaultState);

    override fun writeNbt(nbt: NbtCompound?) {
        nbt?.putInt("bottomId", bottomBlockId);
        nbt?.putInt("topId", topBlockId);

        super.writeNbt(nbt);
    }

    override fun readNbt(nbt: NbtCompound?) {
        bottomBlockId = nbt?.getInt("bottomId") ?: Block.getRawIdFromState(Blocks.STONE_SLAB.defaultState);
        topBlockId = nbt?.getInt("topId") ?: Block.getRawIdFromState(Blocks.STONE_SLAB.defaultState);

        super.readNbt(nbt);
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        val tag = super.toInitialChunkDataNbt();
        this.writeNbt(tag);
        return tag;
    }
}