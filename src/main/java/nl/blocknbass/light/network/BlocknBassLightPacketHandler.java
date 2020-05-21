package nl.blocknbass.light.network;

import com.google.protobuf.InvalidProtocolBufferException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import nl.blocknbass.core.BlocknBassPacketHandler;
import nl.blocknbass.core.proto.MessageProto;
import nl.blocknbass.light.proto.LightMessageProto;

public class BlocknBassLightPacketHandler implements BlocknBassPacketHandler {

    @Override
    public void handle(MessageProto.Message message, MinecraftClient minecraftClient) {
        LightMessageProto.LightMessage lightMessage;
        try {
            lightMessage = message.getMessage().unpack(
                    LightMessageProto.LightMessage.class);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Couldn't decode light protobuf!");
            return;
        }

        minecraftClient.execute(() -> MinecraftClient.getInstance().player.addChatMessage(
                new LiteralText("Pan value: " + lightMessage.getPan()), false)
        );
    }
}
