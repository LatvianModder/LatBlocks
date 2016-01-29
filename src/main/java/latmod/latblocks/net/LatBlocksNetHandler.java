package latmod.latblocks.net;

import cpw.mods.fml.relauncher.Side;
import ftb.lib.api.net.LMNetworkWrapper;

public class LatBlocksNetHandler
{
	static final LMNetworkWrapper NET = LMNetworkWrapper.newWrapper("LatBlocks");
	
	public static void init()
	{
		NET.register(MessageDefaultPaint.class, 0, Side.SERVER);
		NET.register(MessageOpenDefPaintGui.class, 1, Side.SERVER);
	}
}