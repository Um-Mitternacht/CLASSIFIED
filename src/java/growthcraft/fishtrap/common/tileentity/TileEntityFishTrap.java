package growthcraft.fishtrap.common.tileentity;

import growthcraft.core.common.tileentity.GrcTileEntityBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFishTrap extends GrcTileEntityBase implements IInventory
{
	// Constants
	private ItemStack[] invSlots   = new ItemStack[5];

	// Other Vars.
	private String   name;

	/************
	 * UPDATE
	 ************/
	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public boolean canAddStack(ItemStack stack, int index)
	{
		if (this.invSlots[index] == null) return true;
		if (!this.invSlots[index].isItemEqual(stack)) return false;
		final int result = this.invSlots[index].stackSize + stack.stackSize;
		return result <= getInventoryStackLimit() && result <= stack.getMaxStackSize();
	}

	public void addStack(ItemStack stack)
	{
		for (int loop = 0; loop < this.invSlots.length; loop++)
		{
			if (canAddStack(stack, loop))
			{
				if (this.invSlots[loop] == null)
				{
					this.invSlots[loop] = stack.copy();
				}
				else if (this.invSlots[loop].isItemEqual(stack))
				{
					this.invSlots[loop].stackSize += stack.stackSize;
				}
				break;
			}
		}
	}

	/************
	 * INVENTORY
	 ************/
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.invSlots[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int par2)
	{
		if (this.invSlots[index] != null)
		{
			ItemStack itemstack;

			if (this.invSlots[index].stackSize <= par2)
			{
				itemstack = this.invSlots[index];
				this.invSlots[index] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.invSlots[index].splitStack(par2);

				if (this.invSlots[index].stackSize == 0)
				{
					this.invSlots[index] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.invSlots[index] != null)
		{
			final ItemStack itemstack = this.invSlots[index];
			this.invSlots[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemstack)
	{
		this.invSlots[index] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public int getSizeInventory()
	{
		return this.invSlots.length;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack)
	{
		return true;
	}

	/************
	 * NBT
	 ************/
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		final NBTTagList tags = nbt.getTagList("items", 10);
		this.invSlots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tags.tagCount(); ++i)
		{
			final NBTTagCompound nbttagcompound1 = tags.getCompoundTagAt(i);
			final byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.invSlots.length)
			{
				this.invSlots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		if (nbt.hasKey("name"))
		{
			this.name = nbt.getString("name");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		final NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.invSlots.length; ++i)
		{
			if (this.invSlots[i] != null)
			{
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.invSlots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("items", nbttaglist);

		if (this.hasCustomName())
		{
			nbt.setString("name", this.name);
		}
	}

	@Override
	public String getDisplayName()
	{
		return this.hasCustomName() ? this.name : "container.grc.fishTrap";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.name != null && this.name.length() > 0;
	}

	@Override
	public void setGuiDisplayName(String string)
	{
		this.name = string;
	}
}
