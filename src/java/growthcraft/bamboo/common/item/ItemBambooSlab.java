package growthcraft.bamboo.common.item;

import growthcraft.bamboo.GrowthCraftBamboo;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemBambooSlab extends ItemSlab
{
	public ItemBambooSlab(Block block)
	{
		super(block,
			GrowthCraftBamboo.blocks.bambooSingleSlab.getBlock(),
			GrowthCraftBamboo.blocks.bambooDoubleSlab.getBlock()
		);
		setUnlocalizedName("bamboo_slab");
	}
}
