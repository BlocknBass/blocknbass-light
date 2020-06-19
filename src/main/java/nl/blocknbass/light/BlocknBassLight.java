package nl.blocknbass.light;

import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class BlocknBassLight implements ModInitializer {

    public static BlocknBassLight INSTANCE;
    public ArrayList<Ayra506Fixture> lights;

    @Override
    public void onInitialize() {
        lights = new ArrayList<>();

        INSTANCE = this;
        System.out.print("Initializing Block & Bass Light!");
    }
}
