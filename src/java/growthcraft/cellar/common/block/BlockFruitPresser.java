package growthcraft.cellar.common.block;

import java.util.Random;

import growthcraft.cellar.common.tileentity.TileEntityFruitPresser;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.common.block.IRotatableBlock;
import growthcraft.core.common.block.IWrenchable;
import growthcraft.api.core.util.BlockFlags;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFruitPresser extends BlockCellarContainer implements IWrenchable, IRotatableBlock
{
	public static final PropertyBool PRESS_STATE = PropertyBool.create("press_state");

	public BlockFruitPresser()
	{
		super(Material.piston);
		this.isBlockContainer = true;
		setHardness(0.5F);
		setStepSound(soundTypePiston);
		setUnlocalizedName("fruit_presser");
		setCreativeTab(null);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.9375F, 0.8125F);
		setTileEntityType(TileEntityFruitPresser.class);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PRESS_STATE, false));
	}

	@Override
	@SuppressWarnings({"rawtypes"})
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING, PRESS_STATE});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState()
			.withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
			.withProperty(PRESS_STATE, meta % 4 == 4);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex() |
			(state.getValue(PRESS_STATE) ? 4 : 0);
	}

	public String getPressStateName(int meta)
	{
		switch ((meta & 4) >> 2)
		{
			case 0:
				return "unpressed";
			case 1:
				return "pressed";
			default:
				return "invalid";
		}
	}

	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		final IBlockState below = world.getBlockState(pos.down());
		if (below.getBlock() instanceof IRotatableBlock)
		{
			return ((IRotatableBlock)below).isRotatable(world, pos.down(), side);
		}
		return false;
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		if (isRotatable(world, pos, side))
		{
			final IBlockState below = world.getBlockState(pos.down());
			return below.getBlock().rotateBlock(world, pos.down(), side);
		}
		return false;
	}

	@Override
	public boolean wrenchBlock(World world, BlockPos pos, EntityPlayer player, ItemStack wrench)
	{
		final IBlockState below = world.getBlockState(pos.down());
		if (below.getBlock() instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)below).wrenchBlock(world, pos.down(), player, wrench);
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote) return true;
		final IBlockState belowState = world.getBlockState(pos.down());
		final Block block = belowState.getBlock();
		if (block instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)block).tryWrenchItem(world, pos.down(), state, player);
		}
		return false;
	}

	private void updatePressState(World world, BlockPos pos, IBlockState state)
	{
		final boolean pressed = state.getValue(PRESS_STATE);
		final boolean flag = world.isBlockIndirectlyGettingPowered(pos) > 0;
		if (flag != pressed)
		{
			world.setBlockState(pos, state.withProperty(PRESS_STATE, flag));
			world.playSoundEffect(
				(double)pos.getX() + 0.5D,
				(double)pos.getY() + 0.5D,
				(double)pos.getZ() + 0.5D,
				flag ? "tile.piston.in" : "tile.piston.out",
				0.5F,
				world.rand.nextFloat() * 0.25F + 0.6F);
		}
		world.markBlockForUpdate(pos);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		final IBlockState bottomState = world.getBlockState(pos.down());
		final EnumFacing facing = bottomState.getValue(FACING);
		world.setBlockState(pos, state.withProperty(FACING, facing), BlockFlags.UPDATE_AND_SYNC);
		if (!world.isRemote)
		{
			updatePressState(world, pos, state);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, pos, state, entity, stack);
		final EnumFacing facing = world.getBlockState(pos.down()).getValue(FACING);
		world.setBlockState(pos, state.withProperty(FACING, facing), BlockFlags.SYNC);
		if (!world.isRemote)
		{
			updatePressState(world, pos, state);
		}
	}

	public boolean canBlockStay(World world, BlockPos pos)
	{
		return GrowthCraftCellar.blocks.fruitPress.getBlock().equals(world.getBlockState(pos.down()).getBlock());
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		if (!this.canBlockStay(world, pos))
		{
			world.destroyBlock(pos, true);
		}
		if (!world.isRemote)
		{
			updatePressState(world, pos, state);
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		final IBlockState state = world.getBlockState(pos);
		final EnumFacing facing = state.getValue(FACING);
		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
		{
			return side == EnumFacing.EAST || side == EnumFacing.WEST;
		}
		else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
		{
			return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
		}
		return isNormalCube(world, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return GrowthCraftCellar.blocks.fruitPress.getItem();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
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
}
