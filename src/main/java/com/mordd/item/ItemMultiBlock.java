package com.mordd.item;

import java.util.HashMap;
import java.util.Set;

import com.mordd.VoidDwellerCore;
import com.mordd.block.MultiBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMultiBlock extends ItemBlock {

	public ItemMultiBlock(Block block) {
		super(block);
		this.setHasSubtypes(true);
		
	}
	@Override
    public IIcon getIconFromDamage(int meta)
    {
		return MultiBlock.getIcon(meta);
    }

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String postFix = MultiBlock.registeredBlock.get(stack.getItemDamage());
		if(postFix != null) {
			return this.field_150939_a.getUnlocalizedName()+"."+postFix;
		}
		return this.field_150939_a.getUnlocalizedName()+"."+stack.getItemDamage();
	}
	public int getMetadata(int damage)
	{
		return 1;
	}

}
