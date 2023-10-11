package com.mordd.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mordd.VoidDwellerCore;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.lib.crafting.ArcaneSceptreRecipe;
import thaumcraft.common.lib.crafting.ArcaneWandRecipe;
import thaumcraft.common.tiles.TileMagicWorkbench;


public class TC_Recipes {
	public static Method check;
	public static List<IArcaneRecipe> ArcaneRecipes = new ArrayList<IArcaneRecipe>();
	static {
		if(Utils.isTCLoaded()) {
			try {
				Class recipe = Class.forName("thaumcraft.api.crafting.ShapedArcaneRecipe");
				check = recipe.getDeclaredMethod("checkMatch", IInventory.class,int.class,int.class,boolean.class);
				check.setAccessible(true);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadRecipe() {

		for(Object r :ThaumcraftApi.getCraftingRecipes()) {
			if(r instanceof IArcaneRecipe) {
				ArcaneRecipes.add((IArcaneRecipe)r);
			}
		}	
	}
	public static IArcaneRecipe findMatchedRecipe(InventoryCrafting inv)  {
		for(IArcaneRecipe r : ArcaneRecipes) {
			if(matchWithoutResearch(r,inv)) return r;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static boolean matchWithoutResearch(IArcaneRecipe recipe,InventoryCrafting inventory)  {
		if(inventory.getSizeInventory() < 9) return false;
		TileMagicWorkbench bench = new TileMagicWorkbench();
		for(int i = 0;i < 9;i++) {
			bench.setInventorySlotContentsSoftly(i, inventory.getStackInSlot(i));
		}
		if(recipe instanceof ShapedArcaneRecipe) {
			ShapedArcaneRecipe shaped = (ShapedArcaneRecipe)recipe;
			for(int i = 0;i <= 3 - shaped.width;i++) {
				for(int j = 0;j <= 3 - shaped.height;j++) {
					
					boolean mirror = false,org = false;
					try {
						org = (Boolean) check.invoke(shaped, bench, i, j, false);
						mirror = (Boolean) check.invoke(shaped, bench, i, j, true);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return org || mirror;
				}
			}
		}
		else if(recipe instanceof ShapelessArcaneRecipe) {
			ShapelessArcaneRecipe shapeless = (ShapelessArcaneRecipe)recipe;
			ArrayList tList = new ArrayList(shapeless.getInput());
	        for (int x = 0; x < 9; x++) {
	            ItemStack slot = inventory.getStackInSlot(x);
	            if (slot != null) {
	                boolean inRecipe = false;
	                Iterator req = tList.iterator();
	                while (true) {
	                    if (!req.hasNext()) {
	                        break;
	                    }
	                    boolean match = false;
	                    Object next = req.next();
	                    if (next instanceof ItemStack) {
	                        match = Utils.isSameItem((ItemStack) next, slot);
	                    } else if (next instanceof ArrayList) {
	                        Iterator i$ = ((ArrayList) next).iterator();
	                        while (i$.hasNext()) {
	                            ItemStack item = (ItemStack) i$.next();
	                            match = match || Utils.isSameItem(item, slot);
	                        }
	                    }
	                    if (match) {
	                        inRecipe = true;
	                        tList.remove(next);
	                        break;
	                    }
	                }
	                if (!inRecipe) {
	                    return false;
	                }
	            }
	        }
			return tList.isEmpty();
		}
		else if(recipe instanceof ArcaneWandRecipe || recipe instanceof ArcaneSceptreRecipe) {
			if(recipe.getCraftingResult(bench) != null) return true;
		}
		return false;
	}
}
