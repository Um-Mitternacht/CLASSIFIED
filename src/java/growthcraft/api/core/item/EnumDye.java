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
package growthcraft.api.core.item;

import com.google.common.base.CaseFormat;

import growthcraft.api.core.definition.IItemStackFactory;

import net.minecraft.block.material.MapColor;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * Sometimes you honestly can't remember them all, but hey, enum!
 */
public enum EnumDye implements IItemStackFactory
{
	BLACK(EnumDyeColor.BLACK),
	RED(EnumDyeColor.RED),
	GREEN(EnumDyeColor.GREEN),
	BROWN(EnumDyeColor.BROWN),
	BLUE(EnumDyeColor.BLUE),
	PURPLE(EnumDyeColor.PURPLE),
	CYAN(EnumDyeColor.CYAN),
	SILVER(EnumDyeColor.SILVER),
	GRAY(EnumDyeColor.GRAY),
	PINK(EnumDyeColor.PINK),
	LIME(EnumDyeColor.LIME),
	YELLOW(EnumDyeColor.YELLOW),
	LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE),
	MAGENTA(EnumDyeColor.MAGENTA),
	ORANGE(EnumDyeColor.ORANGE),
	WHITE(EnumDyeColor.WHITE);

	// Aliases
	public static final EnumDye INK_SAC = BLACK;
	public static final EnumDye CACTUS_GREEN = GREEN;
	public static final EnumDye COCOA_BEANS = BROWN;
	public static final EnumDye LAPIS_LAZULI = BLUE;
	public static final EnumDye BONE_MEAL = WHITE;
	private static final EnumDye[] META_LOOKUP = new EnumDye[values().length];
	private static final EnumDye[] DYE_DMG_LOOKUP = new EnumDye[values().length];

	static
	{
		for (EnumDye dye : values())
		{
			META_LOOKUP[dye.getMetadata()] = dye;
			DYE_DMG_LOOKUP[dye.getDyeDamage()] = dye;
		}
	}

	private final EnumDyeColor baseDye;

	private EnumDye(EnumDyeColor base)
	{
		this.baseDye = base;
	}

	public int getMetadata()
	{
		return baseDye.getMetadata();
	}

	public int getDyeDamage()
	{
		return baseDye.getDyeDamage();
	}

	public String getUnlocalizedName()
	{
		return baseDye.getUnlocalizedName();
	}

	public String toString()
	{
		return baseDye.toString();
	}

	public String getName()
	{
		return baseDye.getName();
	}

	public String getOreName()
	{
		return String.format("dye%s", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, getName()));
	}

	public MapColor getMapColor()
	{
		return baseDye.getMapColor();
	}

	public ItemStack asStack(int size)
	{
		return new ItemStack(Items.dye, size, getDyeDamage());
	}

	public ItemStack asStack()
	{
		return asStack(1);
	}

	public static EnumDye byDyeDamage(int damage)
	{
		if (damage < 0 || damage >= DYE_DMG_LOOKUP.length)
		{
			damage = 0;
		}

		return DYE_DMG_LOOKUP[damage];
	}

	public static EnumDye byMetadata(int meta)
	{
		if (meta < 0 || meta >= META_LOOKUP.length)
		{
			meta = 0;
		}

		return META_LOOKUP[meta];
	}
}
