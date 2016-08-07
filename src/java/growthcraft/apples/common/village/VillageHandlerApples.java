package growthcraft.apples.common.village;

import java.util.Random;
import java.util.List;

import growthcraft.apples.GrowthCraftApples;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageHandlerApples implements IVillageCreationHandler
{
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
	{
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1 + random.nextInt(2)), GrowthCraftApples.fluids.appleCider.asStack(1, 1)));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(2)), GrowthCraftApples.fluids.appleCider.asStack(1, 2)));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(2)), GrowthCraftApples.fluids.appleCider.asStack(1, 3)));
	}

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i)
	{
		int num = MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i);
		if (!GrowthCraftApples.getConfig().generateAppleFarms)
			num = 0;

		return new PieceWeight(ComponentVillageAppleFarm.class, 21, num);
	}

	@Override
	public Class<?> getComponentClass()
	{
		return ComponentVillageAppleFarm.class;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5)
	{
		return ComponentVillageAppleFarm.buildComponent(startPiece, pieces, random, p1, p2, p3, facing, p5);
	}
}
