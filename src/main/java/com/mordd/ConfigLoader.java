package com.mordd;

import java.io.File;

import com.mordd.util.Utils;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader {
	public static String[] fluxMaterialList;
	
	
	public ConfigLoader(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "vd_core.cfg"));
		config.load();

		fluxMaterialList = config.getStringList("materials", "vd_core.materials.fluxCrystal",new String[] {MT.OREMATS.Magnetite.mNameInternal,MT.OREMATS.Tetrahedrite.mNameInternal}, "");
		for(String s : fluxMaterialList) {
			OreDictMaterial m = OreDictMaterial.get(s);
			if(m != OreDictMaterial.NULL && m != null) Utils.fluxCrystalMaterials.add(m);
			
		}
		config.save();
		
	}
}
