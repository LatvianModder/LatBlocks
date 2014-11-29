package latmod.latblocks.tile;
import latmod.core.tile.TileLM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileVoidTank extends TileLM implements IFluidHandler
{
	public FluidTank tank;
	
	public TileVoidTank()
	{
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	public void onUpdate()
	{
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		return false;
	}
	
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{ return (resource == null) ? 0 : resource.amount; }
	
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{ return null; }
	
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{ return null; }
	
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{ return true; }
	
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{ return false; }
	
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{ return new FluidTankInfo[] { tank.getInfo() }; }
}