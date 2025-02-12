package top.bearcabbage.annoyingeffects.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import static top.bearcabbage.annoyingeffects.effect.TanglingNightmareStatusEffect.ANNOYINGBAR_DISPLAY_PACKET;
import static top.bearcabbage.annoyingeffects.effect.TanglingNightmareStatusEffect.ANNOYINGBAR_STAGE_PACKET;

public record AnnoyingBarStagePayload(int stage) implements CustomPayload {

    public static final Id<AnnoyingBarStagePayload> ID = new Id<>(ANNOYINGBAR_STAGE_PACKET);
    public static final PacketCodec<PacketByteBuf, AnnoyingBarStagePayload> CODEC = PacketCodec.of((value, buf) -> buf.writeInt(value.stage()), buf -> new AnnoyingBarStagePayload(buf.readInt()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
