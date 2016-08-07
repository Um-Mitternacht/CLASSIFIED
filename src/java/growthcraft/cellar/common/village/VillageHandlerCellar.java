package growthcraft.cellar.common.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageHandlerCellar implements IVillageCreationHandler
{
	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i)
	{
		return new PieceWeight(ComponentVillageTavern.class, 18, MathHelper.getRandomIntegerInRange(random, 0 + i, 2 + i));
	}

	@Override
	public Class<?> getComponentClass()
	{
		return ComponentVillageTavern.class;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Village buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5)
	{
		return ComponentVillageTavern.buildComponent(startPiece, pieces, random, p1, p2, p3, facing, p5);
	}
}
