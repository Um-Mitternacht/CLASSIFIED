package growthcraft.bamboo.common.block;

import java.util.List;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.block.GrcBlockBase;

import net.minecraft.block.Block;
//import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
//import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBambooWall extends GrcBlockBase
{
	public BlockBambooWall()
	{
		super(Material.wood);
		this.useNeighborBrightness = true;
		setStepSound(soundTypeWood);
		setResistance(5.0F / 3.0F);
		setHardness(2.0F);
		setUnlocalizedName("grc.bamboo_wall");
		setCreativeTab(GrowthCraftBamboo.creativeTab);
	}

	public boolean canConnectWallTo(IBlockAccess world, BlockPos pos)
	{
		if (world.isAirBlock(pos)) return false;
		final IBlockState state = world.getBlockState(pos);
		final Block block = state.getBlock();
		if (this == block ||
			GrowthCraftBamboo.blocks.bambooStalk.getBlock() == block ||
			Blocks.glass_pane == block ||
			block instanceof BlockFenceGate ||
			block instanceof BlockFence) return true;
		return false;
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
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		GrowthCraftBamboo.getLogger().warn("(fixme) BlockBambooWall#setBlockBoundsBasedOnState");
		/*
		int tm;

		final IBlockState idXneg = world.getBlockState(pos.west());
		final IBlockState idXpos = world.getBlockState(pos.east());
		final IBlockState idZneg = world.getBlockState(pos.north());
		final IBlockState idZpos = world.getBlockState(pos.south());

		final boolean flagXneg = this.canConnectWallTo(world, x - 1, y, z) || (idXneg instanceof BlockStairs && (metaXneg & 3) == 0);
		final boolean flagXpos = this.canConnectWallTo(world, x + 1, y, z) || (idXpos instanceof BlockStairs && (metaXpos & 3) == 1);
		final boolean flagZneg = this.canConnectWallTo(world, x, y, z - 1) || (idZneg instanceof BlockStairs && (metaZneg & 3) == 2);
		final boolean flagZpos = this.canConnectWallTo(world, x, y, z + 1) || (idZpos instanceof BlockStairs && (metaZpos & 3) == 3);

		float x1 = 0.375F;
		float x2 = 0.625F;
		float z1 = 0.375F;
		float z2 = 0.625F;

		//ZNEG
		if (flagZneg)
		{
			z1 = 0.0F;
		}
		else if (idZneg instanceof BlockDoor)
		{
			if ((metaZneg & 8) > 7)
			{
				metaZneg = world.getBlockMetadata(x, y - 1, z - 1);
			}

			tm = metaZneg & 3;

			if (tm == 0 || tm == 2)
			{
				z1 = 0.0F;

				if (tm == 0)
				{
					x1 = 0.0F;
				}

				if (tm == 2)
				{
					x2 = 1.0F;
				}
			}
		}

		//ZPOS
		if (flagZpos)
		{
			z2 = 1.0F;
		}
		else if (idZpos instanceof BlockDoor)
		{
			if ((metaZpos & 8) > 7)
			{
				metaZpos = world.getBlockMetadata(x, y - 1, z + 1);
			}

			tm = metaZpos & 3;

			if (tm == 0 || tm == 2)
			{
				z2 = 1.0F;

				if (tm == 0)
				{
					x1 = 0.0F;
				}

				if (tm == 2)
				{
					x2 = 1.0F;
				}
			}
		}

		//XNEG
		if (flagXneg)
		{
			x1 = 0.0F;
		}
		else if (idXneg instanceof BlockDoor)
		{
			if ((metaXneg & 8) > 7)
			{
				metaXneg = world.getBlockMetadata(x - 1, y - 1, z);
			}

			tm = metaXneg & 3;

			if (tm == 1 || tm == 3)
			{
				x1 = 0.0F;

				if (tm == 1)
				{
					z1 = 0.0F;
				}

				if (tm == 3)
				{
					z2 = 1.0F;
				}
			}
		}

		//XPOS
		if (flagXpos)
		{
			x2 = 1.0F;
		}
		else if (idXpos instanceof BlockDoor)
		{
			if ((metaXpos & 8) > 7)
			{
				metaXpos = world.getBlockMetadata(x + 1, y - 1, z);
			}

			tm = metaXpos & 3;

			if (tm == 1 || tm == 3)
			{
				x2 = 1.0F;

				if (tm == 1)
				{
					z1 = 0.0F;
				}

				if (tm == 3)
				{
					z2 = 1.0F;
				}
			}
		}

		this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
		*/
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axis, List<AxisAlignedBB> list, Entity entity)
	{
		GrowthCraftBamboo.getLogger().warn("(fixme) BlockBambooWall#addCollisionBoxesToList");
		/*int tm;

		final Block idXneg = world.getBlock(x - 1, y, z);
		final Block idXpos = world.getBlock(x + 1, y, z);
		final Block idZneg = world.getBlock(x, y, z - 1);
		final Block idZpos = world.getBlock(x, y, z + 1);

		int metaXneg = world.getBlockMetadata(x - 1, y, z);
		int metaXpos = world.getBlockMetadata(x + 1, y, z);
		int metaZneg = world.getBlockMetadata(x, y, z - 1);
		int metaZpos = world.getBlockMetadata(x, y, z + 1);

		final boolean flagXneg = this.canConnectWallTo(world, x - 1, y, z) || (idXneg instanceof BlockStairs && (metaXneg & 3) == 0);
		final boolean flagXpos = this.canConnectWallTo(world, x + 1, y, z) || (idXpos instanceof BlockStairs && (metaXpos & 3) == 1);
		final boolean flagZneg = this.canConnectWallTo(world, x, y, z - 1) || (idZneg instanceof BlockStairs && (metaZneg & 3) == 2);
		final boolean flagZpos = this.canConnectWallTo(world, x, y, z + 1) || (idZpos instanceof BlockStairs && (metaZpos & 3) == 3);

		float x1 = 0.375F;
		float x2 = 0.625F;
		float z1 = 0.375F;
		float z2 = 0.625F;

		this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
		super.addCollisionBoxesToList(world, pos, state, axis, list, entity);

		//XNEG
		if (flagXneg)
		{
			x1 = 0.0F;
			x2 = 0.375F;
			z1 = 0.375F;
			z2 = 0.625F;

			this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}
		else if (idXneg instanceof BlockDoor)
		{
			if ((metaXneg & 8) > 7)
			{
				metaXneg = world.getBlockMetadata(x - 1, y - 1, z);
			}

			tm = metaXneg & 3;

			if (tm == 1 || tm == 3)
			{
				x1 = 0.0F;
				x2 = 0.375F;
				z1 = 0.375F;
				z2 = 0.625F;

				this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

				if (tm == 1)
				{

					x1 = 0.0F;
					x2 = 0.25F;
					z1 = 0.0F;
					z2 = 0.375F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}

				if (tm == 3)
				{

					x1 = 0.0F;
					x2 = 0.25F;
					z1 = 0.625F;
					z2 = 1.0F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}
			}
		}

		//XPOS
		if (flagXpos)
		{
			x1 = 0.625F;
			x2 = 1.0F;
			z1 = 0.375F;
			z2 = 0.625F;

			this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}
		else if (idXpos instanceof BlockDoor)
		{
			if ((metaXpos & 8) > 7)
			{
				metaXpos = world.getBlockMetadata(x + 1, y - 1, z);
			}

			tm = metaXpos & 3;

			if (tm == 1 || tm == 3)
			{
				x1 = 0.625F;
				x2 = 1.0F;
				z1 = 0.375F;
				z2 = 0.625F;

				this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

				if (tm == 1)
				{
					x1 = 0.75F;
					x2 = 1.0F;
					z1 = 0.0F;
					z2 = 0.375F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}

				if (tm == 3)
				{
					x1 = 0.75F;
					x2 = 1.0F;
					z1 = 0.625F;
					z2 = 1.0F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}
			}
		}

		//ZNEG
		if (flagZneg)
		{

			x1 = 0.375F;
			x2 = 0.625F;
			z1 = 0.0F;
			z2 = 0.375F;

			this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}
		else if (idZneg instanceof BlockDoor)
		{
			if ((metaZneg & 8) > 7)
			{
				metaZneg = world.getBlockMetadata(x, y - 1, z - 1);
			}

			tm = metaZneg & 3;

			if (tm == 0 || tm == 2)
			{
				x1 = 0.375F;
				x2 = 0.625F;
				z1 = 0.0F;
				z2 = 0.375F;

				this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

				if (tm == 0)
				{
					x1 = 0.0F;
					x2 = 0.375F;
					z1 = 0.0F;
					z2 = 0.25F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}

				if (tm == 2)
				{
					x1 = 0.625F;
					x2 = 1.0F;
					z1 = 0.0F;
					z2 = 0.25F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}
			}
		}

		//ZPOS
		if (flagZpos)
		{
			x1 = 0.375F;
			x2 = 0.625F;
			z1 = 0.625F;
			z2 = 1.0F;

			this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}
		else if (idZpos instanceof BlockDoor)
		{
			if ((metaZpos & 8) > 7)
			{
				metaZpos = world.getBlockMetadata(x, y - 1, z + 1);
			}

			tm = metaZpos & 3;

			if (tm == 0 || tm == 2)
			{
				x1 = 0.375F;
				x2 = 0.625F;
				z1 = 0.625F;
				z2 = 1.0F;

				this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
				super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

				if (tm == 0)
				{
					x1 = 0.0F;
					x2 = 0.375F;
					z1 = 0.75F;
					z2 = 1.0F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}

				if (tm == 2)
				{
					x1 = 0.625F;
					x2 = 1.0F;
					z1 = 0.75F;
					z2 = 1.0F;

					this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
					super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
				}
			}
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
		*/
	}
}
