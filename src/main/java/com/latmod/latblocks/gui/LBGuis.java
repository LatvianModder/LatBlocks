package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by LatvianModder on 11.07.2016.
 */
@ParametersAreNonnullByDefault
public class LBGuis
{
    public static final ResourceLocation BAG_MAIN_HAND = new ResourceLocation(LatBlocks.MOD_ID, "bag_main");
    public static final ResourceLocation BAG_OFF_HAND = new ResourceLocation(LatBlocks.MOD_ID, "bag_off");
    public static final ResourceLocation NETHER_CHEST = new ResourceLocation(LatBlocks.MOD_ID, "nether_chest");
    public static final ResourceLocation CRAFTING_PANEL = new ResourceLocation(LatBlocks.MOD_ID, "crafting_panel");

    public static void init()
    {
        FTBLibAPI.get().getRegistries().guis().register(BAG_MAIN_HAND, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
                return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new ContainerBag(player, EnumHand.MAIN_HAND) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
                return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new GuiBag(new ContainerBag(player, EnumHand.MAIN_HAND)).getWrapper() : null;
            }
        });

        FTBLibAPI.get().getRegistries().guis().register(BAG_OFF_HAND, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                ItemStack is = player.getHeldItem(EnumHand.OFF_HAND);
                return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new ContainerBag(player, EnumHand.OFF_HAND) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                ItemStack is = player.getHeldItem(EnumHand.OFF_HAND);
                return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new GuiBag(new ContainerBag(player, EnumHand.OFF_HAND)).getWrapper() : null;
            }
        });

        FTBLibAPI.get().getRegistries().guis().register(NETHER_CHEST, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = player.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileNetherChest) ? new ContainerNetherChest(player, (TileNetherChest) te) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = player.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileNetherChest) ? new GuiNetherChest(new ContainerNetherChest(player, (TileNetherChest) te)).getWrapper() : null;
            }
        });

        FTBLibAPI.get().getRegistries().guis().register(CRAFTING_PANEL, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = player.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileCraftingPanel) ? new ContainerCraftingPanel(player, (TileCraftingPanel) te) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = player.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileCraftingPanel) ? new GuiCraftingPanel(new ContainerCraftingPanel(player, (TileCraftingPanel) te)).getWrapper() : null;
            }
        });
    }
}