package latmod.latblocks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.MathHelperMC;
import ftb.lib.api.client.FTBLibClient;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQChest;
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

import java.util.List;

public class BlockQChest extends BlockLB
{
	public static final TileQChest tempTile = new TileQChest();
	
	public BlockQChest(String s)
	{
		super(s, Material.wood);
		float f = 1F / 16F;
		setBlockBounds(f, 0F, f, 1F - f, 0.875F, 1F - f);
		getMod().addTile(TileQChest.class, s);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileQChest(); }
	
	@Override
	public int getRenderType()
	{ return -1; }
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.chest.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QSQ", 'Q', Blocks.quartz_block, 'F', Blocks.chest, 'D', ODItems.DIAMOND, 'S', ItemMaterialsLB.DUST_GLOWIUM_D);
		}
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return MathHelperMC.get2DRotation(ep); }
	
	@Override
	public int damageDropped(int i)
	{ return 0; }
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return Blocks.quartz_block.getIcon(s, 0); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return FTBLibClient.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, List<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQChest.ITEM_TAG))
		{
			tempTile.readTileData(is.stackTagCompound.getCompoundTag(TileQChest.ITEM_TAG));
			
			if(is.hasDisplayName()) l.add(new ItemStack(is.getItem()).getDisplayName());
			
			int slotsUsed = 0;
			int items = 0;
			
			for(int i = 0; i < tempTile.items.length; i++)
			{
				if(tempTile.items[i] != null)
				{
					slotsUsed++;
					items += tempTile.items[i].stackSize;
				}
			}
			
			l.add("Slots Used: " + slotsUsed + " / " + tempTile.items.length);
			l.add("Items in chest: " + items);
			l.add("Title: " + tempTile.getName());
			l.add("Chest Color: " + tempTile.colorChest);
			l.add("Text Color: " + tempTile.colorText);
		}
	}
}