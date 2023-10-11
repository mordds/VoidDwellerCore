package com.mordd.tileentity;

import static gregapi.data.CS.F;
import static gregapi.data.CS.NI;
import static gregapi.data.CS.SIDE_BOTTOM;
import static gregapi.data.CS.T;
import static gregapi.data.CS.TOOL_LOCALISER_PREFIX;
import static gregapi.data.CS.ZL_IS;

import java.util.List;

import com.mordd.VoidDwellerCore;

import gregapi.data.CS;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.recipes.Recipe;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.UT;
import gregapi.util.WD;
import gregtech.tileentity.multiblocks.MultiTileEntityLargeHeatExchanger;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

public class MultiTileEntityHugeHeatExchanger extends MultiTileEntityLargeHeatExchanger{
	public static int outputBase = 131072;
	boolean overclock = true;
	@Override public String getTileEntityName() {return "gt.multitileentity.generator.hot_fluid_huge";}
	@Override
	public boolean checkStructure2() {
		int registryID = GT_TileEntityLoader.gtRegistry.currentID();
		int tX = xCoord-1, tY = yCoord, tZ = zCoord-1;
		if (worldObj.blockExists(tX-1, tY, tZ-1) && worldObj.blockExists(tX+1, tY, tZ-1) && worldObj.blockExists(tX-1, tY, tZ+1) && worldObj.blockExists(tX+1, tY, tZ+1)) {
			boolean tSuccess = T;
			
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY  , tZ  , 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+1, tY  , tZ  , 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY  , tZ  , 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY  , tZ+1, 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY  , tZ+1, 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY  , tZ+2, 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+1, tY  , tZ+2, 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY  , tZ+2, 18025, registryID, 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)) tSuccess = F;
			
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY+1, tZ  , 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+1, tY+1, tZ  , 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY+1, tZ  , 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY+1, tZ+1, 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+1, tY+1, tZ+1, 18025, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY+1, tZ+1, 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX  , tY+1, tZ+2, 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+1, tY+1, tZ+2, 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX+2, tY+1, tZ+2, 18101, registryID, 0, MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
			return tSuccess;
		}
		return mStructureOkay;
	}
	static {
		LH.add("gt.tooltip.multiblock.heatexchanger.huge.1", "3x3 Ring of 8 Heat Transmitters with Dense Adamantium Wall inside");
		LH.add("gt.tooltip.multiblock.heatexchanger.huge.2", "3x3 Ring of 8 Dense Adamantium Walls with Main inside");
		LH.add("gt.tooltip.multiblock.heatexchanger.huge.3", "HU output will doubled when overclock enabled and the fuel is more than 50%.");
		LH.add("gt.tooltip.multiblock.heatexchanger.huge.4", "use Screwdrive to enable/disable overclock.");
		LH.add("gt.tooltip.multiblock.heatexchanger.huge.5", "DO NOT CHANGE IT WHEN IT WAS WORKING OR IT WILL EXPLOADE!");
	}
	@Override
	public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H){	
		aList.add(Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.huge.1"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.huge.2"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.huge.3"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.huge.4"));
		aList.add(Chat.BLINKING_RED   + LH.get("gt.tooltip.multiblock.heatexchanger.huge.5"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.3"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.heatexchanger.4"));
		aList.add(Chat.CYAN     + LH.get(LH.RECIPES) + ": " + Chat.WHITE + LH.get(mRecipes.mNameInternal));
		aList.add(LH.getToolTipEfficiency(mEfficiency));
		LH.addEnergyToolTips(this, aList, null, mEnergyTypeEmitted, null, LH.get(LH.FACE_TOP));
		aList.add(Chat.ORANGE   + LH.get(LH.NO_GUI_FUNNEL_TAP_TO_TANK));
		aList.add(Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
		if (getFacingTool() != null) aList.add(Chat.DGRAY + LH.get(LH.TOOL_TO_SET_FACING_PRE) + LH.get(TOOL_LOCALISER_PREFIX + getFacingTool(), "Unknown") + LH.get(LH.TOOL_TO_SET_FACING_POST));
		aList.add(Chat.GREEN   + LH.get(LH.TOOLTIP_UNBURNABLE));
	}
	@Override
	public void onTick2(long aTimer, boolean aIsServerSide) {
		if (aIsServerSide && overclock) {
			if(this.mTanks[0].amount() > this.mTanks[0].capacity() / 2) {
				this.mRate = outputBase * 2;
			}
			else this.mRate = outputBase;
		}
		else this.mRate = outputBase;
		super.onTick2(aTimer, aIsServerSide);
	}
	@Override
	public long onToolClickMultiBlock(String aTool,long aRemainingDurability,long aQuality,Entity aPlayer,List<String> aChatReturn,IInventory aPlayerInventory,boolean aSneaking,ItemStack aStack,byte aSide,float aHitX, float aHitY, float aHitZ, ChunkCoordinates aFrom) {
		long ret = super.onToolClickMultiBlock(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ, aFrom);
		if (ret != 0) return ret;
		ret = this.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
		return ret;
	}
	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ){
		long ret = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
		if (ret != 0) return ret; 
		if (isClientSide()) return 0;
		if(aTool.equals(CS.TOOL_screwdriver)) {
			if(this.mActivity != null && this.mActivity.mActive) {
				this.explode(1.0);
				return 0;
			} 
			else overclock = !overclock;
			if(aChatReturn != null) aChatReturn.add("OverClock: " + (overclock ? "enabled" : "disabled"));
			return 100;
		}
		return 0;
	}
	
	
}
