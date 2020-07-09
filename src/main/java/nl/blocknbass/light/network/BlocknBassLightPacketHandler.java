package nl.blocknbass.light.network;

import com.google.protobuf.InvalidProtocolBufferException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import nl.blocknbass.core.BlocknBassPacketHandler;
import nl.blocknbass.core.proto.MessageProto;
import nl.blocknbass.light.Ayra506Fixture;
import nl.blocknbass.light.BlocknBassLight;
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

        float pan = ((float)540/255) * lightMessage.getPan() - 270;
        float tilt = ((float)180/255) * lightMessage.getTilt() - 90;
        int alpha = lightMessage.getDimmer();
        /*minecraftClient.execute(() -> MinecraftClient.getInstance().player.addChatMessage(
                new LiteralText("Tilt value: " + lightMessage.getTilt()), false)
        );*/
        minecraftClient.execute(() -> {
            Ayra506Fixture light = BlocknBassLight.INSTANCE.lights.get(lightMessage.getId());
            light.targetPan = pan;
            light.targetTilt = tilt;
            light.r = lightMessage.getRed();
            light.g = lightMessage.getGreen();
            light.b = lightMessage.getBlue();
            light.a = alpha;
            light.assign_time = minecraftClient.world.getTime();
        });
    }
}
