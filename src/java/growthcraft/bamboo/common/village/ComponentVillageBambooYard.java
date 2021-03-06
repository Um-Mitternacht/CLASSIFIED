package growthcraft.bamboo.common.village;

import java.util.List;
import java.util.Random;
import java.util.HashMap;

import growthcraft.bamboo.common.world.WorldGenBamboo;
import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.util.SchemaToVillage.BlockEntry;
import growthcraft.core.util.SchemaToVillage.IBlockEntries;
import growthcraft.core.util.SchemaToVillage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentVillageBambooYard extends StructureVillagePieces.Village implements SchemaToVillage.IVillage
{
	// Design by Ar97x
	private static final String[][] bambooYardSchema = {
		{
			// y: -1
			"           ",
			"           ",
			"           ",
			"  p     p  ",
			"     ~     ",
			"    ~~~    ",
			"   ~~~~~   ",
			"    ~~~    ",
			"     ~     ",
			"  p     p  ",
			"           ",
			"           "
		},
		{
			// y: 0
			"    pDp    ",
			"ppppp ppppp",
			"p         p",
			"p t     t p",
			"p    s    p",
			"p   s s   p",
			"p  s   s  p",
			"p   s s   p",
			"p    s    p",
			"p t     t p",
			"p         p",
			"ppppppppppp"
		},
		{
			// y: 1
			"    WdW    ",
			"W   W W   W",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"W         W"
		},
		{
			// y: 2
			"    WWW    ",
			"WWWWW WWWWW",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"WWWWWWWWWWW"
		},
	};

	// DO NOT REMOVE
	public ComponentVillageBambooYard() {}

	public ComponentVillageBambooYard(Start startPiece, EnumFacing facing, Random random, StructureBoundingBox boundingBox, int type)
	{
		super(startPiece, type);
		this.coordBaseMode = facing;
		this.boundingBox = boundingBox;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static ComponentVillageBambooYard buildComponent(Start startPiece, List list, Random random, int x, int y, int z, EnumFacing facing, int type)
	{
		// the height of the structure is 15 blocks, since the maximum height of bamboo is 12~14 blocks (+1 for the water layer)
		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 11, 16, 12, facing);
		if (canVillageGoDeeper(structureboundingbox)) {
			if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
				return new ComponentVillageBambooYard(startPiece, facing, random, structureboundingbox, type);
			}
		}
		return null;
	}

	@Override
	public void placeBlockAtCurrentPositionPub(World world, BlockPos pos, IBlockState state, StructureBoundingBox box)
	{
		setBlockState(world, state, pos.getX(), pos.getY(), pos.getZ(), box);
	}

	protected void placeWorldGenAt(World world, Random random, int tx, int ty, int tz, StructureBoundingBox bb, WorldGenerator generator)
	{
		final BlockPos pos = new BlockPos(this.getXWithOffset(tx, tz), this.getYWithOffset(ty), this.getZWithOffset(tx, tz));
		if (bb.isVecInside(pos))
		{
			generator.generate(world, random, pos);
		}
	}

	public boolean addComponentParts(World world, Random random, StructureBoundingBox box)
	{
		if (this.field_143015_k < 0)
		{
			this.field_143015_k = this.getAverageGroundLevel(world, box);
			if (this.field_143015_k < 0)
			{
				return true;
			}
			// the structure is 1 block lower due to the water layer
			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 14, 0);
		}
		// clear entire bounding box
		this.fillWithBlocks(world, box, 0, 0, 0, 11, 4, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
		this.fillWithBlocks(world, box, 0, 0, 0, 11, 0, 12, Blocks.grass.getDefaultState(), Blocks.grass.getDefaultState(), false);
		final HashMap<Character, IBlockEntries> map = new HashMap<Character, IBlockEntries>();
		// okay folks, no BIG D jokes here
		map.put('D', new BlockEntry(GrowthCraftBamboo.blocks.bambooDoor.getBlock().getDefaultState()));
		// top of the door brought forward
		map.put('d', new BlockEntry(GrowthCraftBamboo.blocks.bambooDoor.getBlock().getDefaultState()));
		map.put('p', new BlockEntry(GrowthCraftBamboo.blocks.bambooBlock.getBlock().getDefaultState()));
		map.put('s', new BlockEntry(GrowthCraftBamboo.blocks.bambooSingleSlab.getBlock().getDefaultState()));
		map.put('t', new BlockEntry(Blocks.torch.getDefaultState()));
		map.put('~', new BlockEntry(Blocks.water.getDefaultState()));
		map.put('W', new BlockEntry(GrowthCraftBamboo.blocks.bambooWall.getBlock().getDefaultState()));
		SchemaToVillage.drawSchema(this, world, random, box, bambooYardSchema, map);
		// This places the bamboo trees to the best of its ability.
		final WorldGenBamboo genBamboo = new WorldGenBamboo(true);
		placeWorldGenAt(world, random, 4, 1, 4, box, genBamboo);
		placeWorldGenAt(world, random, 6, 1, 4, box, genBamboo);
		placeWorldGenAt(world, random, 3, 1, 5, box, genBamboo);
		placeWorldGenAt(world, random, 7, 1, 5, box, genBamboo);
		placeWorldGenAt(world, random, 3, 1, 7, box, genBamboo);
		placeWorldGenAt(world, random, 7, 1, 7, box, genBamboo);
		placeWorldGenAt(world, random, 4, 1, 8, box, genBamboo);
		placeWorldGenAt(world, random, 6, 1, 8, box, genBamboo);
		for (int row = 0; row < 12; ++row)
		{
			for (int col = 0; col < 11; ++col)
			{
				clearCurrentPositionBlocksUpwards(world, col, 16, row, box);
				replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), col, -1, row, box);
			}
		}
		return true;
	}
}
