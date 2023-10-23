package com.mordd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.CS.BlocksGT;
import gregapi.data.CS.ItemsGT;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public class Utils {
	public static List<OreDictMaterial> fluxCrystalMaterials = new ArrayList<OreDictMaterial>(); 
	public static List<Aspect> primalAspects;
	public static Map<Integer,ItemStack> rc_recipe = new HashMap<Integer,ItemStack>();
	static{
		
		
		rc_recipe.put(201,new ItemStack(BlocksGT.Marble,1,1));
		rc_recipe.put(202,new ItemStack(BlocksGT.Limestone,1,1));
		rc_recipe.put(203,new ItemStack(BlocksGT.Basalt,1,1));
		rc_recipe.put(204,new ItemStack(BlocksGT.Kimberlite,1,1));
		rc_recipe.put(205,new ItemStack(BlocksGT.Diorite,1,1));
		rc_recipe.put(206,new ItemStack(BlocksGT.Komatiite,1,1));
		rc_recipe.put(207,new ItemStack(BlocksGT.Quartzite,1,1));
		rc_recipe.put(210,new ItemStack(BlocksGT.Andesite,1,1));
		rc_recipe.put(212,new ItemStack(BlocksGT.GraniteRed,1,1));
		rc_recipe.put(213,new ItemStack(BlocksGT.GraniteBlack,1,1));
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
	public static void replaceMaterial(ItemStack stack,OreDictMaterial org,OreDictMaterial tar) {
		List<OreDictMaterialStack> temp = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		for(OreDictMaterialStack t : temp) {
			if(t.mMaterial == org) t.mMaterial = tar;
		}
	}
	public static void replaceMaterial(ItemStack stack,OreDictMaterial org,OreDictMaterial tar,long replaced) {
		List<OreDictMaterialStack> temp = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		boolean add = false;
		for(OreDictMaterialStack t : temp) {
			if(t.mMaterial == org) {
				if(replaced > t.mAmount) {
					t.mAmount = replaced;
					t.mMaterial = tar;
				}
				else {
					t.mAmount -= replaced;
					add = true;
				}
			} 
		}
		if(add) {
			OreDictItemData data = new OreDictItemData(new OreDictMaterialStack(tar,replaced),temp.toArray(new OreDictMaterialStack[temp.size()]));
			OreDictManager.INSTANCE.setItemData(stack,data);
		} 
	}
	public static void clearMaterial(ItemStack stack) {
		OreDictManager.INSTANCE.setItemData(stack,new OreDictItemData());
	}
	public static void addMaterial(ItemStack stack,OreDictMaterial mat,long cnt) {
		List<OreDictMaterialStack> materials = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		OreDictItemData data = new OreDictItemData(new OreDictMaterialStack(mat,cnt),materials.toArray(new OreDictMaterialStack[materials.size()]));
		OreDictManager.INSTANCE.setItemData(stack,data);
	} 
}
