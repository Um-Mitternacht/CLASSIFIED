/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.milk.common.tileentity;

import java.io.IOException;

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.api.core.util.FXHelper;
import growthcraft.api.core.util.Pair;
import growthcraft.api.core.util.PulseStepper;
import growthcraft.api.core.util.SpatialRandom;
import growthcraft.api.core.util.TickUtils;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.GrcTileEntityBase;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.common.struct.CheeseCurd;
import growthcraft.milk.GrowthCraftMilk;

import io.netty.buffer.ByteBuf;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityHangingCurds extends GrcTileEntityBase implements ITickable, INBTItemSerializable
{
	// SpatialRandom instance
	private SpatialRandom sprand = new SpatialRandom();
	// Pulsar instance
	private PulseStepper wheyPulsar = new PulseStepper(TickUtils.seconds(15), 10);

	// the following variables are responsible for step tracking
	/// This pulse stepper is used to control the 'drip' animation
	private PulseStepper animPulsar = new PulseStepper(10, 4);

	/// The server will increment this value whenever it does a drip step
	private int serverStep;

	/// Clients will set this value to the serverStep value and proceed with the drip animation
	@SideOnly(Side.CLIENT)
	private int clientStep;

	private CheeseCurd cheeseCurd = new CheeseCurd();

	private IPancheonTile getPancheonTile()
	{
		for (int i = 1; i < 3; ++i)
		{
			final BlockPos downPos = getPos().down(i);
			final TileEntity te = worldObj.getTileEntity(downPos);
			if (te instanceof IPancheonTile)
			{
				return (IPancheonTile)te;
			}
			else
			{
				if (!worldObj.isAirBlock(downPos)) break;
			}
		}
		return null;
	}

	public int getRenderColor()
	{
		return cheeseCurd.getRenderColor();
	}

	public float getProgress()
	{
		return cheeseCurd.getAgeProgress();
	}

	public boolean isDried()
	{
		return cheeseCurd.isDried();
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			if (cheeseCurd.needClientUpdate)
			{
				cheeseCurd.needClientUpdate = false;
				markForBlockUpdate();
			}
			cheeseCurd.update();
			if (wheyPulsar.update() == PulseStepper.State.PULSE)
			{
				final IPancheonTile pancheonTile = getPancheonTile();
				// When a pancheon is present, try filling it with Whey
				if (pancheonTile != null)
				{
					final FluidStack stack = GrowthCraftMilk.fluids.whey.fluid.asFluidStack(100);
					if (pancheonTile.canFill(EnumFacing.UP, stack.getFluid()))
					{
						pancheonTile.fill(EnumFacing.UP, stack, true);
					}
				}
				// regardless of a pancheon being present, the curd SHOULD drip
				serverStep++;
				markForBlockUpdate();
			}
		}
		else
		{
			if (clientStep != serverStep)
			{
				this.clientStep = serverStep;
				animPulsar.reset();
			}

			if (animPulsar.update() == PulseStepper.State.PULSE)
			{
				final Pair<Double, Double> p = sprand.nextCenteredD2();
				final BlockPos pos = getPos();
				final double px = pos.getX() + 0.5 + p.left * 0.5;
				final double py = pos.getY();
				final double pz = pos.getZ() + 0.5 + p.right * 0.5;
				FXHelper.dropParticle(worldObj, px, py, pz, GrowthCraftMilk.fluids.whey.getItemColor());
			}
		}
	}

	protected void readCheeseCurdFromNBT(NBTTagCompound nbt)
	{
		cheeseCurd.readFromNBT(nbt);
	}

	protected void readWheyPulsarFromNBT(NBTTagCompound nbt)
	{
		wheyPulsar.readFromNBT(nbt, "whey_pulsar");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCheeseCurdFromNBT(nbt);
		readWheyPulsarFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readCheeseCurdFromNBT(nbt);
		readWheyPulsarFromNBT(nbt);
	}

	protected void writeCheeseCurdToNBT(NBTTagCompound nbt)
	{
		cheeseCurd.writeToNBT(nbt);
	}

	protected void writeWheyPulsarToNBT(NBTTagCompound nbt)
	{
		wheyPulsar.writeToNBT(nbt, "whey_pulsar");
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCheeseCurdToNBT(nbt);
		writeWheyPulsarToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeCheeseCurdToNBT(nbt);
		writeWheyPulsarToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_HangingCurds(ByteBuf stream) throws IOException
	{
		cheeseCurd.readFromStream(stream);
		wheyPulsar.readFromStream(stream);
		this.serverStep = stream.readInt();
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_HangingCurds(ByteBuf stream) throws IOException
	{
		cheeseCurd.writeToStream(stream);
		wheyPulsar.writeToStream(stream);
		stream.writeInt(serverStep);
		return true;
	}

	public ItemStack asItemStack()
	{
		final ItemStack stack = GrowthCraftMilk.blocks.hangingCurds.asStack();
		final NBTTagCompound tag = ItemBlockHangingCurds.openNBT(stack);
		writeToNBTForItem(tag);
		return stack;
	}
}
