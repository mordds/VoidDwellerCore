package com.mordd.gui;



import com.mordd.VoidDwellerCore;
import com.mordd.tileentity.TileEntityAdvancedArcaneTable;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class GuiHandler implements IGuiHandler
{
    public static final int GUI_ARCANE = 1;

    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(VoidDwellerCore.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        switch (ID)
        {
        case GUI_ARCANE:
            return new ContainerAdvancedArcaneTable(player,(TileEntityAdvancedArcaneTable)world.getTileEntity(x, y, z));
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case GUI_ARCANE:
            return new GuiContainerAdvancedArcaneTable(new ContainerAdvancedArcaneTable(player,(TileEntityAdvancedArcaneTable)world.getTileEntity(x, y, z)));
        default:
            return null;
        }
    }
}