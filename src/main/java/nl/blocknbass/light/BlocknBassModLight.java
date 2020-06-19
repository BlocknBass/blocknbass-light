package nl.blocknbass.light;

import nl.blocknbass.core.BlocknBassPacketDispatcher;
import nl.blocknbass.core.IBlocknBassMod;
import nl.blocknbass.light.network.BlocknBassLightPacketHandler;
import nl.blocknbass.light.network.BlocknBassLightUpdatePacketHandler;

public class BlocknBassModLight implements IBlocknBassMod {

    @Override
    public void registerPacketSet(BlocknBassPacketDispatcher dispatch) {
        dispatch.register("light", new BlocknBassLightPacketHandler());
        dispatch.register("light_update", new BlocknBassLightUpdatePacketHandler());
    }
}