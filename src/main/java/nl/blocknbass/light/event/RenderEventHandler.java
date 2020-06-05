package nl.blocknbass.light.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import nl.blocknbass.light.Ayra506Fixture;
import nl.blocknbass.light.BlocknBassLight;

public class RenderEventHandler {
    private static RenderEventHandler INSTANCE;

    public static RenderEventHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RenderEventHandler();
        return INSTANCE;
    }

    public void onRenderWorld(MatrixStack matrices, MinecraftClient client, float tickDelta) {
        for (Ayra506Fixture light : BlocknBassLight.INSTANCE.lights)
            light.render(matrices, client, tickDelta);
    }
}
