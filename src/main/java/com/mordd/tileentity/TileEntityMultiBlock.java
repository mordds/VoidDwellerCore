package com.mordd.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMultiBlock extends TileEntity{
	public short meta;
	public TileEntityMultiBlock(short meta) {
		this.meta = meta;
		
	}
	public TileEntityMultiBlock() {
		
	}
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		meta = nbt.getShort("meta");
	}
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("meta", meta);
	}
	public boolean canUpdate() {
		return false;
	}
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,0,nbt);
	}
	public void onDataPacket(NetworkManager manager,S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.func_148857_g();
		if(nbt != null) this.readFromNBT(nbt);
	}
}
