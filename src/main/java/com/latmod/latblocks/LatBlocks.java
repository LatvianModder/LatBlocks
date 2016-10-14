package com.latmod.latblocks;

import com.feed_the_beast.ftbl.lib.CreativeTabLM;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.item.LBItems;
import com.latmod.latblocks.net.LBNetHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * Created by LatvianModder on 12.05.2016.
 */
@Mod(
        modid = LatBlocks.MOD_ID,
        name = LatBlocks.MOD_ID,
        version = "0.0.0",
        useMetadata = true,
        dependencies = "required-after:ftbl"
)
public class LatBlocks
{
    public static final String MOD_ID = "latblocks";

    @Mod.Instance(MOD_ID)
    public static LatBlocks INST;

    @SidedProxy(serverSide = "com.latmod.latblocks.LatBlocksCommon", clientSide = "com.latmod.latblocks.client.LatBlocksClient")
    public static LatBlocksCommon PROXY;

    public CreativeTabLM tab;

    public static <K extends IForgeRegistryEntry<?>> void register(String id, K obj)
    {
        LMUtils.register(new ResourceLocation(MOD_ID, id), obj);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        tab = new CreativeTabLM("latblocks");
        LBCapabilities.enable();
        LBBlocks.init();
        LBItems.init();
        LBNetHandler.init();
        PROXY.preInit();
        MinecraftForge.EVENT_BUS.register(new LBEventHandler());
        tab.addIcon(new ItemStack(LBBlocks.NETHER_CHEST));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit();
    }
}
