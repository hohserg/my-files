package com.lg.realism.dryer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class CumpfireGui extends GuiContainer {

	private TileEntityCumpfire tile;
	 private static final ResourceLocation TEXTURE = new ResourceLocation("realism:textures/gui/cumpfire.png");
	
	public CumpfireGui(InventoryPlayer playerInventory, TileEntityCumpfire tile) {
		super(new CumpfireContainer(playerInventory, tile));
		this.tile = tile;
	}
	@Override
	public void drawGuiContainerBackgroundLayer(float parTick, int mouseX, int mouseY) {
	       /**
	        *   Располагаем gui по центру.
	        */
	       mc.renderEngine.bindTexture(TEXTURE);
	       int x = (this.width - this.xSize) / 2;
	       int y = (this.height - this.ySize) / 2;
	       drawTexturedModalRect(x, y + 1, 0, 0, xSize, ySize);

	
	   }
	}


