package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.gui.GuiHandler;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.item.ItemBag;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBGuiHandler extends GuiHandler
{
    public static final GuiHandler INSTANCE = new LBGuiHandler();

    public static final int BAG_MAIN_HAND = 0;
    public static final int BAG_OFF_HAND = 1;
    public static final int NETHER_CHEST = 2;

    private LBGuiHandler()
    {
        super(LatBlocks.MOD_ID);
    }

    @Override
    public Container getContainer(@Nonnull EntityPlayer ep, int id, @Nullable NBTTagCompound data)
    {
        switch(id)
        {
            case BAG_MAIN_HAND:
            {
                ItemStack is = ep.getHeldItem(EnumHand.MAIN_HAND);

                if(is != null && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null));
                }

                break;
            }
            case BAG_OFF_HAND:
            {
                ItemStack is = ep.getHeldItem(EnumHand.OFF_HAND);

                if(is != null && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null));
                }

                break;
            }
            case NETHER_CHEST:
            {
                TileEntity te = getTileEntity(ep.worldObj, data);

                if(te instanceof TileNetherChest)
                {
                    return new ContainerNetherChest(ep, (TileNetherChest) te);
                }

                break;
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(@Nonnull EntityPlayer ep, int id, @Nullable NBTTagCompound data)
    {
        switch(id)
        {
            case BAG_MAIN_HAND:
            {
                ItemStack is = ep.getHeldItem(EnumHand.MAIN_HAND);

                if(is != null && is.getItem() instanceof ItemBag && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new GuiBag(new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null))).getWrapper();
                }

                break;
            }
            case BAG_OFF_HAND:
            {
                ItemStack is = ep.getHeldItem(EnumHand.OFF_HAND);

                if(is != null && is.getItem() instanceof ItemBag && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new GuiBag(new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null))).getWrapper();
                }

                break;
            }
            case NETHER_CHEST:
            {
                TileEntity te = getTileEntity(ep.worldObj, data);

                if(te instanceof TileNetherChest)
                {
                    return new GuiNetherChest(new ContainerNetherChest(ep, (TileNetherChest) te)).getWrapper();
                }

                break;
            }
        }

        return null;
    }
}
