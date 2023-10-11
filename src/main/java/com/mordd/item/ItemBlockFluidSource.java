package com.mordd.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.mordd.block.BlockFluidSource;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ItemBlockFluidSource extends ItemBlock {

	public ItemBlockFluidSource(Block block) {
		super(block);
		this.setHasSubtypes(true);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.field_150939_a.getUnlocalizedName()+"."+stack.getItemDamage();
	}
	public int getMetadata(int damage)
	{
		return 0;
	}
}
