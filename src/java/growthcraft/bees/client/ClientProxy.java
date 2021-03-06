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
package growthcraft.bees.client;

import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.item.EnumBeesWax;
import growthcraft.bees.GrowthCraftBees;
import growthcraft.core.client.renderer.block.statemap.GrcDomainStateMapper;
import growthcraft.core.client.util.GrcModelRegistry;

public class ClientProxy extends CommonProxy
{
	private void registerBlockStates()
	{
		final GrcModelRegistry gmr = GrcModelRegistry.instance();
		gmr.registerAll(GrowthCraftBees.blocks.all, 0, GrowthCraftBees.resources);
		gmr.setCustomStateMapperForAll(GrowthCraftBees.blocks.all, new GrcDomainStateMapper(GrowthCraftBees.resources));
		gmr.register(GrowthCraftBees.items.bee, 0, GrowthCraftBees.resources);
		gmr.register(GrowthCraftBees.items.honeyCombEmpty, 0, GrowthCraftBees.resources);
		gmr.register(GrowthCraftBees.items.honeyCombFilled, 0, GrowthCraftBees.resources);
		gmr.register(GrowthCraftBees.items.honeyJar, 0, GrowthCraftBees.resources);
		for (EnumBeesWax wax : EnumBeesWax.VALUES)
		{
			gmr.register(GrowthCraftBees.items.beesWax, wax.getMetadata(), GrowthCraftBees.resources.createModel("beeswax_" + wax.getBasename(), "inventory"));
		}
	}

	@Override
	public void register()
	{
		super.register();
		registerBlockStates();
	}
}
