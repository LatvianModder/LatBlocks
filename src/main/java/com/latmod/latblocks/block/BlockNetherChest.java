package com.latmod.latblocks.block;

import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 13.07.2016.
 */
public class BlockNetherChest extends BlockLB
{
    public BlockNetherChest()
    {
        super(Material.ROCK);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileNetherChest();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            playerIn.addChatMessage(new TextComponentString("NetherChest GUI is disabled for now!"));

            /*
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileNetherChest)
            {
                te.markDirty();
                FTBLibIntegration.API.openGui(ContainerNetherChest.ID, (EntityPlayerMP) playerIn, pos, null);
            }
            */
        }

        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if(hasTileEntity(state) && placer instanceof EntityPlayer)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileNetherChest)
            {
                if(stack.hasTagCompound() && stack.getTagCompound().hasKey(TileNetherChest.ITEM_NBT_KEY))
                {
                    ((TileNetherChest) te).readTileData(stack.getTagCompound().getCompoundTag(TileNetherChest.ITEM_NBT_KEY));
                    te.markDirty();
                }
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        if(te instanceof TileNetherChest)
        {
            ItemStack itemstack = new ItemStack(this, 1, 0);

            itemstack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = new NBTTagCompound();
            ((TileNetherChest) te).writeTileData(tag);
            itemstack.getTagCompound().setTag(TileNetherChest.ITEM_NBT_KEY, tag);

            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, null, stack);
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<>();
        ItemStack itemstack = new ItemStack(this, 1, 0);

        TileEntity te = world.getTileEntity(pos);

        if(te instanceof TileNetherChest)
        {
            itemstack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = new NBTTagCompound();
            ((TileNetherChest) te).writeTileData(tag);
            itemstack.getTagCompound().setTag(TileNetherChest.ITEM_NBT_KEY, tag);
        }

        ret.add(itemstack);
        return ret;
    }
}