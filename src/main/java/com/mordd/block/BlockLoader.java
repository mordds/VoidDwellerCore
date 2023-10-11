package com.mordd.block;



import com.mordd.item.ItemBlockFluidSource;
import com.mordd.item.ItemMultiBlock;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;


public class BlockLoader {
	public static BlockFluidSource WaterSource;
	public static BlockAdvancedArcaneTable Adv_ArcaneTable;
	public static MultiBlock multiBlock;
	public static void registerBlocks()
	{
		registerMultiBlocks();
		WaterSource = new BlockFluidSource();
		GameRegistry.registerBlock(WaterSource,ItemBlockFluidSource.class,"watersource");
		multiBlock = new MultiBlock();
		GameRegistry.registerBlock(multiBlock, ItemMultiBlock.class,"multiblock");
		
		if(Loader.isModLoaded("Thaumcraft") && Loader.isModLoaded("thaumicenergistics")) {
			Adv_ArcaneTable = new BlockAdvancedArcaneTable();
			GameRegistry.registerBlock(Adv_ArcaneTable,"adv_arcane_table");
		}

		//Only Test Use!
		//GameRegistry.registerItem(new DebugItem(),"debug");
	}
	private static void registerMultiBlocks() {
		MultiBlock.registerMultiBlock(0,"stone_maker");
		MultiBlock.registerMultiBlock(1, "compressed_wood");
		MultiBlock.registerMultiBlock(2, "rocky_wood");
		
		MultiBlock.registerMultiBlock(101, "dark_marble");
		MultiBlock.registerMultiBlock(102, "dark_limestone");
		MultiBlock.registerMultiBlock(103, "dark_basalt");
		MultiBlock.registerMultiBlock(104, "dark_kimberlite");
		MultiBlock.registerMultiBlock(105, "dark_diorite");
		MultiBlock.registerMultiBlock(106, "dark_komatiite");
		
		MultiBlock.registerMultiBlock(121, "reduced_flux_mixture_marble",0.2f);
		MultiBlock.registerMultiBlock(122, "reduced_flux_mixture_limestone",0.2f);
		MultiBlock.registerMultiBlock(123, "reduced_flux_mixture_basalt",0.2f);
		MultiBlock.registerMultiBlock(124, "reduced_flux_mixture_kimberlite",0.2f);
		MultiBlock.registerMultiBlock(125, "reduced_flux_mixture_diorite",0.2f);
		MultiBlock.registerMultiBlock(126, "reduced_flux_mixture_komatiite",0.2f);

		MultiBlock.registerMultiBlock(142, "oxidized_flux_mixture_limestone",0.2f);
		MultiBlock.registerMultiBlock(144, "oxidized_flux_mixture_kimberlite",0.2f);
		MultiBlock.registerMultiBlock(145, "oxidized_flux_mixture_diorite",0.2f);
		MultiBlock.registerMultiBlock(146, "oxidized_flux_mixture_komatiite",0.2f);
		
		MultiBlock.registerMultiBlock(164, "compressed_flux_mixture_kimberlite");
		
		MultiBlock.registerMultiBlock(184, "hot_flux_mixture_kimberlite",0.5f);
		
		MultiBlock.registerMultiBlock(201, "marble_frame");
		MultiBlock.registerMultiBlock(202, "limestone_frame");
		MultiBlock.registerMultiBlock(203, "basalt_frame");
		MultiBlock.registerMultiBlock(204, "kimberlite_frame");
		MultiBlock.registerMultiBlock(205, "diorite_frame");
		MultiBlock.registerMultiBlock(206, "komatiite_frame");
		MultiBlock.registerMultiBlock(207, "quartzite_frame");
		MultiBlock.registerMultiBlock(208, "salt_frame");
		MultiBlock.registerMultiBlock(209, "bauxite_frame");
		MultiBlock.registerMultiBlock(210, "andesite_frame");
		MultiBlock.registerMultiBlock(211, "coal_frame");
		MultiBlock.registerMultiBlock(212, "granite_red_frame");
		MultiBlock.registerMultiBlock(213, "granite_black_frame");
		
		MultiBlock.registerMultiBlock(221, "flux_coagulum_marble");
		MultiBlock.registerMultiBlock(222, "flux_coagulum_limestone");
		MultiBlock.registerMultiBlock(223, "flux_coagulum_basalt");
		MultiBlock.registerMultiBlock(224, "flux_coagulum_kimberlite");
		MultiBlock.registerMultiBlock(225, "flux_coagulum_diorite");
		MultiBlock.registerMultiBlock(226, "flux_coagulum_komatiite");
		MultiBlock.registerMultiBlock(227, "flux_coagulum_quartzite");
		MultiBlock.registerMultiBlock(230, "flux_coagulum_andesite");
		MultiBlock.registerMultiBlock(232, "flux_coagulum_granite_red");
		MultiBlock.registerMultiBlock(233, "flux_coagulum_granite_black");
	}
}
