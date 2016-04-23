package latmod.latblocks.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.FTBLib;
import ftb.lib.api.item.LMInvUtils;
import ftb.lib.api.tile.IGuiTile;
import ftb.lib.api.tile.TileInvLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.ContainerQFurnace;
import latmod.latblocks.gui.GuiQFurnace;
import latmod.lib.MathHelperLM;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileQFurnace extends TileInvLM implements IGuiTile, ISidedInventory//FIXME:, IQuartzNetTile // TileEntityFurnace
{
	public static final String ITEM_TAG = "Furnace";
	public static final int MAX_ITEMS = 3;
	public static final double MAX_PROGRESS = 200D;
	
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;
	
	public int fuel = 0;
	public int progress = 0;
	public ItemStack result = null;
	
	public TileQFurnace()
	{ super(MAX_ITEMS); }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		fuel = tag.getInteger("Fuel");
		progress = tag.getShort("Progress");
		result = LMInvUtils.loadStack(tag, "Result");
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("Fuel", fuel);
		tag.setShort("Progress", (short) progress);
		LMInvUtils.saveStack(tag, "Result", result);
	}
	
	@Override
	public boolean rerenderBlock()
	{ return true; }
	
	@Override
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer() && security.canInteract(ep) && LMInvUtils.isWrench(is))
		{
			if(ep.isSneaking())
			{
				dropItems = false;
				ItemStack drop = new ItemStack(LatBlocksItems.b_qfurnace, 1, 0);
				
				if(fuel > 0 || result != null || hasCustomInventoryName() || LMInvUtils.getFirstFilledIndex(this, null, -1) != -1)
				{
					NBTTagCompound tag = new NBTTagCompound();
					writeTileData(tag);
					tag.removeTag("CustomName");
					drop.setTagCompound(new NBTTagCompound());
					drop.stackTagCompound.setTag(ITEM_TAG, tag);
					if(hasCustomInventoryName()) drop.setStackDisplayName(getName());
				}
				
				LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, drop, 10);
				
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
			else
			{
				if(side == 0 || side == 1)
				{
					blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
					if(blockMetadata == 2) blockMetadata = 5;
					else if(blockMetadata == 3) blockMetadata = 4;
					else if(blockMetadata == 4) blockMetadata = 2;
					else if(blockMetadata == 5) blockMetadata = 3;
				}
				else blockMetadata = side;
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, side, 3);
				markDirty();
			}
		}
		else if(isServer()) FTBLib.openGui(ep, this, null);
		return true;
	}
	
	@Override
	public void onUpdate()
	{
		if(fuel == 0 && isServer() && items[SLOT_FUEL] != null)
		{
			fuel = TileEntityFurnace.getItemBurnTime(items[SLOT_FUEL]);
			
			if(fuel > 0)
			{
				items[SLOT_FUEL] = LMInvUtils.reduceItem(items[SLOT_FUEL]);
				markDirty();
			}
		}
		
		if(progress == 0)
		{
			if(isServer())
			{
				ItemStack out = (items[SLOT_INPUT] == null) ? null : FurnaceRecipes.smelting().getSmeltingResult(items[SLOT_INPUT]);
				
				if(out != null && fuel > 0)
				{
					items[SLOT_INPUT] = LMInvUtils.reduceItem(items[SLOT_INPUT]);
					result = out.copy();
					progress = 1;
					markDirty();
				}
			}
		}
		else
		{
			if(progress >= MAX_PROGRESS)
			{
				if(result != null)
				{
					progress = (int) MAX_PROGRESS;
					markDirty();
					
					if(items[SLOT_OUTPUT] == null || (items[SLOT_OUTPUT].stackSize + result.stackSize <= items[SLOT_OUTPUT].getMaxStackSize()))
					{
						if(isServer())
						{
							if(items[SLOT_OUTPUT] == null) items[SLOT_OUTPUT] = result.copy();
							else items[SLOT_OUTPUT].stackSize += result.stackSize;
							
							result = null;
						}
						
						progress = 0;
						
						ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[blockMetadata];
						double px = xCoord + 0.5D + fd.offsetX * 0.6D;
						double pz = zCoord + 0.5D + fd.offsetZ * 0.6D;
						
						for(int i = 0; i < 40; i++)
						{
							double r = MathHelperLM.randomDouble(MathHelperLM.rand, -0.25D, 0.25D);
							double px1 = px + ((fd == ForgeDirection.WEST || fd == ForgeDirection.EAST) ? 0D : r);
							double py = yCoord + MathHelperLM.rand.nextFloat() * 6.0D / 16.0D;
							double pz1 = pz + ((fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) ? 0D : r);
							
							worldObj.spawnParticle("flame", px1, py, pz1, 0D, 0D, 0D);
						}
					}
				}
			}
			else
			{
				if(fuel > 0)
				{
					progress++;
					fuel--;
					
					if(fuel == 0 && isServer()) markDirty();
					
					if(progress % 3 == 0)
					{
						ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[blockMetadata];
						double r = MathHelperLM.randomDouble(MathHelperLM.rand, -0.25D, 0.25D);
						double px1 = xCoord + 0.5D + fd.offsetX * 0.6D + ((fd == ForgeDirection.WEST || fd == ForgeDirection.EAST) ? 0D : r);
						double py = yCoord + MathHelperLM.rand.nextFloat() * 6.0D / 16.0D;
						double pz1 = zCoord + 0.5D + fd.offsetZ * 0.6D + ((fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) ? 0D : r);
						
						worldObj.spawnParticle("flame", px1, py, pz1, 0D, 0D, 0D);
					}
				}
			}
		}
	}
	
	@Override
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(ITEM_TAG))
			readTileData(is.stackTagCompound.getCompoundTag(ITEM_TAG));
		
		if(is.hasDisplayName()) setName(is.getDisplayName());
		else setName("");
		
		markDirty();
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
	
	@Override
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerQFurnace(ep, this); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiQFurnace(new ContainerQFurnace(ep, this)); }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{
		if(i == SLOT_FUEL) return TileEntityFurnace.getItemBurnTime(is) > 0;
		else if(i == SLOT_OUTPUT) return false;
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{
		if(s == 0) return new int[] {SLOT_FUEL};
		if(s == 1) return new int[] {SLOT_INPUT};
		return new int[] {SLOT_OUTPUT};
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return true; }
	
	@Override
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return true; }
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
	
	public ItemStack getQIconItem()
	{ return new ItemStack(LatBlocksItems.b_qfurnace, 1, isLit() ? 100 : 0); }
	
	public void onQClicked(EntityPlayer ep, int button)
	{ if(isServer()) FTBLib.openGui(ep, this, null); }
}