package com.mordd.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityFluidSource;
import com.mordd.tileentity.TileEntityMultiBlock;
import com.mordd.util.Utils;

import akka.event.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;


public class MultiBlock extends Block {
	public class BlockInfo{
		String s;
		float Hardness;
	}
	public static HashMap<Integer,String> registeredBlock = new HashMap<Integer,String>();

	public static HashMap<Integer,IIcon> icons = new HashMap<Integer,IIcon>(); 
	public static HashMap<Integer,Float> hardness = new HashMap<Integer,Float>(); 
	public static void registerMultiBlock(int meta,String name) {
		registeredBlock.put(meta, name);
	}
	public static void registerMultiBlock(int meta,String name,float hardness) {
		registeredBlock.put(meta, name);
	    setHardness(meta,hardness);
	}
	public static void setHardness(int meta,float Hardness) {
		if(hardness.containsKey(meta)) {
			hardness.remove(meta);
		}
		hardness.put(meta,Hardness);
	}
	public MultiBlock() {
		super(Material.ground);
		MinecraftForge.EVENT_BUS.register(this);
		this.setHardness(1.0f);
		this.setHarvestLevel("pickaxe", 0);
		
	}
	public int getActualMeta(World world,int x,int y,int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int meta = 0;
		if(tile instanceof TileEntityMultiBlock) {
			TileEntityMultiBlock tile2  = (TileEntityMultiBlock)tile;
			meta = tile2.meta;
		}
		return meta;
	}
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		int meta = this.getActualMeta(world, x, y, z);
		
		if(meta == 0) {
			Block block = world.getBlock(x, y - 1, z);
			ItemStack generate = null;
			if(Block.isEqualTo(block, this)) {
				int meta2 = this.getActualMeta(world, x, y - 1, z);
				if(Utils.rc_recipe.containsKey(meta2)) {
					generate = Utils.rc_recipe.get(meta2);
				}
			}
			else if(Block.isEqualTo(block, Blocks.stonebrick)) {
				generate = new ItemStack(Blocks.cobblestone,1);
			}
			if(generate != null && !world.isRemote) {
				EntityItem entityitem = new EntityItem(world, (double)x + 0.5d, (double)y + 1.2d, (double)z +0.5d, generate);
				double f3 = 0.05F;
				entityitem.motionX = world.rand.nextGaussian() * f3;
				entityitem.motionY = world.rand.nextGaussian() * f3;
				entityitem.motionZ = world.rand.nextGaussian() * f3;
				world.spawnEntityInWorld(entityitem);
			}
			return true;
		}
		return false;
	}

	public void getSubBlocks(Item item,CreativeTabs tab,List list) {
		Set<Integer> keys = registeredBlock.keySet();
		for(int meta : keys) {
			list.add(new ItemStack(this,1,meta));
		}
	}
	@Override
	public int getHarvestLevel(int meta) {
		return 0;
	}
	public String getHarvestTool(int meta) {
		return "pickaxe";
	} 
	@SubscribeEvent
	public void getHarvestSpeed(PlayerEvent.BreakSpeed event) {
		int meta = this.getActualMeta(event.entityPlayer.worldObj, event.x, event.y, event.z);
		if(!hardness.containsKey(meta)) return;
		float tHardness = hardness.get(meta);
		event.newSpeed = event.originalSpeed * 1.0f / tHardness;
	}
	
	@Override
	public void onBlockHarvested(World world,int x,int y,int z,int meta,EntityPlayer player) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		ItemStack stack = new ItemStack(this,1,this.getActualMeta(world, x, y, z));
		BlockEvent.HarvestDropsEvent event = new BlockEvent.HarvestDropsEvent(x, y, z, world, this, this.getActualMeta(world, x, y, z), EnchantmentHelper.getFortuneModifier(player), 1.0f, new ArrayList<ItemStack>(), player, EnchantmentHelper.getSilkTouchModifier(player));
		event.drops.add(stack);
		MinecraftForge.EVENT_BUS.post(event);
		for(ItemStack s : event.drops) {
	
			EntityItem entityitem = new EntityItem(world, (double)x + 0.5d, (double)y + 0.5d, (double)z +0.5d, s);
			double f3 = 0.05F;
			entityitem.motionX = world.rand.nextGaussian() * f3;
			entityitem.motionY = world.rand.nextGaussian() * f3;
			entityitem.motionZ = world.rand.nextGaussian() * f3;
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int metaOld) {
		
	}
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		return list;
	}
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntityMultiBlock) {
			TileEntityMultiBlock tile2  = (TileEntityMultiBlock)tile;
			return new ItemStack(this,1,tile2.meta);
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
		return "vd_core.multiblock";
	}
    @SideOnly(Side.CLIENT)
    public String getItemIconName()
    {
        return null;
    }
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		
		Set<Integer> keys = registeredBlock.keySet();
		for(int meta : keys) {
			IIcon icon = register.registerIcon("vd_core:"+registeredBlock.get(meta));
			icons.put(meta, icon);
		}
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
		int meta = stack.getItemDamage();
		TileEntity tile = this.createTileEntity(world, meta);
		world.setTileEntity(x, y, z, tile);
		
	}
	@Override
	public IIcon getIcon(IBlockAccess access,int x,int y,int z,int side) {

		TileEntity tile = access.getTileEntity(x, y, z);
		int meta = 0;
		if(tile instanceof TileEntityMultiBlock) {
			TileEntityMultiBlock tile2 = (TileEntityMultiBlock)tile;
			meta = tile2.meta;
		}
		return icons.get(meta);
	}
	@Override
	public IIcon getIcon(int side,int meta) {
		return icons.get(meta);
	}
	public static IIcon getIcon(int meta) {
		return icons.get(meta);
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	public TileEntity createTileEntity(World world,int meta) {
		return new TileEntityMultiBlock((short) meta);
	}
}
