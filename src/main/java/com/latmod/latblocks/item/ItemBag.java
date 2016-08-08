package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.ForgeWorldMP;
import com.feed_the_beast.ftbl.api.client.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.LBGuiHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemBag extends ItemLB
{
    private static class BagProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        private final Bag bag = new Bag();

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == LBCapabilities.BAG || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == LBCapabilities.BAG)
            {
                return (T) bag;
            }
            else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            {
                return (T) bag.inv.get(bag.currentTab);
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) LBCapabilities.BAG_STORAGE.writeNBT(LBCapabilities.BAG, bag, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            LBCapabilities.BAG_STORAGE.readNBT(LBCapabilities.BAG, bag, null, nbt);
        }
    }

    public ItemBag()
    {
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new BagProvider();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(LatBlocks.MOD_ID, "bag"), "inventory"));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 10000000;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        return false;
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this, 1, 0),
                "DSD",
                "WCW",
                "WQW",
                'W', ODItems.WOOL,
                'S', ODItems.STRING,
                'C', ODItems.CHEST_WOOD,
                'D', ODItems.DIAMOND,
                'Q', ODItems.QUARTZ_BLOCK);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack is, World w, EntityPlayer ep, EnumHand hand)
    {
        if(!w.isRemote)
        {
            Bag bag = is.getCapability(LBCapabilities.BAG, null);

            if(bag.owner == null)
            {
                bag.owner = ep.getGameProfile().getId();
            }

            if(bag.privacyLevel.canInteract(ForgeWorldMP.inst.getPlayer(bag.owner), ForgeWorldMP.inst.getPlayer(ep)))
            {
                GuiHandler.openGui(LatBlocks.MOD_ID, ep, hand == EnumHand.MAIN_HAND ? LBGuiHandler.BAG_MAIN_HAND : LBGuiHandler.BAG_OFF_HAND, null);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, is);
    }
}