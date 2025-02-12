package top.bearcabbage.annoyingeffects.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import static top.bearcabbage.annoyingeffects.effect.TanglingNightmareStatusEffect.ANNOYINGBAR_DISPLAY_PACKET;

public record AnnoyingBarDisplayPayload(boolean display) implements CustomPayload {

    public static final Id<AnnoyingBarDisplayPayload> ID = new CustomPayload.Id<>(ANNOYINGBAR_DISPLAY_PACKET);
    public static final PacketCodec<PacketByteBuf, AnnoyingBarDisplayPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBoolean(value.display()), buf -> new AnnoyingBarDisplayPayload(buf.readBoolean()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
