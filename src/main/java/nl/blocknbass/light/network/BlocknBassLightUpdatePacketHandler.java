package nl.blocknbass.light.network;

import com.google.protobuf.InvalidProtocolBufferException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3d;
import nl.blocknbass.core.BlocknBassPacketHandler;
import nl.blocknbass.core.proto.MessageProto;
import nl.blocknbass.light.Ayra506Fixture;
import nl.blocknbass.light.BlocknBassLight;
import nl.blocknbass.light.proto.LightMessageProto;
import nl.blocknbass.light.proto.LightMessageProto.FixtureMessage;

import java.util.ArrayList;
import java.util.List;

public class BlocknBassLightUpdatePacketHandler implements BlocknBassPacketHandler {

    private void ensureSize(ArrayList<?> list, int size) {
        list.ensureCapacity(size);
        while (list.size() < size)
            list.add(null);
    }

    @Override
    public void handle(MessageProto.Message message, MinecraftClient minecraftClient) {
        LightMessageProto.LightsUpdateMessage lightMessage;
        try {
            lightMessage = message.getMessage().unpack(
                    LightMessageProto.LightsUpdateMessage.class);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Couldn't decode light protobuf!");
            return;
        }

        int type = lightMessage.getType();
        switch (type) {
            case LightMessageProto.LightsUpdateType.SET_LIGHTS_VALUE: {
                List<FixtureMessage> list = lightMessage.getLightsList();
                for (FixtureMessage light : list) {
                    Ayra506Fixture fixture = new Ayra506Fixture();
                    fixture.position = new Vector3d(light.getX(), light.getY(), light.getZ());
                    minecraftClient.execute(() -> {
                        ensureSize(BlocknBassLight.INSTANCE.lights, light.getId() + 1);
                        BlocknBassLight.INSTANCE.lights.set(light.getId(), fixture);
                    });
                }
                break;
            }
            case LightMessageProto.LightsUpdateType.ADD_LIGHT_VALUE: {
                List<FixtureMessage> list = lightMessage.getLightsList();
                FixtureMessage light = list.get(0);
                Ayra506Fixture fixture = new Ayra506Fixture();
                fixture.position = new Vector3d(light.getX(), light.getY(), light.getZ());
                minecraftClient.execute(() -> {
                    ensureSize(BlocknBassLight.INSTANCE.lights, light.getId() + 1);
                    BlocknBassLight.INSTANCE.lights.set(light.getId(), fixture);
                });
                break;
            }
            case LightMessageProto.LightsUpdateType.REMOVE_LIGH_VALUE: {
                List<FixtureMessage> list = lightMessage.getLightsList();
                FixtureMessage light = list.get(0);
                minecraftClient.execute(() -> {
                    if (light.getId() >= BlocknBassLight.INSTANCE.lights.size()) {
                        System.err.println("removing an out of bounds light");
                        return;
                    }
                    BlocknBassLight.INSTANCE.lights.remove(light.getId());
                });
                break;
            }
        }
    }
}
