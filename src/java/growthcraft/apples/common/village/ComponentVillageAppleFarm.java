package growthcraft.apples.common.village;

import java.util.List;
import java.util.Random;
import java.util.HashMap;

import growthcraft.apples.common.world.WorldGenAppleTree;
import growthcraft.core.util.SchemaToVillage.BlockEntry;
import growthcraft.core.util.SchemaToVillage.IBlockEntries;
import growthcraft.core.util.SchemaToVillage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.World;

public class ComponentVillageAppleFarm extends StructureVillagePieces.Village implements SchemaToVillage.IVillage
{
	// Design by Ar97x
	private static final String[][] appleFarmSchema = {
		{
			"x---x x---x",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"|         |",
			"x---------x"
		},
		{
			"fffffgfffff",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"f         f",
			"fffffffffff"
		},
		{
			"t   t t   t",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"t         t"
		},
	};

	// DO NOT REMOVE
	public ComponentVillageAppleFarm() {}

	public ComponentVillageAppleFarm(Start startPiece, EnumFacing facing, Random random, StructureBoundingBox boundingBox, int type)
	{
		super(startPiece, type);
		this.coordBaseMode = facing;
		this.boundingBox = boundingBox;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static ComponentVillageAppleFarm buildComponent(Start startPiece, List list, Random random, int x, int y, int z, EnumFacing facing, int type)
	{
		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 11, 11, 11, facing);
		if (canVillageGoDeeper(structureboundingbox))
		{
			if (StructureComponent.findIntersecting(list, structureboundingbox) == null)
			{
				return new ComponentVillageAppleFarm(startPiece, facing, random, structureboundingbox, type);
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
		if (field_143015_k < 0)
		{
			this.field_143015_k = this.getAverageGroundLevel(world, box);
			if (field_143015_k < 0)
			{
				return true;
			}
			boundingBox.offset(0, field_143015_k - boundingBox.maxY + 9, 0);
		}
		// clear entire bounding box
		fillWithBlocks(world, box, 0, 0, 0, 11, 4, 11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
		// Fill floor with grass blocks
		fillWithBlocks(world, box, 0, 0, 0, 11, 0, 11, Blocks.grass.getDefaultState(), Blocks.grass.getDefaultState(), false);
		final HashMap<Character, IBlockEntries> map = new HashMap<Character, IBlockEntries>();
		map.put('x', new BlockEntry(Blocks.log.getDefaultState()));
		map.put('-', new BlockEntry(Blocks.log.getDefaultState()));
		map.put('|', new BlockEntry(Blocks.log.getDefaultState()));
		map.put('f', new BlockEntry(Blocks.oak_fence.getDefaultState()));
		map.put('g', new BlockEntry(Blocks.oak_fence_gate.getDefaultState()));
		map.put('t', new BlockEntry(Blocks.torch.getDefaultState()));
		SchemaToVillage.drawSchema(this, world, random, box, appleFarmSchema, map, 0, 1, 0);
		final WorldGenAppleTree genAppleTree = new WorldGenAppleTree(true);
		placeWorldGenAt(world, random, 3, 1, 3, box, genAppleTree);
		placeWorldGenAt(world, random, 7, 1, 3, box, genAppleTree);
		placeWorldGenAt(world, random, 3, 1, 7, box, genAppleTree);
		placeWorldGenAt(world, random, 7, 1, 7, box, genAppleTree);
		for (int row = 0; row < 11; ++row)
		{
			for (int col = 0; col < 11; ++col)
			{
				clearCurrentPositionBlocksUpwards(world, col, 7, row, box);
				replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), col, -1, row, box);
			}
		}
		return true;
	}
}
