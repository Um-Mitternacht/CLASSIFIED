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
package growthcraft.hops;

import growthcraft.api.core.CoreRegistry;
import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.api.core.util.DomainResourceLocationFactory;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.util.MapGenHelper;
import growthcraft.hops.common.CommonProxy;
import growthcraft.hops.common.village.ComponentVillageHopVineyard;
import growthcraft.hops.common.village.VillageHandlerHops;
import growthcraft.hops.init.GrcHopsBlocks;
import growthcraft.hops.init.GrcHopsFluids;
import growthcraft.hops.init.GrcHopsItems;

import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

@Mod(
	modid = GrowthCraftHops.MOD_ID,
	name = GrowthCraftHops.MOD_NAME,
	version = GrowthCraftHops.MOD_VERSION,
	acceptedMinecraftVersions = GrowthCraftCore.MOD_ACC_MINECRAFT,
	dependencies = "required-after:Growthcraft@@VERSION@;required-after:Growthcraft|Cellar@@VERSION@"
)
public class GrowthCraftHops
{
	public static final String MOD_ID = "Growthcraft|Hops";
	public static final String MOD_NAME = "Growthcraft Hops";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(MOD_ID)
	public static GrowthCraftHops instance;
	public static DomainResourceLocationFactory resources = new DomainResourceLocationFactory("grchops");
	public static GrcHopsBlocks blocks = new GrcHopsBlocks();
	public static GrcHopsItems items = new GrcHopsItems();
	public static GrcHopsFluids fluids = new GrcHopsFluids();

	private ILogger logger = new GrcLogger(MOD_ID);
	private GrcHopsConfig config = new GrcHopsConfig();
	private ModuleContainer modules = new ModuleContainer();

	public static GrcHopsConfig getConfig()
	{
		return instance.config;
	}

	public static ILogger getLogger()
	{
		return instance.logger;
	}

	@EventHandler
	public void preload(FMLPreInitializationEvent event)
	{
		config.setLogger(logger);
		config.load(event.getModConfigurationDirectory(), "growthcraft/hops.conf");
		if (config.debugEnabled) modules.setLogger(logger);
		modules.add(blocks);
		modules.add(items);
		modules.add(fluids);
		if (config.enableThaumcraftIntegration) modules.add(new growthcraft.hops.integration.ThaumcraftModule());
		modules.add(CommonProxy.instance);
		modules.freeze();
		modules.preInit();
		register();
	}

	private void register()
	{
		modules.register();
		CoreRegistry.instance().vineDrops().addDropEntry(items.hops.asStack(2), config.hopsVineDropRarity);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(items.hops.asStack(), 1, 2, 10));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(items.hops.asStack(), 1, 2, 10));
		MapGenHelper.registerStructureComponent(ComponentVillageHopVineyard.class, "grc.hop_vineyard");
		GameRegistry.addShapelessRecipe(items.hopSeeds.asStack(), items.hops.getItem());
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		final VillageHandlerHops handler = new VillageHandlerHops();
		logger.warn("(fixme) GrowthCraftHops registerVillageTradeHandler");
		//VillagerRegistry.instance().registerVillageTradeHandler(GrowthCraftCellar.getConfig().villagerBrewerID, handler);
		VillagerRegistry.instance().registerVillageCreationHandler(handler);
		modules.init();
	}

	@EventHandler
	public void postload(FMLPostInitializationEvent event)
	{
		modules.postInit();
	}
}
