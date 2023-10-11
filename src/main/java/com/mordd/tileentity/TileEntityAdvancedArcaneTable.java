package com.mordd.tileentity;


import java.util.List;

import com.mordd.VoidDwellerCore;
import com.mordd.gui.ContainerAdvancedArcaneTable;
import com.mordd.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ArcaneSceptreRecipe;
import thaumcraft.common.lib.crafting.ArcaneWandRecipe;
import thaumcraft.common.tiles.TileMagicWorkbench;

import thaumicenergistics.common.fluids.GaseousEssentia;


public class TileEntityAdvancedArcaneTable extends TileEntity implements IFluidHandler,IInventory{
	private int t = 0;
	private ItemStack contents[] = new ItemStack[31];
	private Container eventHandler;
	public IArcaneRecipe recipe;
	public FluidStack aspectsFluid[] = new FluidStack[6];
	public int visRequired[] = new int[6];
	public static final int FluidCapacity = 1280;
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList items = nbt.getTagList("Items", 10);
        this.contents = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = items.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.contents.length)
            {
                this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        NBTTagList fluidsNBT = nbt.getTagList("Fluids", 10);
        for(int i = 0;i < 6;i++) {
        	NBTTagCompound fluid = fluidsNBT.getCompoundTagAt(i);
        	this.aspectsFluid[i] = FluidStack.loadFluidStackFromNBT(fluid);
        }
    }
	
	public void setHandler(Container c) {
		this.eventHandler = c;
	}
	@Override
	public void updateEntity() {
		t++;
		if(contents[30] != null) {
			for(int i = 0;i < 6;i++) {
				if(t > 100) {
					//VoidDwellerCore.logger.fatal("try charge wand~");
					//t = 0
				} 
				chargeWand(Utils.primalAspects.get(i));
			}
		}

	}
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagList items = new NBTTagList();
        NBTTagList fluids = new NBTTagList();
        for (int i = 0; i < this.contents.length; ++i)
        {
            if (this.contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.contents[i].writeToNBT(nbttagcompound1);
                items.appendTag(nbttagcompound1);
            }
        }
        for(int i = 0;i < 6;i++) {
        	NBTTagCompound fluid = new NBTTagCompound();
        	if(this.aspectsFluid[i] != null)this.aspectsFluid[i].writeToNBT(fluid);
        	fluids.appendTag(fluid);
        }
        nbt.setTag("Items", items);
        nbt.setTag("Fluids",fluids);
        
    }
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(!(resource.getFluid() instanceof GaseousEssentia)) return 0;
		Aspect tAspect = ((GaseousEssentia)resource.getFluid()).getAspect();
		if(!Utils.primalAspects.contains(tAspect)) return 0;
		int index = Utils.primalAspects.indexOf(tAspect);
		if(aspectsFluid[index] != null) {
			if(aspectsFluid[index].amount == FluidCapacity) return 0;
			if(aspectsFluid[index].amount + resource.amount > FluidCapacity) {
				int ret = aspectsFluid[index].amount + resource.amount - FluidCapacity;
				aspectsFluid[index].amount = FluidCapacity;
				return ret;
			}
			else aspectsFluid[index].amount += resource.amount;
			return resource.amount;
			
		}else {
			if(doFill) {
				aspectsFluid[index] = resource.copy();
				if(aspectsFluid[index].amount > FluidCapacity) aspectsFluid[index].amount = FluidCapacity;
			}
			return resource.amount > FluidCapacity ? FluidCapacity : resource.amount;
		}
	}

	
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		FluidTankInfo ret[] = new FluidTankInfo[6];
		for(int i = 0;i < 6;i++) {
			ret[i] = new FluidTankInfo(aspectsFluid[i],FluidCapacity); 
		}
		return ret;
	}

	@Override
	public int getSizeInventory() {
		return 31;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return contents[index];
	}
	
	@Override
	public ItemStack decrStackSize(int id, int p_70298_2_) {

        if (this.contents[id] != null)
        {
        	if(id == 30) {
        		
        	}
        	if(id == 29) {
        		ItemStack ret = this.contents[id].copy();
        		for(int i = 20;i < 29;i++) {
        			if(this.contents[i] == null) continue;
        			this.contents[i].stackSize--;
        			if(this.contents[i].stackSize == 0) this.contents[i] = null;
        		}
        		for(int i = 0;i < 6;i++) {
        			this.consumeAspect(Utils.primalAspects.get(i), visRequired[i]);
        		}
        		this.eventHandler.onCraftMatrixChanged(this);
        		this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        		return ret;
        	}
            ItemStack itemstack;

            if (this.contents[id].stackSize <= p_70298_2_)
            {
                itemstack = this.contents[id];
                this.contents[id] = null;
                this.markDirty();
                if(id >= 20) this.eventHandler.onCraftMatrixChanged(this);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return itemstack;
            }
            else
            {
                itemstack = this.contents[id].splitStack(p_70298_2_);

                if (this.contents[id].stackSize == 0)
                {
                    this.contents[id] = null;
                }

                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                if(id >= 20) this.eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}
	

	@Override
    public void setInventorySlotContents(int id, ItemStack stack)
    {

	        this.contents[id] = stack;

	        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        {
	        	stack.stackSize = this.getInventoryStackLimit();
	        }

	        if(id >= 20 && id < 29) this.eventHandler.onCraftMatrixChanged(this);
	        this.markDirty();
		

    }

	@Override
	public String getInventoryName() {
		return "mordd.advancedarcanetable";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		
		if(index == 29) return false;
        if (index == 30 && stack != null && (stack.getItem() instanceof ItemWandCasting)) {
        	return true;
        }
		return false;
	}
	public boolean hasEnoughAspect(IArcaneRecipe recipe) {
		if(recipe != null) {
    		TileMagicWorkbench bench = new TileMagicWorkbench();
    		for(int j = 0;j < 9;j++) {
    			bench.setInventorySlotContentsSoftly(j, this.getStackInSlot(20 + j));
    		}
    		
			for(int i = 0;i < 6;i++) {
				int required = 0; 
				if(recipe instanceof ArcaneWandRecipe || recipe instanceof ArcaneSceptreRecipe) {
	    			required = recipe.getAspects(bench).getAmount(Utils.primalAspects.get(i));
	            }
				else required = recipe.getAspects().getAmount(Utils.primalAspects.get(i));
				
				if(required != 0) {
					if(!hasEnoughAspect(Utils.primalAspects.get(i),required)) return false;
				}
			}
			return true;
		}
		return true;
	}
	public int getVisConsumeRatio() {
		if(contents[30] == null) return 100;
		ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
		return (int)(wand.getConsumptionModifier(contents[30], ((ContainerAdvancedArcaneTable)(this.eventHandler)).player, Aspect.ORDER, true) * 100);
		
	}
	public boolean hasEnoughAspect(Aspect aspect,int amount) {
		return getVis(aspect) >= amount * getVisConsumeRatio();
	}
	public void consumeAspect(Aspect aspect,int amount) {
		if(!Utils.primalAspects.contains(aspect)) return;
		if(amount == 0) return;
		int realAmount = amount * getVisConsumeRatio();
		int index = Utils.primalAspects.indexOf(aspect);
		if(this.aspectsFluid[index] != null) {
			if(realAmount * 128 / 100 > this.aspectsFluid[index].amount) {
				int consumed = this.aspectsFluid[index].amount / 128 * 100;
				this.aspectsFluid[index].amount = 0;
				ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
				wand.consumeVis(contents[30], ((ContainerAdvancedArcaneTable)(this.eventHandler)).player, aspect, (amount * 100 - consumed), false);
				
			}
			else {
				this.aspectsFluid[index].amount -= realAmount * 128 / 100;
			}
		}
		else {
			ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
			wand.consumeVis(contents[30], ((ContainerAdvancedArcaneTable)(this.eventHandler)).player, aspect, realAmount, false);
		}
	}
	public void chargeWand(Aspect aspect) {
		int index = Utils.primalAspects.indexOf(aspect);
		if(this.aspectsFluid[index] == null) return;
		if(this.aspectsFluid[index].amount < 128) return;
		if(contents[30] == null) return;
		ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
		int t = wand.addVis(contents[30], aspect, 1, true);
		if(this.t > 100) {
			VoidDwellerCore.logger.fatal(t);
			this.t = 0;
		}
		this.aspectsFluid[index].amount -= t == 0 ? 128 : 0;
	}
	public int getCapacity() {
		if(contents[30] != null) {
			ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
			int wandCapacity = wand.getMaxVis(contents[30]);
			return 10 + wandCapacity / 100;
		}
		return 10;
	}
	public int getVis(Aspect aspect) {
		if(!Utils.primalAspects.contains(aspect)) return -1;
		int index = Utils.primalAspects.indexOf(aspect);
		if(contents[30] == null) return aspectsFluid[index] == null ? 0 : aspectsFluid[index].amount / 128 * 100; 
		else {
			ItemWandCasting wand = (ItemWandCasting) contents[30].getItem();
			int wandVis = wand.getVis(contents[30], aspect);
			int cache = aspectsFluid[index] == null ? 0 : aspectsFluid[index].amount / 128 * 100;
			return wandVis + cache;
		}
	}
}
