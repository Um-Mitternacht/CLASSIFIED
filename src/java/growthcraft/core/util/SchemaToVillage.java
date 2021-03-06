/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.core.util;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.World;

/**
 * Utility class for drawing Schema String Arrays as structures
 */
public class SchemaToVillage
{
	private SchemaToVillage() {}

	public static interface IVillage
	{
		public void placeBlockAtCurrentPositionPub(World world, BlockPos pos, IBlockState block, StructureBoundingBox box);
	}

	public static interface IBlockEntries
	{
		public BlockEntry getBlockEntry(Random random);
	}

	public static class BlockEntry implements IBlockEntries
	{
		private IBlockState state;

		public BlockEntry(IBlockState st)
		{
			this.state = st;
		}

		public BlockEntry getBlockEntry(Random random)
		{
			return this;
		}

		public IBlockState getBlockState()
		{
			return this.state;
		}
	}

	public static class MultiBlockEntries implements IBlockEntries
	{
		private BlockEntry[] entries;

		public MultiBlockEntries(BlockEntry[] blockEntries)
		{
			this.entries = blockEntries;
		}

		public BlockEntry getBlockEntry(Random random)
		{
			if (this.entries.length == 0)
			{
				return null;
			}
			return entries[random.nextInt(this.entries.length)];
		}
	}

	public static void drawSchema(IVillage village, World world, Random random, StructureBoundingBox box, String[][] schema, Map<Character, IBlockEntries> map, int offx, int offy, int offz)
	{
		// loop by schema layer
		for (int y = 0; y < schema.length; ++y)
		{
			final String[] layer = schema[y];
			// then loop by schema layer-row
			for (int z = 0; z < layer.length; ++z)
			{
				final String row = layer[z];
				// finally loop by schema layer-row-cell
				for (int x = 0; x < row.length(); ++x)
				{
					IBlockState state = null;
					final IBlockEntries entries = map.get(row.charAt(x));
					// look out for null entries, though by right we should
					// warn about these.
					if (entries != null)
					{
						final BlockEntry entry = entries.getBlockEntry(random);
						// a null entry is possible, for "Ignore the this block"
						if (entry != null)
						{
							state = entry.getBlockState();
						}
					}
					// null blocks are not placed
					if (state != null) {
						village.placeBlockAtCurrentPositionPub(world, new BlockPos(offx + x, offy + y, offz + z), state, box);
					}
				}
			}
		}
	}

	public static void drawSchema(IVillage village, World world, Random random, StructureBoundingBox box, String[][] schema, Map<Character, IBlockEntries> map)
	{
		drawSchema(village, world, random, box, schema, map, 0, 0, 0);
	}
}
