package latmod.latblocks.item;
import cpw.mods.fml.relauncher.*;
import latmod.core.util.MathHelperLM;
import latmod.ftbu.inv.ODItems;
import latmod.ftbu.item.IItemLM;
import latmod.ftbu.mod.FTBU;
import latmod.ftbu.util.LatCoreMC;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.api.ILBGlasses;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class ItemGlasses extends ItemArmor implements IItemLM, ILBGlasses
{
	public static boolean hasClientPlayer()
	{ return hasPlayer(FTBU.proxy.getClientPlayer()); }
	
	public static boolean hasPlayer(EntityPlayer ep)
	{
		if(ep == null) return false;
		ItemStack is = ep.inventory.armorInventory[3];
		return is != null && is.getItem() instanceof ILBGlasses && ((ILBGlasses)is.getItem()).areLBGlassesActive(is, ep);
	}
	
	public static void spawnInvParticles(World w, double x, double y, double z, int q)
	{
		if(hasClientPlayer())
		{
			double s = 0.25D;
			for(int i = 0; i < q; i++)
			{
				double x1 = x + MathHelperLM.randomDouble(LatCoreMC.rand, -s, s);
				double y1 = y + MathHelperLM.randomDouble(LatCoreMC.rand, -s, s);
				double z1 = z + MathHelperLM.randomDouble(LatCoreMC.rand, -s, s);
				FTBU.proxy.spawnDust(w, x1, y1, z1, 0xC200FFFF);
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
	{ LatBlocks.mod.addItem(this); return this; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return LatBlocks.mod.getItemName(itemName); }
	
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.goggles.get())
			LatBlocks.mod.recipes.addRecipe(new ItemStack(this), "CCC", "LIL",
				'C', Items.leather,
				'L', ItemMaterialsLB.LENS,
				'I', ODItems.IRON);
	}
	
	public boolean isValidArmor(ItemStack is, int i, Entity e)
	{ return i == 0; }
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    { return LatBlocks.mod.assets + "textures/items/glasses_armor.png"; }
	
	public String getItemID()
	{ return itemName; }
	
	public void onPostLoaded()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		itemIcon = ir.registerIcon(LatBlocks.mod.assets + itemName);
	}
	
	public boolean areLBGlassesActive(ItemStack is, EntityPlayer ep)
	{ return true; }
}