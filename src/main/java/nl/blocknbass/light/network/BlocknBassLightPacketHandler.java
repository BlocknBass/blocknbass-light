package nl.blocknbass.light.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import nl.blocknbass.core.BlocknBassPacketHandler;
import nl.blocknbass.core.proto.MessageProto;

public class BlocknBassLightPacketHandler implements BlocknBassPacketHandler {

    @Override
    public void handle(MessageProto.Message message, MinecraftClient minecraftClient) {
        minecraftClient.execute(() -> MinecraftClient.getInstance().player.addChatMessage(
                new LiteralText("Hello from light mod"), false)
        );
    }
}
