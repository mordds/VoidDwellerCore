package com.mordd.gui;

import java.util.List;

import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityAdvancedArcaneTable;
import com.mordd.util.TC_Recipes;
import com.mordd.util.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.common.lib.crafting.ArcaneSceptreRecipe;
import thaumcraft.common.lib.crafting.ArcaneWandRecipe;
import thaumcraft.common.tiles.TileMagicWorkbench;
import thaumicenergistics.common.fluids.GaseousEssentia;

public class ContainerAdvancedArcaneTable extends Container{
	public EntityPlayer player;
	public TileEntityAdvancedArcaneTable tile;
	public IArcaneRecipe recipe;
	public int visBar[] = new int[6];
	public int required[] = new int[6];
	public ContainerAdvancedArcaneTable(EntityPlayer player,TileEntityAdvancedArcaneTable tile) {
		this.player = player;
		this.tile = tile;
		this.tile.setHandler(this);
		for(int i = 0;i < 6;i++) visBar[i] = 0;
        for (int i = 0; i < 5; i++)
        {
        	for(int j = 0;j < 4;j++) {
        		this.addSlotToContainer(new Slot(tile, i * 4 + j, 12 + 18 * j, 26 + 18 * i));
        	}
        }
        for(int i = 0;i < 3;i++) {
        	for(int j = 0;j < 3;j++) {
        		this.addSlotToContainer(new Slot(tile, i * 3 + j + 20, 87 + 18 * j, 62 + 18 * i));
        	}
        }
        this.addSlotToContainer(new Slot(tile, 29, 174, 79) {
			 
        	@Override
	        public boolean isItemValid(ItemStack stack)
	        {
	            return false;
	        }
        	@Override
        	public boolean canTakeStack(EntityPlayer player) {
        		return ContainerAdvancedArcaneTable.this.canTakeStack();
        	}
			@Override
			 public void onSlotChanged() {
				 super.onSlotChanged();

				 ContainerAdvancedArcaneTable.this.onCraftMatrixChanged(this.inventory);
			 }
		});
        this.addSlotToContainer(new Slot(tile, 30, 210, 6) {
        	@Override
	        public boolean isItemValid(ItemStack stack)
	        {
	            return ContainerAdvancedArcaneTable.this.tile.isItemValidForSlot(30, stack);
	        }
        });
        for(int i = 0;i < 9;i++) {
        	this.addSlotToContainer(new Slot(player.inventory, i, 12 + 18 * i, 180));
        }
        for(int i = 0;i < 3;i++) {
        	for(int j = 0;j < 9;j++) {
        		this.addSlotToContainer(new Slot(player.inventory, i * 9 + j + 9, 12 + 18 * j, 123 + 18 * i));
        	}
        }
		this.onCraftMatrixChanged(this.tile);
		this.detectAndSendChanges();
	}
	

	
	@Override
    public void onCraftMatrixChanged(IInventory inv) {
		super.onCraftMatrixChanged(inv);
        InventoryCrafting ic = new InventoryCrafting(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer player) {
				return false;
			}}, 
        	3, 3);
        TileMagicWorkbench bench = new TileMagicWorkbench();
        for (int a = 0; a < 9; a++) {
            ic.setInventorySlotContents(a, this.tile.getStackInSlot(a+20));
            bench.setInventorySlotContentsSoftly(a, ic.getStackInSlot(a));
        }
         
        if(TC_Recipes.matchWithoutResearch(this.recipe, ic)) {
        	if(recipe != null) {
                if(recipe instanceof ArcaneWandRecipe || recipe instanceof ArcaneSceptreRecipe) {
        			inv.setInventorySlotContents(29, recipe.getCraftingResult(bench).copy());
                }
                else inv.setInventorySlotContents(29, recipe.getRecipeOutput().copy());
            }
        	else inv.setInventorySlotContents(29, null);
        	return;
        }
        
        recipe = TC_Recipes.findMatchedRecipe(ic);
        
        this.tile.recipe = recipe;

        if(recipe != null) {
            if(recipe instanceof ArcaneWandRecipe || recipe instanceof ArcaneSceptreRecipe) {
    			inv.setInventorySlotContents(29, recipe.getCraftingResult(bench));
            }
            else inv.setInventorySlotContents(29, recipe.getRecipeOutput().copy());
        }
        else inv.setInventorySlotContents(29, null);
    }
	public int getVisBar(int id) {
		return visBar[id];
	}
	public int getRequiredBar(int id) {
		return required[id];
	}
	
    @SuppressWarnings("unchecked")
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i = 0;i < 6;i++) {
        	this.visBar[i] = this.tile.getVis(Utils.primalAspects.get(i)) * 31 / (this.tile.getCapacity() * 100);
        	if(recipe != null) {
        		TileMagicWorkbench bench = new TileMagicWorkbench();
        		for(int j = 0;j < 9;j++) {
        			bench.setInventorySlotContentsSoftly(j, this.tile.getStackInSlot(20 + j));
        		}
        		if(recipe instanceof ArcaneWandRecipe || recipe instanceof ArcaneSceptreRecipe) {
        			this.tile.visRequired[i] = recipe.getAspects(bench).getAmount(Utils.primalAspects.get(i));
                }
        		else this.tile.visRequired[i] = recipe.getAspects().getAmount(Utils.primalAspects.get(i));
        		required[i] = tile.visRequired[i] * 31 / tile.getCapacity() > 31 ? 32 : tile.visRequired[i] * 31 / tile.getCapacity();
        	}
        }

        for (ICrafting i : (List<ICrafting>)this.crafters)
        {
        	 for(int j = 0;j < 6;j++) {
        		 i.sendProgressBarUpdate(this, j, this.visBar[j]);
        		 
        	 }
        	 for(int j = 0;j < 6;j++) {
        		 i.sendProgressBarUpdate(this, j + 6, this.tile.visRequired[j]);
        	 }
        	 for(int j = 0;j < 6;j++) {
        		 if(this.tile.aspectsFluid[j] != null) i.sendProgressBarUpdate(this, j + 12, this.tile.aspectsFluid[j].amount);
        	 }
        	 for(int j = 0;j < 6;j++) {
        		 i.sendProgressBarUpdate(this, j + 18, this.required[j]);
        	 }
        }
    }
    public boolean canTakeStack() {
    	
    	if(recipe != null) {
        	boolean ret = tile.hasEnoughAspect(recipe);
        	return ret;
    	}
    	return false;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);
        if(id < 6) {
        	this.visBar[id] = data; 
        }
        else if(id < 12) {
        	this.tile.visRequired[id - 6] = data;
        }
        else if(id < 18){
        	Fluid f = GaseousEssentia.getGasFromAspect(Utils.primalAspects.get(id - 12));
        	this.tile.aspectsFluid[id - 12] = new FluidStack(f,data);
        }
        else {
        	this.required[id - 18] = data;
        }
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
    	 return null;
    }
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
