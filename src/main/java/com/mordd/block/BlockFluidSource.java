package com.mordd.block;

import java.util.ArrayList;
import java.util.List;

import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityFluidSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.FL;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockFluidSource extends Block {
	public static List<Fluid> allowedFluid = new ArrayList<Fluid>();
	static {
		allowedFluid.add(FluidRegistry.WATER);
		allowedFluid.add(FL.DistW.fluid());
	}
	public BlockFluidSource() {
		super(Material.ground);
		this.setHardness(1.0f);
	}
	@Override 
	public void onBlockPreDestroy(World world, int x, int y, int z, int metaOld) {
		TileEntity tile = world.getTileEntity(x, y, z);
		ItemStack stack;
		if(tile instanceof TileEntityFluidSource) {
			TileEntityFluidSource source  = (TileEntityFluidSource)tile;
			int meta = allowedFluid.indexOf(source.getFluid());
			stack = new ItemStack(this,1,meta >= 0 ? meta : 0);
		}
		else {
			 stack = new ItemStack(this,1);
		}
		EntityItem entityitem = new EntityItem(world, (double)x + 0.5d, (double)y + 0.5d, (double)z +0.5d, stack);
		double f3 = 0.05F;
		entityitem.motionX = world.rand.nextGaussian() * f3;
		entityitem.motionY = world.rand.nextGaussian() * f3;
		entityitem.motionZ = world.rand.nextGaussian() * f3;
		world.spawnEntityInWorld(entityitem);
	}
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		return list;
	}
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntityFluidSource) {
			TileEntityFluidSource source  = (TileEntityFluidSource)tile;
			int meta = allowedFluid.indexOf(source.getFluid());
			return new ItemStack(this,1,meta >= 0 ? meta : 0);
		}
		else {
			 return new ItemStack(this,1);
		}
	}
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return getPickBlock(target,world,x,y,z);
	}
	@Override
	public String getUnlocalizedName() {
		return "vd_core.fluidsource";
	}
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		
		this.blockIcon = register.registerIcon("vd_core" + ":watersource");

	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
		int meta = stack.getItemDamage();
		TileEntity tile = this.createTileEntity(world, meta);
		if(tile instanceof TileEntityFluidSource) {
			TileEntityFluidSource source  = (TileEntityFluidSource)tile;
			source.setFluid(allowedFluid.get(meta >= allowedFluid.size() ? 0 : meta));
		}
		world.setTileEntity(x, y, z, tile);
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
    	for(int i = 0;i < allowedFluid.size();i++) {
    		list.add(new ItemStack(this,1,i));
    	}
    }
	@Override
	public IIcon getIcon(int id, int meta)
	{
		return this.blockIcon;
	}
	@Override
	public TileEntity createTileEntity(World world,int metadata) {

	
		return new TileEntityFluidSource();
	}
	
	
	
}
