package latmod.latblocks.block.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.*;
import latmod.latblocks.tile.blocks.TilePSlab;

public class BlockPSlab extends BlockPaintableSingle
{
	public BlockPSlab(String s)
	{
		super(s, Material.rock, 1F / 2F);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this, 6), "PPP",
				'P', ODItems.PAINTABLE_BLOCK);
		
		mod.recipes().addShapelessRecipe(new ItemStack(this, 2), LatBlocksItems.b_paintable, ODItems.TOOL_SAW);
		mod.recipes().addShapelessRecipe(new ItemStack(LatBlocksItems.b_paintable), this, this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePSlab(); }
}