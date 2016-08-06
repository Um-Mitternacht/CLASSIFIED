package growthcraft.cellar.common.block;

import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.cellar.util.CellarGuiType;
import growthcraft.api.core.util.BBox;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCultureJar extends BlockCellarContainer
{
	public BlockCultureJar()
	{
		super(Material.glass);
		setHardness(0.3F);
		setStepSound(soundTypeGlass);
		setUnlocalizedName("grc.culture_jar");
		setCreativeTab(GrowthCraftCellar.tab);
		setTileEntityType(TileEntityCultureJar.class);
		setGuiType(CellarGuiType.FERMENT_JAR);

		final BBox bbox = BBox.newCube(6, 0, 6, 4, 6, 4).scale(1 / 16.0f);
		setBlockBounds(bbox.x0(), bbox.y0(), bbox.z0(), bbox.x1(), bbox.y1(), bbox.z1());
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
