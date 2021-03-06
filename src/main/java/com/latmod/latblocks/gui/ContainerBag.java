package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerBag extends Container
{
    public static final ResourceLocation BAG_MAIN_HAND = new ResourceLocation(LatBlocks.MOD_ID, "bag_main");
    public static final ResourceLocation BAG_OFF_HAND = new ResourceLocation(LatBlocks.MOD_ID, "bag_off");

    public EntityPlayer player;
    public final EnumHand hand;
    public Bag bag;
    private int lastTab = -1;
    private int lastPrivacyLevel = -1;
    private byte lastColor = 0;

    public ContainerBag(EntityPlayer ep, EnumHand h)
    {
        player = ep;
        hand = h;
        refreshBag();

        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new SlotItemHandler(null, x + y * 9, 7 + x * 18, 29 + y * 18)
                {
                    @Override
                    public IItemHandler getItemHandler()
                    {
                        return bag.inv.get(bag.currentTab);
                    }
                });
            }
        }

        LMInvUtils.addPlayerSlots(this, player, 7, 128, true);
    }

    @Override
    public boolean canInteractWith(EntityPlayer ep)
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return LMInvUtils.transferStackInSlot(this, index, Bag.BagItemStackHandler.SIZE);
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack)
    {
        if(slotID == 0)
        {
            refreshBag();
        }

        super.putStackInSlot(slotID, stack);
    }

    public void refreshBag()
    {
        bag = player.getHeldItem(hand).getCapability(LBCapabilities.BAG, null);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        int newTab = bag.currentTab;
        int newPrivacyLevel = bag.privacyLevel.ordinal();
        byte newColor = bag.colorID;

        for(IContainerListener l : listeners)
        {
            if(lastTab != newTab)
            {
                l.sendProgressBarUpdate(this, 0, newTab);
            }

            if(lastPrivacyLevel != newPrivacyLevel)
            {
                l.sendProgressBarUpdate(this, 1, newPrivacyLevel);
            }

            if(lastColor != newColor)
            {
                l.sendProgressBarUpdate(this, 2, newColor);
            }
        }

        lastTab = newTab;
        lastPrivacyLevel = newPrivacyLevel;
        lastColor = newColor;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        refreshBag();

        switch(id)
        {
            case 0:
                bag.currentTab = (byte) data;
                break;
            case 1:
                bag.privacyLevel = EnumPrivacyLevel.VALUES[data];
                break;
            case 2:
                bag.colorID = (byte) data;
                break;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer ep, int id)
    {
        if(!ep.worldObj.isRemote)
        {
            id = id & 0xFF;
            if(id == 210 || id == 211)
            {
                int level;

                if(id == 210)
                {
                    level = (bag.privacyLevel.ordinal() + 1) % EnumPrivacyLevel.VALUES.length;
                }
                else
                {
                    level = bag.privacyLevel.ordinal() - 1;
                    if(level < 0)
                    {
                        level = EnumPrivacyLevel.VALUES.length - 1;
                    }
                }

                bag.privacyLevel = EnumPrivacyLevel.VALUES[level];
            }
            else if(id >= 200 && id < 200 + bag.inv.size())
            {
                bag.currentTab = (byte) (id - 200);
            }

            return true;
        }

        return false;
    }
}