package latmod.latblocks.block.tank;
import java.text.DecimalFormat;

import cpw.mods.fml.relauncher.*;
import latmod.core.util.*;
import latmod.ftbu.inv.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.config.*;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.tank.TileTank;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.RecipeSorter;

public class BlockTank extends BlockTankBase
{
	public static ItemStack TANK_BASIC = new ItemStack(Blocks.glass, 1, 0);
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockTank(String s)
	{
		super(s);
		LatBlocks.mod.addTile(TileTank.class, s);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(6);
		TANK_BASIC = new ItemStack(this, 1, 0);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfigGeneral.tankCraftingHandler.get())
		{
			RecipeSorter.register("latblocks:tanks", TankCraftingHandler.class, RecipeSorter.Category.SHAPED, "before:minecraft:shaped");
			TankCraftingHandler.register(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0), new ItemStack(Items.iron_ingot));
			TankCraftingHandler.register(new ItemStack(this, 1, 2), new ItemStack(this, 1, 1), new ItemStack(Items.gold_ingot));
			TankCraftingHandler.register(new ItemStack(this, 1, 3), new ItemStack(this, 1, 2), new ItemStack(Items.quartz));
			TankCraftingHandler.register(new ItemStack(this, 1, 4), new ItemStack(this, 1, 3), new ItemStack(Items.diamond));
			
			if(LatBlocksConfigCrafting.endlessTank.get())
				TankCraftingHandler.register(new ItemStack(this, 1, 5), new ItemStack(this, 1, 4), ItemMaterialsLB.DUST_STAR.getStack());
		}
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 0), " S ", "SGS", " S ",
				'G', ODItems.GLASS_PANE_ANY,
				'S', ODItems.STICK);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 1), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 0),
				'I', ODItems.IRON);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 2), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 1),
				'I', ODItems.GOLD);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 3), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 2),
				'I', ODItems.QUARTZ);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 4), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 3),
				'I', ODItems.DIAMOND);
		
		if(LatBlocksConfigCrafting.endlessTank.get())
			mod.recipes.addRecipe(new ItemStack(this, 1, 5), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 4),
				'I', ItemMaterialsLB.DUST_STAR);
	}
	
	public int damageDropped(int i)
	{ return i; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileTank(m); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[6];
		for(int i = 0; i < icons.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "tank/outside_" + i);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("Fluid"))
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.stackTagCompound.getCompoundTag("Fluid"));
			l.add("Stored: " + fs.amount + "mB of " + fs.getLocalizedName());
		}
		
		int meta = is.getItemDamage();
		
		int cap = MathHelperLM.power(8, meta);
		
		DecimalFormat format = new DecimalFormat("###,###,###,###,###.###");
		l.add(format.format( ((meta == 5) ? 2000000000 : (cap * 1000)) ).replace(',', '\'') + " mB [ Mk" + (meta + 1) + " ]");
		
		if(GuiScreen.isShiftKeyDown())
		{
			l.add((cap * 4) + "x " + new ItemStack(Items.stick).getDisplayName());
			l.add((int)Math.ceil(cap * 6D / 16D) + "x " + new ItemStack(Blocks.glass).getDisplayName());
			
			if(cap >= 8) l.add((cap / 8) + "x " + new ItemStack(Items.iron_ingot).getDisplayName());
			if(cap >= 64) l.add((cap / 64) + "x " + new ItemStack(Items.gold_ingot).getDisplayName());
			if(cap >= 512) l.add((cap / 512) + "x " + new ItemStack(Items.quartz).getDisplayName());
			if(cap >= 4096) l.add((cap / 4096) + "x " + new ItemStack(Items.diamond).getDisplayName());
			if(meta == 5) l.add("1x " + ItemMaterialsLB.DUST_STAR.getStack().getDisplayName());
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return icons[item.getItemDamage()]; }
	
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{
		if(item.hasTagCompound() && item.stackTagCompound.hasKey("Fluid"))
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(item.stackTagCompound.getCompoundTag("Fluid"));
			
			int meta = item.getItemDamage();
			
			double c = 1000D;
			if(meta == 1) c = 8000D;
			else if(meta == 2) c = 64000D;
			else if(meta == 3) c = 512000D;
			else if(meta == 4) c = 4096000D;
			else if(meta == 5) c = fs.amount;
			
			fs.amount = (int)MathHelperLM.map(fs.amount, 0D, c, 0D, 1000D);
			return fs;
		}
		
		return null;
	}
}