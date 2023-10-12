package com.mordd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mordd.block.BlockLoader;
import com.mordd.block.MultiBlock;
import com.mordd.gui.GuiHandler;
import com.mordd.item.ItemLoader;
import com.mordd.item.ItemMultiBlock;
import com.mordd.item.MultiItem;
import com.mordd.tileentity.GT_TileEntityLoader;
import com.mordd.tileentity.TileEntityLoader;

import cpw.mods.fml.common.Mod;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.item.multiitem.MultiItemRandomWithCompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

/**
 * @author mordd
 * 
 * 
 * If you have ANY Problems with the examples here, then you can contact me on the Forums or IRC.
 * 
 * You may ask yourself why there are no imports on this File.
 * I decided to do that, so Beginners cannot mess up by choosing wrong imports when they copy and paste Stuff.
 * Also I avoided creating Variables, because people tend to copy them over for no reason, because they don't understand what they were for, and that they could be removed easily.
 * 
 * Note: it is important to load after "gregapi_post".
 * 
 * Note: There are NO TEXTURES contained in GT that correspond to the Examples. Those you will have to do or copy them yourself.
 * 
 * uncomment the @cpw.mods.fml.common.Mod-Annotation for actual usage.
 */
@cpw.mods.fml.common.Mod(modid=VoidDwellerCore.MOD_ID, name=VoidDwellerCore.MOD_NAME, version=VoidDwellerCore.VERSION, dependencies="required-after:gregapi_post")
public final class VoidDwellerCore extends gregapi.api.Abstract_Mod {
	/** Your Mod-ID has to be LOWERCASE and without Spaces. Uppercase Chars and Spaces can create problems with Resource Packs. This is a vanilla forge "Issue". */
	public static final String MOD_ID = "vd_core"; // <-- TODO: you need to change this to the ID of your own Mod, and then remove this Comment after you did that.
	/** This is your Mods Name */
	public static final String MOD_NAME = "Void Dweller Core"; // <-- TODO: you need to change this to the Name of your own Mod, and then remove this Comment after you did that.
	/** This is your Mods Version */
	public static final String VERSION = "0.2.0_0117";
	/** Contains a ModData Object for ID and Name. Doesn't have to be changed. */
	public static gregapi.code.ModData MOD_DATA = new gregapi.code.ModData(MOD_ID, MOD_NAME);
	
	public static Logger logger = LogManager.getLogger(MOD_ID);
	
	@cpw.mods.fml.common.SidedProxy(modId = MOD_ID, clientSide = "com.mordd.ClientProxy", serverSide = "com.mordd.ServerProxy")
	public static gregapi.api.Abstract_Proxy PROXY;
	@Mod.Instance
	public static VoidDwellerCore instance;
	
	public VoidDwellerCore() {
		instance = this;
	}
	
	@Override public String getModID() {return MOD_ID;}
	@Override public String getModName() {return MOD_NAME;}
	@Override public String getModNameForLog() {return "Void Dweller Core";}
	@Override public gregapi.api.Abstract_Proxy getProxy() {return PROXY;}
	
	// Do not change these 7 Functions. Just keep them this way.
	@cpw.mods.fml.common.Mod.EventHandler public final void onPreLoad           (cpw.mods.fml.common.event.FMLPreInitializationEvent    aEvent) {onModPreInit(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onLoad              (cpw.mods.fml.common.event.FMLInitializationEvent       aEvent) {onModInit(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onPostLoad          (cpw.mods.fml.common.event.FMLPostInitializationEvent   aEvent) {onModPostInit(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onServerStarting    (cpw.mods.fml.common.event.FMLServerStartingEvent       aEvent) {onModServerStarting(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onServerStarted     (cpw.mods.fml.common.event.FMLServerStartedEvent        aEvent) {onModServerStarted(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onServerStopping    (cpw.mods.fml.common.event.FMLServerStoppingEvent       aEvent) {onModServerStopping(aEvent);}
	@cpw.mods.fml.common.Mod.EventHandler public final void onServerStopped     (cpw.mods.fml.common.event.FMLServerStoppedEvent        aEvent) {onModServerStopped(aEvent);}
	
	@Override
	public void onModPreInit2(cpw.mods.fml.common.event.FMLPreInitializationEvent event) {
		new MultiTileEntityRegistry("vd.multitileentity");
		
		new ConfigLoader(event);
		BlockLoader.registerBlocks();
		ItemLoader.loadItems();
		new TileEntityLoader();
		
		
		// If you want to make yourself a new OreDict Prefix for your Component Items or similar.
	}
	
	@Override
	public void onModInit2(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {

		
		
		new GuiHandler();
		GT_TileEntityLoader.load();
		
	}
	
	@Override
	public void onModPostInit2(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
		// Insert your PostInit Code here and not above
	}
	
	@Override
	public void onModServerStarting2(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
		// Insert your ServerStarting Code here and not above
	}
	
	@Override
	public void onModServerStarted2(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
		// Insert your ServerStarted Code here and not above
	}
	
	@Override
	public void onModServerStopping2(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
		// Insert your ServerStopping Code here and not above
	}
	
	@Override
	public void onModServerStopped2(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
		// Insert your ServerStopped Code here and not above
	}
}

