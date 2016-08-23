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
package growthcraft.core.client;

import growthcraft.api.core.item.EnumDye;
import growthcraft.core.client.renderer.block.statemap.GrcDomainStateMapper;
import growthcraft.core.client.util.GrcModelRegistry;
import growthcraft.core.common.CommonProxy;
import growthcraft.core.GrowthCraftCore;

public class ClientProxy extends CommonProxy
{
	private void registerBlockStates()
	{
		final GrcModelRegistry gmr = GrcModelRegistry.instance();
		gmr.registerAll(GrowthCraftCore.blocks.all, 0, GrowthCraftCore.resources);
		gmr.setCustomStateMapperForAll(GrowthCraftCore.blocks.all, new GrcDomainStateMapper(GrowthCraftCore.resources));
		if (GrowthCraftCore.items.crowbar != null)
		{
			for (EnumDye dye : EnumDye.values())
			{
				gmr.register(GrowthCraftCore.items.crowbar, dye.getDyeDamage(), GrowthCraftCore.resources.createModel("crowbar_" + dye.getName(), "inventory"));
			}
		}
		gmr.register(GrowthCraftCore.items.rope, 0, GrowthCraftCore.resources);
		gmr.register(GrowthCraftCore.items.salt, 0, GrowthCraftCore.resources);
		gmr.register(GrowthCraftCore.items.saltBottle, 0, GrowthCraftCore.resources);
		gmr.register(GrowthCraftCore.items.saltBucket, 0, GrowthCraftCore.resources);
	}

	@Override
	public void register()
	{
		super.register();
		registerBlockStates();
	}
}
