/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package growthcraft.bamboo.integration;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.integration.ThaumcraftModuleBase;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.Aspect;

import net.minecraftforge.fml.common.Optional;

public class ThaumcraftModule extends ThaumcraftModuleBase
{
	public ThaumcraftModule()
	{
		super(GrowthCraftBamboo.MOD_ID);
	}

	@Override
	@Optional.Method(modid="Thaumcraft")
	protected void integrate()
	{
		GrowthCraftBamboo.getLogger().warn("(fixme) bamboo/ThaumcraftModule#integrate");
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooBlock.asStack(), new AspectList().add(Aspect.TREE, 4));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooFence.asStack(), new AspectList().add(Aspect.TREE, 2));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooFenceGate.asStack(), new AspectList().add(Aspect.TREE, 2).add(Aspect.MECHANISM, 1).add(Aspect.MOTION, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooLeaves.asStack(), new AspectList().add(Aspect.PLANT, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooShoot.asStack(), new AspectList().add(Aspect.TREE, 1).add(Aspect.PLANT, 2).add(Aspect.CROP, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooSingleSlab.asStack(), new AspectList().add(Aspect.TREE, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooStairs.asStack(), new AspectList().add(Aspect.TREE, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooWall.asStack(), new AspectList().add(Aspect.TREE, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooScaffold.asStack(), new AspectList().add(Aspect.TREE, 4).add(Aspect.MECHANISM, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.blocks.bambooDoor.asStack(), new AspectList().add(Aspect.TREE, 4).add(Aspect.MECHANISM, 1).add(Aspect.MOTION, 1));
		////
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.items.bamboo.asStack(), new AspectList().add(Aspect.TREE, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.items.bambooDoorItem.asStack(), new AspectList().add(Aspect.TREE, 1).add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.items.bambooRaft.asStack(), new AspectList().add(Aspect.TREE, 3).add(Aspect.WATER, 4).add(Aspect.TRAVEL, 4));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.items.bambooCoal.asStack(), new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENERGY, 1));
		//ThaumcraftApi.registerObjectTag(GrowthCraftBamboo.items.bambooShootFood.asStack(), new AspectList().add(Aspect.PLANT, 1));
	}
}
