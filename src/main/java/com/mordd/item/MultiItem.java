package com.mordd.item;

import java.util.HashMap;
import java.util.List;

import com.mordd.block.MultiBlock;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MultiItem extends Item {
	public MultiItem() {
		this.setHasSubtypes(true);

	}
	public static HashMap<Integer,String> registeredItem = new HashMap<Integer,String>();
	public static HashMap<Integer,IIcon> icons = new HashMap<Integer,IIcon>(); 
	public static void registerMultiItem(int meta,String name) {
		registeredItem.put(meta,name);
	}
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String postFix = registeredItem.get(stack.getItemDamage());
		if(postFix != null) {
			return "vd_core.multiitem"+"."+postFix;
		}
		return "vd_core.multiitem"+"."+stack.getItemDamage();
	}
	@Override
	public void getSubItems(Item item,CreativeTabs tabs,List list) {
		for(int meta : registeredItem.keySet()) {
			list.add(new ItemStack(this,1,meta));
		}
	}
	public IIcon getIconFromDamage(int meta) {
		return icons.get(meta);
	}
	public void registerIcons(IIconRegister register) {
		for(int meta : registeredItem.keySet()) {
			IIcon icon = register.registerIcon("vd_core:"+registeredItem.get(meta));
			icons.put(meta,icon);
		}
	}
	
}
