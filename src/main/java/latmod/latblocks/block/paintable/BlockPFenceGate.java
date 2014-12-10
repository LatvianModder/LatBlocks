package latmod.latblocks.block.paintable;

import java.util.List;

import latmod.core.tile.TileLM;
import latmod.core.util.*;
import latmod.core.util.MathHelper;
import latmod.latblocks.*;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.paintable.TilePFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPFenceGate extends BlockPaintableSingle
{
	public BlockPFenceGate(String s)
	{
		super(s, Material.wood, 1F);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePFence(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "PSP", "PSP",
				'P', LatBlocksItems.b_paintable,
				'S', LatBlocksItems.b_cover);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		int m = w.getBlockMetadata(x, y, z);
		
		if(m > 1) return;
		
		double p = 1D / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(m % 2 == 0) { x0 = -d; x1 = 1D + d; }
		else { z0 = -d; z1 = 1D + d; }
		
		double h = 1.5D;
		if(LatBlocksConfig.General.fencesIgnorePlayers && e instanceof EntityPlayer) h = 1D;
		AxisAlignedBB bb1 = AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, h, z1).getOffsetBoundingBox(x, y, z);
		if(bb1 != null && bb.intersectsWith(bb1)) l.add(bb1);
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		int m0 = w.getBlockMetadata(x, y, z);
		w.setBlockMetadataWithNotify(x, y, z, (m0 + 2) % 4, 3);
		
		for(int i = -2; i <= 2; i++)
		{
			if(i != 0)
			{
				int m = w.getBlockMetadata(x, y + i, z);
				if(m == m0) w.setBlockMetadataWithNotify(x, y + i, z, (m + 2) % 4, 3);
			}
		}
		
		w.playAuxSFXAtEntity(ep, 1003, x, y, z, 0);
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double p = 1D / 16D * 3D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, pn, p, 1D, pp));
		boxes.add(AxisAlignedBB.getBoundingBox(1D - p, 0D, pn, 1D, 1D, pp));
		
		double hn = 1D / 8D * 1D;
		double hp = 1D / 8D * 7D;
		
		double dd = 1D / 8D;
		
		double x0 = p;
		double z0 = 0.5D - dd / 2D;
		double x1 = 1D - p;
		double z1 = 0.5D + dd / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, hn, z0, x1, hp, z1));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return (MathHelper.floor(ep.rotationYaw * 4D / 360D + 0.5D) & 3) % 2; }
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1D / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(m % 2 == 0) { x0 = -d; x1 = 1D + d; }
		else { z0 = -d; z1 = 1D + d; }
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, 1D, z1));
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1D / 16D * 3D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		if(m % 2 == 0)
		{
			boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, pn, p, 1D, pp));
			boxes.add(AxisAlignedBB.getBoundingBox(1D - p, 0D, pn, 1D, 1D, pp));
		}
		else
		{
			boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, 0D, pp, 1D, p));
			boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, 1D - p, pp, 1D, 1D));
		}
		
		if(m <= 1)
		{
			double hn = 1D / 8D * 1D;
			double hp = 1D / 8D * 7D;
			
			if(iba.getBlock(x, y - 1, z) == this) hn = 0D;
			if(iba.getBlock(x, y + 1, z) == this) hp = 1D;
			
			double dd = 1D / 8D;
			
			double x0 = p;
			double z0 = 0.5D - dd / 2D;
			double x1 = 1D - p;
			double z1 = 0.5D + dd / 2D;
			
			if(m == 1)
			{
				z0 = p;
				x0 = 0.5D - dd / 2D;
				z1 = 1D - p;
				x1 = 0.5D + dd / 2D;
			}
			
			boxes.add(AxisAlignedBB.getBoundingBox(x0, hn, z0, x1, hp, z1));
		}
	}
}