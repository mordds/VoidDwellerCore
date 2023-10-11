package com.mordd.gui;

import cpw.mods.fml.relauncher.SideOnly;

import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityAdvancedArcaneTable;
import com.mordd.util.Utils;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiContainerAdvancedArcaneTable extends GuiContainer {
	private ContainerAdvancedArcaneTable container;
    private static final String TEXTURE_PATH = VoidDwellerCore.MOD_ID + ":" + "textures/gui/container/advanced_arcane_table.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    
	public GuiContainerAdvancedArcaneTable(ContainerAdvancedArcaneTable container) {
		super(container);
		this.xSize = 232;
        this.ySize = 208;
        this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		
		 this.mc.getTextureManager().bindTexture(TEXTURE);
	     int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
	     this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
	    
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146976_1_, int p_146976_2_) {
		this.mc.getTextureManager().bindTexture(TEXTURE);
		for(int i = 0;i < 6;i++) {
		     int fullHeight = this.container.getVisBar(i); 
		     int height = this.container.getRequiredBar(i);
		     int diff = Math.abs(fullHeight - height);
		     if(fullHeight > height) {
		    	 this.drawTexturedModalRect(100 + 15 * i, 41 - fullHeight, 10 * i + 60, 243 - fullHeight, 10, height);
		    	 this.drawTexturedModalRect(100 + 15 * i, 41 - diff, 10 * i, 243 - diff, 10, diff);
		     }
		     else {
		    	 height = height > 31 ? 31 : height;
		    	 this.drawTexturedModalRect(100 + 15 * i, 41 - height, 10 * i + 60, 243 - height, 10, height);
		     }
		     
	     }
	     
	     for(int i = 0;i < 6;i++) {
	    	 double amount =this.container.tile.getVis(Utils.primalAspects.get(i)) / 100;
		     int color = Utils.primalAspects.get(i).getColor();
		     if(i == 5) color = 0x5F5F5F;
		     String str = Double.toString(amount);
		     str = str.length() > 4 ? str.substring(0,3) : str.substring(0, str.length() - 1);
		     this.fontRendererObj.drawString(str,106 + 15 * i - this.fontRendererObj.getStringWidth(str) / 2,46,color);
	     }
	     this.fontRendererObj.drawString("Cur", 86, 46, 0xFFFFFF);
	}
	

}
