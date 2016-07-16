package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.util.MathHelperMC;
import com.latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintableSlab extends BlockPaintable
{
    public final AxisAlignedBB[] BOXES;

    public BlockPaintableSlab(double height)
    {
        setLightOpacity(255);
        setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.DOWN));
        translucent = true;
        BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(0D, 0D, 0D, 1D, height, 1D));
    }

    @Nonnull
    @Override
    public TilePaintable createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        return new TilePaintable.Single();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockDirectional.FACING).ordinal();
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOXES[state.getValue(BlockDirectional.FACING).ordinal()];
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, facing.getOpposite());
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState withRotation(@Nonnull IBlockState state, Rotation rot)
    {
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState withMirror(@Nonnull IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING)));
    }
}