package latmod.latblocks.block;

import com.feed_the_beast.ftbl.util.FTBLib;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintableBlock extends BlockPaintable
{
    @Nonnull
    @Override
    public TilePaintable createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        return new TilePaintable.Sided();
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TilePaintable.Sided.class, new ResourceLocation(LatBlocks.MOD_ID, "paintable_sided"));
        FTBLib.addTile(TilePaintable.Single.class, new ResourceLocation(LatBlocks.MOD_ID, "paintable_single"));
    }
}