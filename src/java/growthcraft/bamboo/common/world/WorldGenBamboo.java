package growthcraft.bamboo.common.world;

import java.util.Random;

import growthcraft.bamboo.GrowthCraftBamboo;

import net.minecraft.block.Block;
//import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.World;

public class WorldGenBamboo extends WorldGenAbstractTree
{
	private final int density = GrowthCraftBamboo.getConfig().bambooWorldGenDensity;
	private final int minTreeHeight = GrowthCraftBamboo.getConfig().bambooTreeMinHeight;
	private final int maxTreeHeight = GrowthCraftBamboo.getConfig().bambooTreeMaxHeight;
	private final Block leaves = GrowthCraftBamboo.blocks.bambooLeaves.getBlock();
	private final Block log = GrowthCraftBamboo.blocks.bambooStalk.getBlock();

	public WorldGenBamboo(boolean doblocknotify)
	{
		super(doblocknotify);
	}

	public boolean generateClumps(World world, Random rand, BlockPos pos)
	{
		for (int loop = 0; loop < this.density; ++loop)
		{
			final int x = pos.getX() + rand.nextInt(8) - rand.nextInt(8);
			final int y = pos.getY() + rand.nextInt(4) - rand.nextInt(4);
			final int z = pos.getZ() + rand.nextInt(8) - rand.nextInt(8);
			this.generate(world, rand, new BlockPos(x, y, z));
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		GrowthCraftBamboo.getLogger().warn("(fixme) WorldGenBamboo#generate");
		/*final int height = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (j >= 1 && j + height + 1 <= maxTreeHeight)
		{
			int y;
			byte b0;
			int x;
			int z;
			Block checkBlock;

			for (y = j; y <= j + 1 + height; ++y)
			{
				b0 = 0;

				if (y >= j + height - 3)
				{
					b0 = 1;
				}

				for (x = i - b0; x <= i + b0 && flag; ++x)
				{
					for (z = k - b0; z <= k + b0 && flag; ++z)
					{
						if (y >= 0 && y < maxTreeHeight)
						{
							checkBlock = world.getBlock(x, y, z);

							if (!this.isReplaceable(world, x, y, z))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				final Block soil = world.getBlock(i, j - 1, k);
				final boolean isSoil = soil != null &&
					soil.canSustainPlant(world, i, j - 1, k, EnumFacing.UP, (BlockSapling)Blocks.sapling);

				int it;
				Block block;

				if (isSoil && j < maxTreeHeight - height - 1)
				{
					soil.onPlantGrow(world, i, j - 1, k, i, j, k);

					for (it = 0; it <= 3; ++it)
					{
						y = (j + 5 + height - this.minTreeHeight) + (it * 2);

						for (x = i - 1; x <= i + 1; ++x)
						{
							final int x2 = x - i;

							for (z = k - 1; z <= k + 1; ++z)
							{
								final int z2 = z - k;

								block = world.getBlock(x, y, z);

								if ((Math.abs(x2) != 1 || Math.abs(z2) != 1 || 1 <= 0) && (block == null || block.canBeReplacedByLeaves(world, x, y, z)))
								{
									this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
								}
							}
						}
					}

					y = j + height - 2;
					for (x = i - 1; x <= i + 1; ++x)
					{
						for (z = k - 1; z <= k + 1; ++z)
						{
							block = world.getBlock(x, y, z);

							if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
							{
								this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
							}
						}
					}

					x = i;
					z = k;
					for (it = 0; it <= 3; ++it)
					{
						switch(it)
						{
							case 0: x = i - 2; z = k; break;
							case 1: x = i + 2; z = k; break;
							case 2: x = i; z = k - 2; break;
							case 3: x = i; z = k + 2; break;
							default: break;
						}

						block = world.getBlock(x, y, z);

						if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
						{
							this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
						}
					}

					y = j + height;
					block = world.getBlock(i, y, k);

					if (block == null || block.canBeReplacedByLeaves(world, i, y, k))
					{
						this.setBlockAndNotifyAdequately(world, i, y, k, this.leaves, 0);
					}

					for (y = 0; y < height - 1; ++y)
					{
						block = world.getBlock(i, j + y, k);

						if (block.isAir(world, it, j + y, k) || block.isLeaves(world, i, j + y, k))
						{
							this.setBlockAndNotifyAdequately(world, i, j + y, k, this.log, 0);
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		*/
		return false;
	}

	@Override
	public boolean func_150523_a(Block block)
	{
		return block.getMaterial() == Material.air ||
			block.getMaterial() == Material.leaves ||
			block == Blocks.grass ||
			block == Blocks.dirt ||
			block == Blocks.log ||
			block == Blocks.log2 ||
			block == Blocks.sapling ||
			block == Blocks.vine;
	}

	@Override
	public boolean isReplaceable(World world, BlockPos pos)
	{
		final Block block = world.getBlockState(pos).getBlock();
		return block.isAir(world, pos) ||
			block.isLeaves(world, pos) ||
			block.isWood(world, pos) ||
			func_150523_a(block);
	}
}
