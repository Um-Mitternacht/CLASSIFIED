package growthcraft.bamboo.common.block;

import java.util.Random;

import growthcraft.bamboo.common.world.WorldGenBamboo;
import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.block.ICropDataProvider;
import growthcraft.core.util.BlockCheck;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.RenderType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBambooShoot extends BlockBush implements ICropDataProvider, IGrowable
{
	//constants
	private final int growth = GrowthCraftBamboo.getConfig().bambooShootGrowthRate;

	public BlockBambooShoot()
	{
		super(Material.plants);
		setStepSound(soundTypeGrass);
		setHardness(0.0F);
		setTickRandomly(true);
		final float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		setUnlocalizedName("grc.bamboo_shoot");
		setCreativeTab(null);
	}

	public float getGrowthProgress(IBlockAccess world, BlockPos pos, IBlockState state)
	{
		return (float)(meta / 1.0);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!world.isRemote)
		{
			super.updateTick(world, pos, rand);

			if (getLightValue(world, pos.up()) >= 9 && rand.nextInt(this.growth) == 0)
			{
				markOrGrowMarked(world, pos, rand);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		super.onNeighborBlockChange(world, pos, state, block);
		checkShootChange(world, pos);
	}

	protected final void checkShootChange(World world, BlockPos pos)
	{
		if (!canBlockStay(world, pos))
		{
			dropBlockAsItem(world, pos, world.getBlockMetadata(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return (world.getFullBlockLightValue(pos) >= 8 || world.canBlockSeeSky(pos)) &&
			BlockCheck.canSustainPlant(world, x, y - 1, z, EnumFacing.UP, this);
	}

	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return GrowthCraftBamboo.items.bambooShootFood.getItem();
	}

	public void growBamboo(World world, BlockPos pos, Random rand)
	{
		if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
		final int meta = world.getBlockMetadata(pos) & 3;
		final WorldGenerator generator = new WorldGenBamboo(true);
		world.setBlockToAir(pos);
		if (!generator.generate(world, rand, pos))
		{
			world.setBlock(pos, this, meta, BlockFlags.ALL);
		}
	}

	public void markOrGrowMarked(World world, BlockPos pos, Random random)
	{
		final int meta = world.getBlockMetadata(pos);

		if ((meta & 8) == 0)
		{
			world.setBlockMetadataWithNotify(pos, meta | 8, BlockFlags.SUPRESS_RENDER);
		}
		else
		{
			this.growBamboo(world, pos, random);
		}
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		if (random.nextFloat() < 0.45D)
		{
			markOrGrowMarked(world, pos, random);
		}
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return GrowthCraftBamboo.items.bambooShootFood.getItem();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, BlockPos pos)
	{
		return null;
	}
}
