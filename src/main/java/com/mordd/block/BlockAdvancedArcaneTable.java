package com.mordd.block;

import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityAdvancedArcaneTable;
import com.mordd.util.TC_Recipes;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAdvancedArcaneTable extends Block{
	IIcon icons[] = new IIcon[6];
	public BlockAdvancedArcaneTable() {
		super(Material.wood);
		this.setHardness(4.0f);
	}
	@Override
	public String getUnlocalizedName() {
		return "vd_core.adv_arcane_table";
	}
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		
		this.icons[0] = register.registerIcon("vd_core:adv_arcane_table_bottom");
		this.icons[1] = register.registerIcon("vd_core:adv_arcane_table_top");
		this.icons[2] = register.registerIcon("vd_core:adv_arcane_table_side");
		for(int i = 3;i < 6;i++) {
			this.icons[i] = icons[2];
		}
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	@Override
	public IIcon getIcon(int id, int meta)
	{
		return this.icons[id];
	}
	@Override
	public TileEntity createTileEntity(World world,int metadata) {
		return new TileEntityAdvancedArcaneTable();
	}
	
	@Override
	public void onBlockPreDestroy(World world,int x,int y,int z,int metaData) {
		super.onBlockPreDestroy(world, x, y, z ,metaData);
		TileEntityAdvancedArcaneTable tile = (TileEntityAdvancedArcaneTable) world.getTileEntity(x, y, z);
		if(!world.isRemote && tile != null) {
			for(int i = 0;i < 28;i++) {
				ItemStack stack = tile.getStackInSlot(i);
				if(stack != null) {
					EntityItem entityitem = new EntityItem(world, (double)x + 0.5d, (double)y + 0.5d, (double)z +0.5d, stack);
					double f3 = 0.05F;
					entityitem.motionX = world.rand.nextGaussian() * f3;
					entityitem.motionY = world.rand.nextGaussian() * f3;
					entityitem.motionZ = world.rand.nextGaussian() * f3;
					world.spawnEntityInWorld(entityitem);
				}
				
				
			}
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TC_Recipes.loadRecipe();
		if (world.isRemote) player.openGui(VoidDwellerCore.instance, 1, world, x, y, z);
		return true;
	}

}
