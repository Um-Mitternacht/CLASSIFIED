/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package growthcraft.rice;

//import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.api.core.util.DomainResourceLocationFactory;
import growthcraft.core.eventhandler.PlayerInteractEventPaddy;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.util.MapGenHelper;
import growthcraft.rice.common.CommonProxy;
import growthcraft.rice.common.village.ComponentVillageRiceField;
import growthcraft.rice.common.village.VillageHandlerRice;
import growthcraft.rice.init.GrcRiceBlocks;
import growthcraft.rice.init.GrcRiceFluids;
import growthcraft.rice.init.GrcRiceItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(
	modid = GrowthCraftRice.MOD_ID,
	name = GrowthCraftRice.MOD_NAME,
	version = GrowthCraftRice.MOD_VERSION,
	acceptedMinecraftVersions = GrowthCraftCore.MOD_ACC_MINECRAFT,
	dependencies = "required-after:Growthcraft@@VERSION@;required-after:Growthcraft|Cellar@@VERSION@"
)
public class GrowthCraftRice
{
	public static final String MOD_ID = "Growthcraft|Rice";
	public static final String MOD_NAME = "Growthcraft Rice";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(MOD_ID)
	public static GrowthCraftRice instance;
	public static DomainResourceLocationFactory resources = new DomainResourceLocationFactory("grcrice");
	public static final GrcRiceBlocks blocks = new GrcRiceBlocks();
	public static final GrcRiceItems items = new GrcRiceItems();
	public static final GrcRiceFluids fluids = new GrcRiceFluids();

	private final ILogger logger = new GrcLogger(MOD_ID);
	private final GrcRiceConfig config = new GrcRiceConfig();
	private final ModuleContainer modules = new ModuleContainer();

	public static GrcRiceConfig getConfig()
	{
		return instance.config;
	}

	public static ILogger getLogger()
	{
		return instance.logger;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config.setLogger(logger);
		config.load(event.getModConfigurationDirectory(), "growthcraft/rice.conf");
		if (config.debugEnabled) modules.setLogger(logger);
		modules.add(blocks);
		modules.add(items);
		modules.add(fluids);
		if (config.enableThaumcraftIntegration) modules.add(new growthcraft.rice.integration.ThaumcraftModule());
		modules.add(CommonProxy.instance);
		modules.freeze();
		modules.preInit();
		register();
	}

	private void register()
	{
		modules.register();
		MinecraftForge.addGrassSeed(items.rice.asStack(), config.riceSeedDropRarity);
		MapGenHelper.registerStructureComponent(ComponentVillageRiceField.class, "grc.rice_field");

		//====================
		// CRAFTING
		//====================
		GameRegistry.addRecipe(new ShapedOreRecipe(items.riceBall.asStack(1), "###", "###", '#', "cropRice"));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PlayerInteractEventPaddy.paddyBlocks.put(Blocks.farmland, blocks.paddyField.getBlock());
		final VillageHandlerRice handler = new VillageHandlerRice();
		//VillagerRegistry.instance().registerVillageTradeHandler(GrowthCraftCellar.getConfig().villagerBrewerID, handler);
		VillagerRegistry.instance().registerVillageCreationHandler(handler);
		modules.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		modules.postInit();
	}
}
