package nl.blocknbass.light;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.util.math.Vector3d;
import nl.blocknbass.core.IBlocknBassMod;

import java.util.ArrayList;

public class BlocknBassLight implements ModInitializer {

    public static BlocknBassLight INSTANCE;
    public ArrayList<Ayra506Fixture> lights;

    @Override
    public void onInitialize() {
        lights = new ArrayList<>();
        Ayra506Fixture fixture = new Ayra506Fixture();
        fixture.position = new Vector3d(-140, 6, 131);
        lights.add(fixture);

        INSTANCE = this;
        System.out.print("Initializing Block & Bass Light!");
    }
}
