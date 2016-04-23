package latmod.latblocks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.MathHelperMC;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQTerminal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockQTerminal extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_front;
	
	public BlockQTerminal(String s)
	{
		super(s, Material.rock);
		setHardness(1.2F);
		getMod().addTile(TileQTerminal.class, s);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileQTerminal(); }
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.qNetBlocks.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "QDQ", "QSQ", "QDQ", 'Q', Blocks.quartz_block, 'D', ODItems.DIAMOND, 'D', ItemMaterialsLB.DUST_GLOWIUM_D, 'S', ItemMaterialsLB.DUST_STAR);
		}
	}
	
	@Override
	public int damageDropped(int i)
	{ return 0; }
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return MathHelperMC.get3DRotation(w, mop.blockX, mop.blockY, mop.blockZ, ep); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":terminal_side");
		icon_front = ir.registerIcon(getMod().lowerCaseModID + ":terminal_front");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return (s == 3) ? icon_front : blockIcon; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == iba.getBlockMetadata(x, y, z)) return icon_front;
		return blockIcon;
	}
}