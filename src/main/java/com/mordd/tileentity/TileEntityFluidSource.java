package com.mordd.tileentity;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import scala.Int;

public class TileEntityFluidSource extends TileEntity implements IFluidHandler{
	Fluid fluid = FluidRegistry.WATER;
	public TileEntityFluidSource() {}
	public TileEntityFluidSource(Fluid fluid) {

		this.fluid = fluid;
	}
	public Fluid getFluid() {
		return fluid;
	}
	public void setFluid(Fluid fluid) {
		this.fluid = fluid;
	}
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,0,nbt);
	}
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		this.readFromNBT(nbt);
	}

	@Override
	public void updateEntity() {
		for(ForgeDirection side : ForgeDirection.values()) {
			TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY, this.zCoord + side.offsetZ);
			if(tile != null && tile instanceof IFluidHandler) {
				int mAmount = ((IFluidHandler)tile).fill(side.getOpposite(), new FluidStack(fluid,Int.MaxValue()), false);
				if(mAmount != 0) {
					((IFluidHandler)tile).fill(side.getOpposite(), new FluidStack(fluid,mAmount), true);
				}
			}
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		fluid = FluidRegistry.getFluid(nbt.getString("fluid"));
		if(fluid == null) fluid = FluidRegistry.WATER;
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("fluid",fluid.getName());
	}
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return new FluidStack(fluid,resource.amount);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return new FluidStack(fluid,maxDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {new FluidTankInfo(new FluidStack(fluid,65535),65535)};
	}
}
