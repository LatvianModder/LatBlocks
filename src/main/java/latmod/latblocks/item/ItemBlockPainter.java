package latmod.latblocks.item;

import latmod.latblocks.api.IPainterItem;
import latmod.latblocks.api.PaintItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockPainter extends ItemLB implements IPainterItem
{
	public ItemBlockPainter(String s)
	{
		super(s);
		setMaxStackSize(1);
		setMaxDamage(128);
		setFull3D();
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addShapelessRecipe(new ItemStack(this), ItemMaterialsLB.PAINT_ROLLER_ROD, ItemMaterialsLB.PAINT_ROLLER);
	}
	
	@Override
	public ItemStack getPaintItem(ItemStack is)
	{ return PaintItemHelper.getPaintItem(is); }
	
	@Override
	public boolean canPaintBlock(ItemStack is)
	{ return is.getItemDamage() <= getMaxDamage(); }
	
	@Override
	public void damagePainter(ItemStack is, EntityPlayer ep)
	{ is.damageItem(1, ep); }
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{ return PaintItemHelper.onItemRightClick(this, is, w, ep); }
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{ return PaintItemHelper.onItemUse(this, is, ep, w, x, y, z, s, x1, y1, z1); }
}