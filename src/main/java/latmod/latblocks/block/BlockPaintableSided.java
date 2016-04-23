package latmod.latblocks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import latmod.latblocks.block.paintable.BlockPaintableDef;
import latmod.latblocks.client.render.world.RenderPaintable;
import latmod.latblocks.tile.TilePaintableLB;
import latmod.latblocks.tile.TileSidedPaintable;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockPaintableSided extends BlockPaintableLB
{
	public BlockPaintableSided(String s)
	{ super(s); }
	
	@Override
	public int getLightOpacity()
	{ return 0; }
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new BlockPaintableDef.TilePaintableDef(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderPaintable.instance.getRenderId(); }
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return true; }
	
	@Override
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{ return true; }
	
	@Override
	public float getExplosionResistance(Entity e, World w, int x, int y, int z, double ex, double ey, double ez)
	{
		float f = getExplosionResistance(e);
		
		TileSidedPaintable t = (TileSidedPaintable) getTile(w, x, y, z);
		
		if(t != null)
		{
			for(int i = 0; i < t.paint.length; i++)
			{
				if(t.paint[i] != null)
				{
					float f1 = t.paint[i].block.getExplosionResistance(e);
					if(f1 > f) f = f1;
				}
			}
		}
		
		return f;
	}
}