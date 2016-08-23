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
package growthcraft.cellar.client;

import growthcraft.api.core.item.EnumDye;
import growthcraft.cellar.client.resource.GrcCellarResources;
import growthcraft.cellar.common.CommonProxy;
import growthcraft.cellar.common.item.EnumYeast;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.client.renderer.block.statemap.GrcDomainStateMapper;
import growthcraft.core.client.util.GrcModelRegistry;

public class ClientProxy extends CommonProxy
{
	private void registerBlockStates()
	{
		final GrcModelRegistry gmr = GrcModelRegistry.instance();
		gmr.registerAll(GrowthCraftCellar.blocks.all, 0, GrowthCraftCellar.resources);
		gmr.setCustomStateMapperForAll(GrowthCraftCellar.blocks.all, new GrcDomainStateMapper(GrowthCraftCellar.resources));

		for (EnumYeast yeast : EnumYeast.values())
		{
			gmr.register(GrowthCraftCellar.items.yeast, yeast.getMetadata(), GrowthCraftCellar.resources.createModel("yeast_" + yeast.getBasename(), "inventory"));
		}
		for (EnumDye dye : EnumDye.values())
		{
			gmr.register(GrowthCraftCellar.items.waterBag, dye.getDyeDamage(), GrowthCraftCellar.resources.createModel("water_bag_" + dye.getName(), "inventory"));
		}
		gmr.register(GrowthCraftCellar.items.waterBag, 16, GrowthCraftCellar.resources.createModel("water_bag_default", "inventory"));
		gmr.register(GrowthCraftCellar.items.chievItemDummy, 0, GrowthCraftCellar.resources);
	}

	@Override
	public void register()
	{
		super.register();
		registerBlockStates();
	}

	@Override
	public void init()
	{
		super.init();
		new GrcCellarResources();
	}
}
