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
package growthcraft.milk.client;

import growthcraft.core.client.renderer.block.statemap.GrcDomainStateMapper;
import growthcraft.core.client.util.GrcModelRegistry;
import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.item.EnumButter;
import growthcraft.milk.common.item.EnumCheeseType;
import growthcraft.milk.common.item.EnumIceCream;
import growthcraft.milk.common.item.EnumYogurt;
import growthcraft.milk.GrowthCraftMilk;

public class ClientProxy extends CommonProxy
{
	private void registerBlockStates()
	{
		final GrcModelRegistry gmr = GrcModelRegistry.instance();
		gmr.registerAll(GrowthCraftMilk.blocks.all, 0, GrowthCraftMilk.resources);
		gmr.setCustomStateMapperForAll(GrowthCraftMilk.blocks.all, new GrcDomainStateMapper(GrowthCraftMilk.resources));
		for (EnumButter enumButter : EnumButter.VALUES)
		{
			gmr.register(GrowthCraftMilk.items.butter, enumButter.getMetadata(), GrowthCraftMilk.resources.createModel("butter_" + enumButter.getBasename(), "inventory"));
		}
		for (EnumCheeseType enumCheeseType : EnumCheeseType.VALUES)
		{
			gmr.register(GrowthCraftMilk.items.cheese, enumCheeseType.getMetadata(), GrowthCraftMilk.resources.createModel("cheese_" + enumCheeseType.getBasename(), "inventory"));
		}
		gmr.register(GrowthCraftMilk.items.cheeseCloth, 0, GrowthCraftMilk.resources);
		for (EnumIceCream enumIceCream : EnumIceCream.VALUES)
		{
			gmr.register(GrowthCraftMilk.items.iceCream, enumIceCream.getMetadata(), GrowthCraftMilk.resources.createModel("ice_cream_" + enumIceCream.getBasename(), "inventory"));
		}
		if (GrowthCraftMilk.items.seedThistle != null)
		{
			gmr.register(GrowthCraftMilk.items.seedThistle, 0, GrowthCraftMilk.resources);
		}
		gmr.register(GrowthCraftMilk.items.starterCulture, 0, GrowthCraftMilk.resources);
		gmr.register(GrowthCraftMilk.items.stomach, 0, GrowthCraftMilk.resources);
		for (EnumYogurt enumYogurt : EnumYogurt.VALUES)
		{
			gmr.register(GrowthCraftMilk.items.yogurt, enumYogurt.getMetadata(), GrowthCraftMilk.resources.createModel("yogurt_" + enumYogurt.getBasename(), "inventory"));
		}
	}

	@Override
	public void register()
	{
		super.register();
		registerBlockStates();
	}
}
