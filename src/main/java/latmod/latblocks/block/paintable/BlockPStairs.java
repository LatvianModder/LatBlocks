package latmod.latblocks.block.paintable;

import latmod.core.util.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.*;
import latmod.latblocks.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPStairs extends BlockPaintableSingle
{
	public BlockPStairs(String s)
	{
		super(s, 1F / 2F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePStairs(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 4), "P  ", "PP ", "PPP",
				'P', LatBlocksItems.b_paintable);
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 0.5D, 1D));
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0.5D, 0D, 1D, 1D, 0.5D));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		double hitY = mop.hitVec.yCoord - mop.blockY;
		
		if(!(mop.sideHit == Placement.D_DOWN || mop.sideHit == Placement.D_UP))
			hitY = 1D - hitY;
		
		int l = MathHelperLM.floor((double)(ep.rotationYaw * 8F / 360F) + 0.5D) & 7;
		return (hitY >= 0.5D) ? (8 + l) : l;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		if(m == -1) return;
		
		boolean isUp = (m / 8) == 0;
		
		double h = isUp ? 0D : 0.5D;
		double h1 = isUp ? 0.5D : 1D;
		
		int s = m % 8;
		boolean addS = s == 7 || s == 0 || s == 1;
		boolean addE = s == 1 || s == 2 || s == 3;
		boolean addN = s == 3 || s == 4 || s == 5;
		boolean addW = s == 5 || s == 6 || s == 7;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 1D - h1, 0D, 1D, 1D - h, 1D));
		
		boolean addNW = (addN || addE);
		boolean addNE = (addN || addW);
		boolean addSW = (addS || addE);
		boolean addSE = (addS || addW);
		
		boolean hasN = iba.getBlock(x, y, z - 1) == this;
		boolean hasW = iba.getBlock(x - 1, y, z) == this;
		boolean hasS = iba.getBlock(x, y, z + 1) == this;
		boolean hasE = iba.getBlock(x + 1, y, z) == this;
		
		if(s == 3 && hasN && hasW) addNE = addSW = false;
		if(s == 5 && hasN && hasE) addNW = addSE = false;
		if(s == 1 && hasS && hasW) addSE = addNW = false;
		if(s == 7 && hasS && hasE) addSW = addNE = false;
		
		if(addNW) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.0D, 0.5D, h1, 0.5D));
		if(addNE) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.0D, 1.0D, h1, 0.5D));
		if(addSW) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D));
		if(addSE) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.5D, 1.0D, h1, 1.0D));
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		if(m == -1) return;
		
		boolean isUp = (m / 8) == 0;
		
		double h = isUp ? 0D : 0.5D;
		double h1 = isUp ? 0.5D : 1D;
		
		int s = m % 8;
		boolean addS = s == 7 || s == 0 || s == 1;
		boolean addE = s == 1 || s == 2 || s == 3;
		boolean addN = s == 3 || s == 4 || s == 5;
		boolean addW = s == 5 || s == 6 || s == 7;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 1D - h1, 0D, 1D, 1D - h, 1D));
		
		boolean addNW = (addN || addE);
		boolean addNE = (addN || addW);
		boolean addSW = (addS || addE);
		boolean addSE = (addS || addW);
		
		boolean hasN = iba.getBlock(x, y, z - 1) == this;
		boolean hasW = iba.getBlock(x - 1, y, z) == this;
		boolean hasS = iba.getBlock(x, y, z + 1) == this;
		boolean hasE = iba.getBlock(x + 1, y, z) == this;
		
		if(s == 3 && hasN && hasW) addNE = addSW = false;
		if(s == 5 && hasN && hasE) addNW = addSE = false;
		if(s == 1 && hasS && hasW) addSE = addNW = false;
		if(s == 7 && hasS && hasE) addSW = addNE = false;
		
		if(addNW) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.0D, 0.5D, h1, 0.5D));
		if(addNE) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.0D, 1.0D, h1, 0.5D));
		if(addSW) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D));
		if(addSE) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.5D, 1.0D, h1, 1.0D));
	}
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	public static class TilePStairs extends TileSinglePaintable
	{
	}
}