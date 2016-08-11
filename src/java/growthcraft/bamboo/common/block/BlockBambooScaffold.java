package growthcraft.bamboo.common.block;

import java.util.Random;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.block.GrcBlockBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBambooScaffold extends GrcBlockBase
{
	public BlockBambooScaffold()
	{
		super(Material.wood);
		setStepSound(soundTypeWood);
		setResistance(0.2F);
		setHardness(0.5F);
		setUnlocalizedName("bamboo_scaffold");
		setCreativeTab(GrowthCraftBamboo.creativeTab);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		onNeighborBlockChange(world, pos, state, null);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		GrowthCraftBamboo.getLogger().warn("(fixme) BlockBambooScaffold#onBlockActivated");
		final ItemStack itemstack = player.inventory.getCurrentItem();
		if (itemstack != null)
		{
			if (itemstack.getItem() == Item.getItemFromBlock(this))
			{
				final int loop = world.getActualHeight();
				for (int j = pos.getY() + 1; j < loop; j++)
				{
					final BlockPos lPos = new BlockPos(pos.getX(), j, pos.getZ());
					final IBlockState lBlockState = world.getBlockState(lPos);
					final Block block = lBlockState.getBlock();
					if ((block == null) || (world.isAirBlock(lPos)) || (block.isReplaceable(world, lPos)))
					{
						if (!world.isRemote)
						{
							if (world.setBlockState(lPos, getDefaultState(), BlockFlags.UPDATE_AND_SYNC) && !player.capabilities.isCreativeMode)
							{
								itemstack.stackSize -= 1;
							}
							return true;
						}
					}
					if (block != this)
					{
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		if (!canBlockStay(world, pos))
		{
			fellBlockAsItem(world, pos, state);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
	{
		entity.fallDistance = 0.0F;
		if (entity.isCollidedHorizontally)
		{
			entity.motionY = 0.2D;
		}
		else if (entity.isSneaking())
		{
			final double d = entity.prevPosY - entity.posY;
			entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0, d, 0));
			entity.posY = entity.prevPosY;
		}
		else
		{
			entity.motionY = -0.1D;
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return canBlockStay(world, pos);
	}

	public boolean canBlockStay(World world, BlockPos pos)
	{
		if (world.getBlockState(pos.down()).getBlock().isSideSolid(world, pos.down(), EnumFacing.UP)) return true;
		if (checkSides(world, pos)) return true;

		return false;
	}

	private boolean checkSides(World world, BlockPos pos)
	{
		final boolean flag = world.getBlockState(pos.east()).getBlock().isAssociatedBlock(this);
		final boolean flag1 = world.getBlockState(pos.west()).getBlock().isAssociatedBlock(this);
		final boolean flag2 = world.getBlockState(pos.north()).getBlock().isAssociatedBlock(this);
		final boolean flag3 = world.getBlockState(pos.south()).getBlock().isAssociatedBlock(this);

		if (!flag && !flag1 && !flag2 && !flag3) return false;

		GrowthCraftBamboo.getLogger().warn("(fixme) BlockBambooScaffold#checkSides");
		//if (flag && world.getBlock(x + 1, y - 1, z).isSideSolid(world, x + 1, y - 1, z, EnumFacing.UP)) return true;
		//if (flag1 && world.getBlock(x - 1, y - 1, z).isSideSolid(world, x - 1, y - 1, z, EnumFacing.UP)) return true;
		//if (flag2 && world.getBlock(x, y - 1, z + 1).isSideSolid(world, x, y - 1, z + 1, EnumFacing.UP)) return true;
		//if (flag3 && world.getBlock(x, y - 1, z - 1).isSideSolid(world, x, y - 1, z - 1, EnumFacing.UP)) return true;

		//if (flag && world.getBlock(x + 2, y - 1, z).isSideSolid(world, x + 2, y - 1, z, EnumFacing.UP)) return true;
		//if (flag1 && world.getBlock(x - 2, y - 1, z).isSideSolid(world, x - 2, y - 1, z, EnumFacing.UP)) return true;
		//if (flag2 && world.getBlock(x, y - 1, z + 2).isSideSolid(world, x, y - 1, z + 2, EnumFacing.UP)) return true;
		//if (flag3 && world.getBlock(x, y - 1, z - 2).isSideSolid(world, x, y - 1, z - 2, EnumFacing.UP)) return true;

		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return EnumFacing.UP == side;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		return true;
	}

	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		final float f = 0.125F;
		return AxisAlignedBB.fromBounds(
			pos.getX() + this.minX + f,
			pos.getY() + this.minY,
			pos.getZ() + this.minZ + f,
			pos.getX() + this.maxX - f,
			pos.getY() + this.maxY,
			pos.getZ() + this.maxZ - f);
	}
}
