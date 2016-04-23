package latmod.latblocks.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.IItemLM;
import ftb.lib.api.item.ODItems;
import ftb.lib.mod.FTBLibMod;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.api.ILBGlasses;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.lib.MathHelperLM;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGlasses extends ItemArmor implements IItemLM, ILBGlasses
{
	public static boolean hasClientPlayer()
	{ return hasPlayer(FTBLibMod.proxy.getClientPlayer()); }
	
	public static boolean hasPlayer(EntityPlayer ep)
	{
		if(ep == null) return false;
		ItemStack is = ep.inventory.armorInventory[3];
		return is != null && is.getItem() instanceof ILBGlasses && ((ILBGlasses) is.getItem()).areLBGlassesActive(is, ep);
	}
	
	public static void spawnInvParticles(World w, double x, double y, double z, int q)
	{
		if(hasClientPlayer())
		{
			double s = 0.25D;
			for(int i = 0; i < q; i++)
			{
				double x1 = x + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
				double y1 = y + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
				double z1 = z + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
				FTBLibMod.proxy.spawnDust(w, x1, y1, z1, 0xC200FFFF);
			}
		}
	}
	
	public final String itemName;
	
	public ItemGlasses(String s)
	{
		super(ArmorMaterial.GOLD, 4, 0);
		itemName = s;
	}
	
	public ItemGlasses register()
	{
		LatBlocks.mod.addItem(this);
		return this;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is)
	{ return LatBlocks.mod.getItemName(itemName); }
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.goggles.getAsBoolean())
			LatBlocks.mod.recipes.addRecipe(new ItemStack(this), "CCC", "LIL", 'C', Items.leather, 'L', ItemMaterialsLB.LENS, 'I', ODItems.IRON);
	}
	
	@Override
	public Item getItem()
	{ return this; }
	
	@Override
	public boolean isValidArmor(ItemStack is, int i, Entity e)
	{ return i == 0; }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{ return LatBlocks.mod.lowerCaseModID + ":textures/items/glasses_armor.png"; }
	
	@Override
	public String getItemID()
	{ return itemName; }
	
	@Override
	public void onPostLoaded()
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		itemIcon = ir.registerIcon(LatBlocks.mod.lowerCaseModID + ":" + itemName);
	}
	
	@Override
	public boolean areLBGlassesActive(ItemStack is, EntityPlayer ep)
	{ return true; }
}