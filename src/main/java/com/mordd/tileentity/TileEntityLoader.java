package com.mordd.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityLoader {
	public TileEntityLoader() {
		 GameRegistry.registerTileEntity(TileEntityFluidSource.class,"tile.mordd.watersource");
		 GameRegistry.registerTileEntity(TileEntityAdvancedArcaneTable.class,"tile.mordd.advanced_arcan_table");
		 GameRegistry.registerTileEntity(TileEntityMultiBlock.class,"tile.mordd.multiblock");
	}
}
