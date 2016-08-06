package growthcraft.bamboo.common.block;

import java.util.List;
import java.util.Random;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.block.GrcBlockBase;
import growthcraft.core.util.BlockCheck;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBambooStalk extends GrcBlockBase
{
	private final int growth = GrowthCraftBamboo.getConfig().bambooStalkGrowthRate;

	public BlockBambooStalk()
	{
		super(Material.wood);
		setStepSound(soundTypeWood);
		setHardness(2.0F);
		setTickRandomly(true);
		setUnlocalizedName("grc.bamboo_stalk");
		setCreativeTab(null);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, BlockPos pos)
	{
		return true;
	}

	/************
	 * TICK
	 ************/
	@Override
	public int tickRate(World world)
	{
		return 5;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			int x1 = x;
			int y1 = y;
			int z1 = z;

			if (isBambooOnGround(world, x, y, z))
			{
				if (rand.nextInt(this.growth) == 0)
				{
					final byte b = 9;
					int amount = 10;
					final BlockBambooShoot bambooShoot = GrowthCraftBamboo.blocks.bambooShoot.getBlock();

					for (x1 = x - b; x1 <= x + b; ++x1)
					{
						for (z1 = z - b; z1 <= z + b; ++z1)
						{
							for (y1 = y - 1; y1 <= y + 1; ++y1)
							{
								final boolean flag1 = world.getBlock(x1, y1, z1) == this && isBambooOnGround(world, x1, y1, z1);
								final boolean flag2 = world.getBlock(x1, y1, z1) == bambooShoot;
								if (flag1 || flag2)
								{
									--amount;
									if (amount <= 0)
									{
										return;
									}
								}
							}
						}
					}

					x1 = x + rand.nextInt(3) - 1;
					y1 = y + rand.nextInt(2) - rand.nextInt(2);
					z1 = z + rand.nextInt(3) - 1;

					for (int loop = 0; loop < 4; ++loop)
					{
						if (world.isAirBlock(x1, y1, z1) && bambooShoot.canBlockStay(world, x1, y1, z1))
						{
							x = x1;
							y = y1;
							z = z1;
						}

						x1 = x + rand.nextInt(3) - 1;
						y1 = y + rand.nextInt(2) - rand.nextInt(2);
						z1 = z + rand.nextInt(3) - 1;
					}

					if (world.isAirBlock(x1, y1, z1) && bambooShoot.canBlockStay(world, x1, y1, z1))
					{
						world.setBlock(x1, y1, z1, bambooShoot);
					}
				}

			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		boolean flag = false;

		if (world.getBlock(pos.down()) != this)
		{
			if (!isBambooOnGround(world, pos))
			{
				flag = true;
			}
		}

		if (flag && world.getBlockMetadata(pos) == 0)
		{
			fellBlockAsItem(world, pos, state);
		}

		super.onNeighborBlockChange(world, pos, state, block);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, Block par5, int par6)
	{
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			final byte b0 = 4;
			final int j1 = b0 + 1;

			if (world.checkChunksExist(x - j1, y - j1, z - j1, x + j1, y + j1, z + j1))
			{
				for (int x1 = -b0; x1 <= b0; ++x1)
				{
					for (int y1 = -b0; y1 <= b0; ++y1)
					{
						for (int z1 = -b0; z1 <= b0; ++z1)
						{
							final Block block = world.getBlock(x + x1, y + y1, z + z1);
							if (block != null)
							{
								block.beginLeavesDecay(world, x + x1, y + y1, z + z1);
							}
						}
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return GrowthCraftBamboo.items.bamboo.getItem();
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockMetadata(x, y, z) == 0 ? true : false;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	public boolean isBambooOnGround(World world, BlockPos pos)
	{
		if (!BlockCheck.canSustainPlant(world, pos.down(), EnumFacing.UP, GrowthCraftBamboo.blocks.bambooShoot.getBlock())) return false;
		return this == world.getBlock(x, y, z);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	private boolean canFence(IBlockAccess world, BlockPos pos)
	{
		return world.getBlock(x, y, z) == GrowthCraftBamboo.blocks.bambooFence.getBlock() ||
			world.getBlock(x, y, z) == Blocks.fence_gate ||
			world.getBlock(x, y, z) == GrowthCraftBamboo.blocks.bambooFenceGate.getBlock();
	}

	private boolean canWall(IBlockAccess world, BlockPos pos)
	{
		return world.getBlock(x, y, z) == GrowthCraftBamboo.blocks.bambooWall.getBlock();
	}

	private boolean canDoor(IBlockAccess world, BlockPos pos)
	{
		return world.getBlock(x, y, z) instanceof BlockDoor;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return GrowthCraftBamboo.items.bamboo.getItem();
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
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

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		final double d0 = 0.5D;
		final double d1 = 1.0D;
		return ColorizerGrass.getGrassColor(d0, d1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state)
	{
		//return par1 == 0 ? 0xFFFFFF : ColorizerFoliage.getFoliageColorBasic();
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			int r = 0;
			int g = 0;
			int b = 0;

			for (int l1 = -1; l1 <= 1; ++l1)
			{
				for (int i2 = -1; i2 <= 1; ++i2)
				{
					final int color = world.getBiomeGenForCoords(x + i2, z + l1).getBiomeFoliageColor(x + i2, y, z + l1);
					r += (color & 16711680) >> 16;
					g += (color & 65280) >> 8;
					b += color & 255;
				}
			}

			return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
		}
		else
		{
			return 0xFFFFFF;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		float x1 = 0.25F;
		float x2 = 0.75F;
		float z1 = 0.25F;
		float z2 = 0.75F;

		if (world.getBlockMetadata(x, y, z) != 0)
		{
			if (this.canFence(world, x, y, z - 1) || this.canWall(world, x, y, z - 1) || this.canDoor(world, x, y, z - 1))
			{
				z1 = 0.0F;
			}

			if (this.canFence(world, x, y, z + 1) || this.canWall(world, x, y, z + 1) || this.canDoor(world, x, y, z + 1))
			{
				z2 = 1.0F;
			}

			if (this.canFence(world, x - 1, y, z) || this.canWall(world, x - 1, y, z) || this.canDoor(world, x - 1, y, z))
			{
				x1 = 0.0F;
			}

			if (this.canFence(world, x + 1, y, z) || this.canWall(world, x + 1, y, z) || this.canDoor(world, x + 1, y, z))
			{
				x2 = 1.0F;
			}
		}

		this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List<AxisAlignedBB> list, Entity entity)
	{
		final float x1 = 0.25F;
		final float x2 = 0.75F;
		final float z1 = 0.25F;
		final float z2 = 0.75F;

		this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
		super.addCollisionBoxesToList(world, pos, state, axis, list, entity);

		if (world.getBlockMetadata(x, y, z) != 0)
		{
			if (this.canFence(world, x, y, z - 1))
			{
				renderFence(world, axis, list, entity, x, y, z, EnumFacing.NORTH);
			}
			else if (this.canWall(world, x, y, z - 1))
			{
				renderWall(world, axis, list, entity, x, y, z, EnumFacing.NORTH);
			}
			else if (this.canDoor(world, x, y, z - 1))
			{
				renderDoor(world, axis, list, entity, x, y, z, EnumFacing.NORTH);
			}

			if (this.canFence(world, x, y, z + 1))
			{
				renderFence(world, axis, list, entity, x, y, z, EnumFacing.SOUTH);
			}
			else if (this.canWall(world, x, y, z + 1))
			{
				renderWall(world, axis, list, entity, x, y, z, EnumFacing.SOUTH);
			}
			else if (this.canDoor(world, x, y, z + 1))
			{
				renderDoor(world, axis, list, entity, x, y, z, EnumFacing.SOUTH);
			}

			if (this.canFence(world, x - 1, y, z))
			{
				renderFence(world, axis, list, entity, x, y, z, EnumFacing.WEST);
			}
			else if (this.canWall(world, x - 1, y, z))
			{
				renderWall(world, axis, list, entity, x, y, z, EnumFacing.WEST);
			}
			else if (this.canDoor(world, x - 1, y, z))
			{
				renderDoor(world, axis, list, entity, x, y, z, EnumFacing.WEST);
			}

			if (this.canFence(world, x + 1, y, z))
			{
				renderFence(world, axis, list, entity, x, y, z, EnumFacing.EAST);
			}
			else if (this.canWall(world, x + 1, y, z))
			{
				renderWall(world, axis, list, entity, x, y, z, EnumFacing.EAST);
			}
			else if (this.canDoor(world, x + 1, y, z))
			{
				renderDoor(world, axis, list, entity, x, y, z, EnumFacing.EAST);
			}
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void renderFence(World world, AxisAlignedBB axis, List list, Entity entity, BlockPos pos, EnumFacing m)
	{
		float x1 = x;
		float x2 = x + 1.0F;
		float z1 = z;
		float z2 = z + 1.0F;

		float y1 = 0.75F;
		float y2 = 0.9375F;

		if (m == EnumFacing.NORTH)
		{
			x1 = 0.4375F;
			x2 = 0.5625F;
			z1 = 0.0F;
			z2 = 0.25F;
		}
		else if (m == EnumFacing.SOUTH)
		{
			x1 = 0.4375F;
			x2 = 0.5625F;
			z1 = 0.75F;
			z2 = 1.0F;
		}
		else if (m == EnumFacing.WEST)
		{
			z1 = 0.4375F;
			z2 = 0.5625F;
			x1 = 0.0F;
			x2 = 0.25F;
		}
		else if (m == EnumFacing.EAST)
		{
			z1 = 0.4375F;
			z2 = 0.5625F;
			x1 = 0.75F;
			x2 = 1.0F;
		}

		this.setBlockBounds(x1, y1, z1, x2, y2, z2);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

		y1 = 0.375F;
		y2 = 0.5625F;

		if (m == EnumFacing.NORTH)
		{
			x1 = 0.4375F;
			x2 = 0.5625F;
			z1 = 0.0F;
			z2 = 0.25F;
		}
		else if (m == EnumFacing.SOUTH)
		{
			x1 = 0.4375F;
			x2 = 0.5625F;
			z1 = 0.75F;
			z2 = 1.0F;
		}
		else if (m == EnumFacing.WEST)
		{
			z1 = 0.4375F;
			z2 = 0.5625F;
			x1 = 0.0F;
			x2 = 0.25F;
		}
		else if (m == EnumFacing.EAST)
		{
			z1 = 0.4375F;
			z2 = 0.5625F;
			x1 = 0.75F;
			x2 = 1.0F;
		}

		this.setBlockBounds(x1, y1, z1, x2, y2, z2);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void renderWall(World world, AxisAlignedBB axis, List list, Entity entity, BlockPos pos, EnumFacing m)
	{
		float x1 = x;
		float x2 = x + 1.0F;
		float z1 = z;
		float z2 = z + 1.0F;

		final double y1 = 0.0F;
		final double y2 = 1.0F;

		if (m == EnumFacing.NORTH)
		{
			x1 = 0.375F;
			x2 = 0.625F;
			z1 = 0.0F;
			z2 = 0.25F;
		}
		else if (m == EnumFacing.SOUTH)
		{
			x1 = 0.375F;
			x2 = 0.625F;
			z1 = 0.75F;
			z2 = 1.0F;
		}
		else if (m == EnumFacing.WEST)
		{
			z1 = 0.375F;
			z2 = 0.625F;
			x1 = 0.0F;
			x2 = 0.25F;
		}
		else if (m == EnumFacing.EAST)
		{
			z1 = 0.375F;
			z2 = 0.625F;
			x1 = 0.75F;
			x2 = 1.0F;
		}

		this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void renderDoor(World world, AxisAlignedBB axis, List list, Entity entity, BlockPos pos, EnumFacing m)
	{
		float x1 = x;
		float x2 = x + 1.0F;
		float z1 = z;
		float z2 = z + 1.0F;

		final float y1 = 0.0F;
		final float y2 = 1.0F;

		int tm0;
		int tm;

		if (m == EnumFacing.NORTH)
		{
			tm0 = world.getBlockMetadata(x, y, z - 1);
			if ((tm0 & 8) > 7)
			{
				tm0 = world.getBlockMetadata(x, y - 1, z - 1);
			}
			tm = tm0 & 3;
			if (tm == 0)
			{
				x1 = 0.0F;
				x2 = 0.375F;
				z1 = 0.0F;
				z2 = 0.25F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}

			if (tm == 2)
			{
				x1 = 0.625F;
				x2 = 1.0F;
				z1 = 0.0F;
				z2 = 0.25F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}
		}
		else if (m == EnumFacing.SOUTH)
		{
			tm0 = world.getBlockMetadata(x, y, z + 1);
			if ((tm0 & 8) > 7)
			{
				tm0 = world.getBlockMetadata(x, y - 1, z + 1);
			}

			tm = tm0 & 3;
			if (tm == 0)
			{
				x1 = 0.0F;
				x2 = 0.375F;
				z1 = 0.75F;
				z2 = 1.0F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}

			if (tm == 2)
			{
				x1 = 0.625F;
				x2 = 1.0F;
				z1 = 0.75F;
				z2 = 1.0F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}
		}
		else if (m == EnumFacing.WEST)
		{
			tm0 = world.getBlockMetadata(x - 1, y, z);
			if ((tm0 & 8) > 7)
			{
				tm0 = world.getBlockMetadata(x - 1, y - 1, z);
			}
			tm = tm0 & 3;
			if (tm == 1)
			{

				x1 = 0.0F;
				x2 = 0.25F;
				z1 = 0.0F;
				z2 = 0.375F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}

			if (tm == 3)
			{

				x1 = 0.0F;
				x2 = 0.25F;
				z1 = 0.625F;
				z2 = 1.0F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}
		}
		else if (m == EnumFacing.EAST)
		{
			tm0 = world.getBlockMetadata(x + 1, y, z);
			if ((tm0 & 8) > 7)
			{
				tm0 = world.getBlockMetadata(x + 1, y - 1, z);
			}
			tm = tm0 & 3;
			if (tm == 1)
			{
				x1 = 0.75F;
				x2 = 1.0F;
				z1 = 0.0F;
				z2 = 0.375F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}

			if (tm == 3)
			{
				x1 = 0.75F;
				x2 = 1.0F;
				z1 = 0.625F;
				z2 = 1.0F;

				this.setBlockBounds(x1, y1, z1, x2, y2, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
			}
		}
	}
}
