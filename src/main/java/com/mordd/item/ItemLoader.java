package com.mordd.item;

import static gregapi.data.CS.U;
import static gregapi.data.TD.Prefix.BURNABLE;
import static gregapi.data.TD.Prefix.NEEDS_HANDLE;
import static gregapi.data.TD.Prefix.RECYCLABLE;
import static gregapi.data.TD.Prefix.SCANNABLE;
import static gregapi.data.TD.Prefix.TOOLTIP_ENCHANTS;
import static gregapi.data.TD.Prefix.TOOL_HEAD;
import static gregapi.data.TD.Prefix.UNIFICATABLE;
import static gregapi.oredict.OreDictMaterialCondition.typemin;

import com.mordd.util.Utils;

import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.item.prefixitem.PrefixItem;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;

public class ItemLoader {
	public static void loadItems() {
		OreDictMaterial[] m = new OreDictMaterial[] {};
		final gregapi.oredict.OreDictPrefix fluxCrystal = OreDictPrefix.createPrefix("fluxCrystal").setCategoryName("Fluid Crystal").setLocalPrefixName("Flux Crystal").setLocalItemName("", "Flux Crystal").setMaterialStats(2*U).setStacksize(64);
		PrefixItem crystal = new gregapi.item.prefixitem.PrefixItem("vd_core", "vd_core", "vd_core.item.fluxCrystal", fluxCrystal, Utils.fluxCrystalMaterials.toArray(m));
		MultiItem item = new MultiItem();
		loadMultiItem();
		GameRegistry.registerItem(item, "multiitem");
	}
	public static void loadMultiItem() {
		MultiItem.registerMultiItem(0,"clay_powder");
		MultiItem.registerMultiItem(1,"iron_bacteria");
		MultiItem.registerMultiItem(2,"carbon_mud");
		MultiItem.registerMultiItem(3,"fertilizer");
		MultiItem.registerMultiItem(4,"potassiumAmmoniumHydrogenPhosphate");
		MultiItem.registerMultiItem(5,"bone_in_bottle");
		MultiItem.registerMultiItem(6,"singleUseCrushHammer");
		MultiItem.registerMultiItem(7,"magnesiumOxide");
		MultiItem.registerMultiItem(8,"eternity_crystal");
		MultiItem.registerMultiItem(9,"potassiumHeptafluorotantalate_crystal");
		MultiItem.registerMultiItem(10,"aluminumchloride");
		MultiItem.registerMultiItem(11,"aluminiumHydride");
		MultiItem.registerMultiItem(12,"triethylaluminium");
		MultiItem.registerMultiItem(13,"triisopropylaluminium");
		MultiItem.registerMultiItem(14,"rawRubber");
		MultiItem.registerMultiItem(15,"sodiumBeryllate");
		MultiItem.registerMultiItem(16,"berylliumHydroxide");
		MultiItem.registerMultiItem(17,"emerald_mix");
		MultiItem.registerMultiItem(20,"crushed_flux_coagulum_limestone");
		MultiItem.registerMultiItem(21,"crushed_flux_coagulum_marble");
		MultiItem.registerMultiItem(22,"crushed_flux_coagulum_diorite");
		MultiItem.registerMultiItem(23,"crushed_flux_coagulum_kimberlite");
		MultiItem.registerMultiItem(24,"crushed_flux_coagulum_basalt");
		MultiItem.registerMultiItem(25,"crushed_flux_coagulum_komatiite");
		MultiItem.registerMultiItem(26,"crushed_flux_coagulum_quartzite");
		MultiItem.registerMultiItem(27,"crushed_flux_coagulum_andesite");
		MultiItem.registerMultiItem(28,"crushed_flux_coagulum_granite_red");
		MultiItem.registerMultiItem(29,"crushed_flux_coagulum_granite_black");
	}
}
