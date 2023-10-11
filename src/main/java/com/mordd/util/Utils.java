package com.mordd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.Loader;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.CS.BlocksGT;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public class Utils {
	public static List<OreDictMaterial> fluxCrystalMaterials = new ArrayList<OreDictMaterial>(); 
	public static List<Aspect> primalAspects;
	public static Map<Integer,ItemStack> rc_recipe = new HashMap<Integer,ItemStack>();
	static{
		rc_recipe.put(201,OP.cobblestone.mat(MT.STONES.Marble,1));
		rc_recipe.put(202,OP.cobblestone.mat(MT.STONES.Limestone,1));
		rc_recipe.put(203,OP.cobblestone.mat(MT.STONES.Basalt,1));
		rc_recipe.put(204,OP.cobblestone.mat(MT.STONES.Kimberlite,1));
		rc_recipe.put(205,OP.cobblestone.mat(MT.STONES.Diorite,1));
		rc_recipe.put(206,OP.cobblestone.mat(MT.STONES.Komatiite,1));
		rc_recipe.put(207,OP.cobblestone.mat(MT.STONES.Quartzite,1));
		rc_recipe.put(210,OP.cobblestone.mat(MT.STONES.Andesite,1));
		rc_recipe.put(212,OP.cobblestone.mat(MT.STONES.GraniteBlack,1));
		rc_recipe.put(213,OP.cobblestone.mat(MT.STONES.GraniteRed,1));
		rc_recipe.put(208,new ItemStack(BlocksGT.RockOres,1,2));
		rc_recipe.put(209,new ItemStack(BlocksGT.RockOres,1,4));
		rc_recipe.put(211,new ItemStack(BlocksGT.RockOres,1,0));
		if(isTCLoaded()) primalAspects = Aspect.getPrimalAspects();
		fluxCrystalMaterials.add(MT.Empty);
	}
	public static boolean isTCLoaded() {
		return Loader.isModLoaded("Thaumcraft") && Loader.isModLoaded("thaumicenergistics");
	}
	public static boolean isSameItem(ItemStack stack,ItemStack stack2) {
		if(stack == null || stack2 == null) return false; 
		if(stack.getItem() != stack2.getItem()) return false;
		if(!stack.getHasSubtypes()) return true;
		if(stack.getItemDamage() == stack2.getItemDamage()) return true;
		return false;
	}
	public static boolean xor(boolean b1,boolean b2) {
		return !(b1 && b2) && (b1 || b2);
	}
	
}
