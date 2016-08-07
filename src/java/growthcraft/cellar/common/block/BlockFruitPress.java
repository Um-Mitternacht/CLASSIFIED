package growthcraft.cellar.common.block;

import java.util.Random;

import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.cellar.util.CellarGuiType;
import growthcraft.api.core.util.BlockFlags;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFruitPress extends BlockCellarContainer
{
	public BlockFruitPress()
	{
		super(Material.wood);
		setTileEntityType(TileEntityFruitPress.class);
		setHardness(2.0F);
		setStepSound(soundTypeWood);
		setUnlocalizedName("grc.fruit_press");
		setCreativeTab(GrowthCraftCellar.tab);
		setGuiType(CellarGuiType.FRUIT_PRESS);
	}

	private Block getPresserBlock()
	{
		return GrowthCraftCellar.blocks.fruitPresser.getBlock();
	}

	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public void doRotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		final EnumFacing rot = side.rotateY();
		world.setBlockState(pos, world.getBlockState(pos).withProperty(ROTATION, rot.ordinal()), BlockFlags.SYNC);
		world.setBlockState(pos.up(), world.getBlockState(pos).withProperty(ROTATION, rot.ordinal()), BlockFlags.SYNC);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		world.setBlockState(pos.up(), getPresserBlock().getDefaultState().withProperty(ROTATION, state.getValue(ROTATION)), BlockFlags.SYNC);
	}


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, pos, state, entity, stack);
		final int a = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		GrowthCraftCellar.getLogger().warn("(fixme) cellar/BlockFruitPress#onBlockPlacedBy");
		//if (a == 0 || a == 2)
		//{
		//	world.setBlockMetadataWithNotify(x, y, z, 0, BlockFlags.SYNC);
		//}
		//else if (a == 1 || a == 3)
		//{
		//	world.setBlockMetadataWithNotify(x, y, z, 1, BlockFlags.SYNC);
		//}

		world.setBlockState(pos.up(), getPresserBlock().getDefaultState().withProperty(ROTATION, state.getValue(ROTATION)), BlockFlags.SYNC);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode && presserIsAbove(world, pos))
		{
			world.destroyBlock(pos.up(), true);
			world.getTileEntity(pos.up()).invalidate();
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		if (!this.canBlockStay(world, pos))
		{
			world.destroyBlock(pos, true);
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		final IBlockState state = world.getBlockState(pos);
		final EnumFacing rot = EnumFacing.getFront((int)state.getValue(ROTATION));
		switch (rot)
		{
			case NORTH:
			case SOUTH:
				return side == EnumFacing.EAST || side == EnumFacing.WEST;
			case EAST:
			case WEST:
				return side == EnumFacing.EAST || side == EnumFacing.WEST;
			default:
				return isNormalCube(world, pos);
		}
		return isNormalCube(world, pos);
	}

	/************
	 * STUFF
	 ************/

	/**
	 * @param world - world block is in
	 * @param pos
	 * @return true if the BlockFruitPresser is above this block, false otherwise
	 */
	public boolean presserIsAbove(World world, BlockPos pos)
	{
		return getPresserBlock() == world.getBlockState(pos.up()).getBlock();
	}

	public boolean canBlockStay(World world, BlockPos pos)
	{
		return presserIsAbove(world, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		if (pos.getY() >= 255) return false;

		return World.doesBlockHaveSolidTopSurface(world, pos.down()) &&
			super.canPlaceBlockAt(world, pos) &&
			super.canPlaceBlockAt(world, pos.up());
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
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, BlockPos pos)
	{
		final TileEntityFruitPress te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.getFluidAmountScaled(15, 0);
		}
		return 0;
	}
}
