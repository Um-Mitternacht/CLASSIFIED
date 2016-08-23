package growthcraft.core;

public class GrcCoreConfig extends ConfigBase
{
	@ConfigOption(catergory="minecraft/debug", name="Enable Game Registry Dump", desc="Should Growthcraft dump the GameRegistry and FluidRegistry to text files?")
	public boolean dumpGameRegistry;


	@ConfigOption(catergory="salt", name="Bucket Ocean Salt Water", desc="Should we enable the bucket of salt water event handler?")
	public boolean bucketOfOceanSaltWater;


	@ConfigOption(catergory="fluid_container", name="Bottle Capacity", desc="How much booze does a bottle hold?")
	public int bottleCapacity = 333;

	@ConfigOption(catergory="fluid_container", name="Water Bottle Capacity", desc="Change vanilla water bottles to GrowthCraft's capacity?")
	public boolean changeWaterBottleCapacity;

	@ConfigOption(catergory="fluid_container", name="Water Bottle Container", desc="Should vanilla water bottles return the bottle on use?")
	public boolean changeWaterBottleContainer;


	@ConfigOption(catergory="booze/effects", name="Hide Poisoned", desc="Should purposely poisoned booze have its effect hidden?")
	public boolean hidePoisonedBooze = true;


	@ConfigOption(catergory="integration", name="Enable Apple Core Integration", desc="Should we integrate with Apple Core (if available)?")
	public boolean enableAppleCoreIntegration = true;

	@ConfigOption(catergory="integration", name="Enable Thaumcraft Integration", desc="Should we integrate with Thaumcraft (if available)?")
	public boolean enableThaumcraftIntegration = true;

	@ConfigOption(catergory="integration", name="Enable Waila Integration", desc="Should we integrate with Waila (if available)?")
	public boolean enableWailaIntegration = true;
}
