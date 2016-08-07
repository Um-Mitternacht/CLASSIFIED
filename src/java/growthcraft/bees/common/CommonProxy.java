package growthcraft.bees.common;

import net.minecraftforge.fml.common.SidedProxy;

public class CommonProxy
{
	@SidedProxy(clientSide="growthcraft.bees.client.ClientProxy", serverSide="growthcraft.bees.common.CommonProxy")
	public static CommonProxy instance;

	public void initSounds() {}

	public void init()
	{
		initSounds();
	}

	public void registerVillagerSkin() {}
}
