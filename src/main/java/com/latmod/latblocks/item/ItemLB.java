package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.item.ItemLM;
import com.feed_the_beast.ftbl.util.LMMod;
import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class ItemLB extends ItemLM
{
    public ItemLB()
    {
        setCreativeTab(LatBlocks.tab);
    }

    @Override
    public LMMod getMod()
    {
        return LatBlocks.mod;
    }
}