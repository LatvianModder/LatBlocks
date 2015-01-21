package latmod.latblocks.gui;
import latmod.core.FastList;
import latmod.core.gui.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQFurnace extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/qfurnace.png");
	
	public final TextureCoords
	texFuel = new TextureCoords(texLoc, 176, 0),
	texProgress = new TextureCoords(texLoc, 176, 14);
	
	public TileQFurnace furnace;
	public WidgetLM barFuel, barProgress;
	
	public GuiQFurnace(final ContainerQFurnace c)
	{
		super(c, texLoc);
		furnace = (TileQFurnace)c.inv;
		
		widgets.add(barFuel = new WidgetLM(this, 57, 36, 14, 14)
		{
			public void addMouseOverText(FastList<String> l)
			{
				double d = (furnace.fuel / 175D);
				d = ((int)(d * 10D)) / 10D;
				l.add(d + " items");
			}
		});
		widgets.add(barProgress = new WidgetLM(this, 80, 35, 22, 15)
		{
			public void addMouseOverText(FastList<String> l)
			{
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		if(furnace.fuel > 0) barFuel.render(texFuel);
		barProgress.render(texProgress, furnace.progress / 175D, 1D);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
}