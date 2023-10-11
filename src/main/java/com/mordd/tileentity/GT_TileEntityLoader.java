package com.mordd.tileentity;

import static gregapi.data.CS.F;
import static gregapi.data.CS.NBT_ENERGY_EMITTED;
import static gregapi.data.CS.NBT_FUELMAP;
import static gregapi.data.CS.NBT_HARDNESS;
import static gregapi.data.CS.NBT_MATERIAL;
import static gregapi.data.CS.NBT_OUTPUT;
import static gregapi.data.CS.NBT_RESISTANCE;
import static gregapi.data.CS.NBT_TEXTURE;
import static gregapi.data.CS.TOOL_wrench;

import gregapi.block.MaterialMachines;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.ANY;
import gregapi.data.FM;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.util.UT;
import gregtech.tileentity.multiblocks.MultiTileEntityLargeHeatExchanger;
import net.minecraft.block.Block;

public class GT_TileEntityLoader {
	public static MultiTileEntityRegistry registry,gtRegistry;

	public static void load() {
		registry = MultiTileEntityRegistry.getRegistry("vd.multitileentity");
		gtRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
		MultiTileEntityBlock aMachine = MultiTileEntityBlock.getOrCreate(MD.GT.mID, "machine"      , MaterialMachines.instance , Block.soundTypeMetal, TOOL_wrench , 0, 0, 15, F, F);
		registry.add("Large Heat Exchanger(Adamantium)", "Multiblock Machines", 17996, 17101, MultiTileEntityHugeHeatExchanger.class  , MT.Ad.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.Ad, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "largeheatexchanger"      , NBT_OUTPUT, 131072, NBT_FUELMAP, FM.Hot, NBT_ENERGY_EMITTED, TD.Energy.HU), "DDD", "PMP", "DDD", 'M', gtRegistry.getItem(18025), 'D', OP.plateDense.dat(MT.AnnealedCopper), 'P', OP.pipeHuge.dat(MT.Draconium));
	}
}
