package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.lib.item.ItemLM;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class ItemLB extends ItemLM
{
    @Override
    public CreativeTabs getCreativeTab()
    {
        return LatBlocks.INST.tab;
    }
}