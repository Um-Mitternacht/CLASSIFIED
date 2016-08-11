package growthcraft.core.common.block;

import java.util.List;
import java.util.Random;

import growthcraft.core.GrowthCraftCore;
import growthcraft.core.util.BlockCheck;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRope extends GrcBlockBase implements IBlockRope
{
	public BlockRope()
	{
		super(Material.circuits);
		this.setHardness(0.5F);
		//this.setStepSound(soundWoodFootstep);
		this.setUnlocalizedName("rope_block");
		this.setCreativeTab(null);
	}

	protected final void checkBlockCoordValid(World world, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		this.checkBlockCoordValid(world, pos, state);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		for (EnumFacing face : EnumFacing.VALUES)
		{
			if (this.canConnectRopeTo(world, pos.offset(face))) return true;
		}
		return false;
	}

	public boolean canBlockStay(World world, BlockPos pos)
	{
		return this.canPlaceBlockAt(world, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return GrowthCraftCore.items.rope.getItem();
	}

	@Override
	public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos)
	{
		return BlockCheck.isRopeBlock(world.getBlockState(pos));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return GrowthCraftCore.items.rope.getItem();
	}

	@Override
	public int quantityDropped(Random random)
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
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB aabb, List<AxisAlignedBB> list, Entity entity)
	{
		final boolean flag = this.canConnectRopeTo(world, pos.north());
		final boolean flag1 = this.canConnectRopeTo(world, pos.south());
		final boolean flag2 = this.canConnectRopeTo(world, pos.west());
		final boolean flag3 = this.canConnectRopeTo(world, pos.east());
		final boolean flag4 = this.canConnectRopeTo(world, pos.down());
		final boolean flag5 = this.canConnectRopeTo(world, pos.up());
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;
		float f4 = 0.4375F;
		float f5 = 0.5625F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag || flag1)
		{
			this.setBlockBounds(f, f4, f2, f1, f5, f3);
			super.addCollisionBoxesToList(world, pos, state, aabb, list, entity);
		}

		f2 = 0.4375F;
		f3 = 0.5625F;

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		if (flag2 || flag3)
		{
			this.setBlockBounds(f, f4, f2, f1, f5, f3);
			super.addCollisionBoxesToList(world, pos, state, aabb, list, entity);
		}

		f = 0.4375F;
		f1 = 0.5625F;

		if (flag4)
		{
			f4 = 0.0F;
		}

		if (flag5)
		{
			f5 = 1.0F;
		}

		if (flag4 || flag5)
		{
			this.setBlockBounds(f, f4, f2, f1, f5, f3);
			super.addCollisionBoxesToList(world, pos, state, aabb, list, entity);
		}

		this.setBlockBoundsBasedOnState(world, pos);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		final boolean flag = this.canConnectRopeTo(world, pos.north());
		final boolean flag1 = this.canConnectRopeTo(world, pos.south());
		final boolean flag2 = this.canConnectRopeTo(world, pos.west());
		final boolean flag3 = this.canConnectRopeTo(world, pos.east());
		final boolean flag4 = this.canConnectRopeTo(world, pos.down());
		final boolean flag5 = this.canConnectRopeTo(world, pos.up());
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;
		float f4 = 0.4375F;
		float f5 = 0.5625F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		if (flag4)
		{
			f4 = 0.0F;
		}

		if (flag5)
		{
			f5 = 1.0F;
		}

		this.setBlockBounds(f, f4, f2, f1, f5, f3);
	}

}
