package studio.mnohrato.coldsweatindoor;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(
	modid = ColdSweatIndoor.MOD_ID,
	bus = Mod.EventBusSubscriber.Bus.MOD
)
public class Config
{
	private static final ForgeConfigSpec.Builder BUILDER;
	private static final ForgeConfigSpec.DoubleValue MAX_DISTANCE;
	private static final ForgeConfigSpec.DoubleValue STEP;
	private static final ForgeConfigSpec.IntValue UPDATE_INTERVAL;
	
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_LEAVES;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_IMPERMEABLE;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_SOLID_RENDER;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_CAN_OCCLUDE;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_FULL_COLLISION;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_BLOCKS_MOTION;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_FLUID;
	private static final ForgeConfigSpec.DoubleValue SOLIDITY_DEFAULT;
	
	private static final ForgeConfigSpec SPEC;
	
	static
	{
		BUILDER = new ForgeConfigSpec.Builder( );
		MAX_DISTANCE = BUILDER
			.comment( "How far, in blocks, a sample ray looks for cover. Beyond this the world is treated as open." )
			.defineInRange( "max_distance", 14d, 0, Double.MAX_VALUE );
		STEP = BUILDER.comment( "Distance between two samples along a ray." ).defineInRange( "distance", 0.75d, 0, Double.MAX_VALUE );
		UPDATE_INTERVAL = BUILDER
			.comment( "How often the (relatively expensive) scan is recalculated, in ticks." )
			.defineInRange( "update_interval", 2, 0, Integer.MAX_VALUE );
		
		BUILDER.comment( "Solidity values used when estimating how much a block blocks the view. Range [0, 1]." ).push( "solidity" );
		SOLIDITY_LEAVES = BUILDER
			.comment( "Solidity of blocks in the LEAVES tag." )
			.defineInRange( "leaves", 0.35d, 0d, 1d );
		SOLIDITY_IMPERMEABLE = BUILDER
			.comment( "Solidity of impermeable blocks, half-transparent blocks (e.g. stained glass) and iron bars." )
			.defineInRange( "impermeable", 0.6d, 0d, 1d );
		SOLIDITY_SOLID_RENDER = BUILDER
			.comment( "Solidity of blocks that render as fully solid." )
			.defineInRange( "solid_render", 1.0d, 0d, 1d );
		SOLIDITY_CAN_OCCLUDE = BUILDER
			.comment( "Solidity of blocks that can occlude (but are not fully solid-rendered)." )
			.defineInRange( "can_occlude", 0.75d, 0d, 1d );
		SOLIDITY_FULL_COLLISION = BUILDER
			.comment( "Solidity of blocks with a full-block collision shape." )
			.defineInRange( "full_collision", 0.65d, 0d, 1d );
		SOLIDITY_BLOCKS_MOTION = BUILDER
			.comment( "Solidity of blocks that block motion." )
			.defineInRange( "blocks_motion", 0.45d, 0d, 1d );
		SOLIDITY_FLUID = BUILDER
			.comment( "Solidity of blocks containing a non-empty fluid." )
			.defineInRange( "fluid", 0.12d, 0d, 1d );
		SOLIDITY_DEFAULT = BUILDER
			.comment( "Solidity of any other non-air block." )
			.defineInRange( "default", 0.0d, 0d, 1d );
		BUILDER.pop( );
		
		SPEC = BUILDER.build( );
	}
	
	public static double maxDistance = 14d;
	public static double step = 0.75d;
	public static int updateInterval = 2;
	
	public static double solidityLeaves = 0.35d;
	public static double solidityImpermeable = 0.6d;
	public static double soliditySolidRender = 1.0d;
	public static double solidityCanOcclude = 0.75d;
	public static double solidityFullCollision = 0.65d;
	public static double solidityBlocksMotion = 0.45d;
	public static double solidityFluid = 0.12d;
	public static double solidityDefault = 0.0d;
	
	public static ForgeConfigSpec SPEC( )
	{
		return SPEC;
	}
	
	@SubscribeEvent
	public static void onLoad( ModConfigEvent event )
	{
		maxDistance = MAX_DISTANCE.get( );
		step = STEP.get( );
		updateInterval = UPDATE_INTERVAL.get( );
		
		solidityLeaves = SOLIDITY_LEAVES.get( );
		solidityImpermeable = SOLIDITY_IMPERMEABLE.get( );
		soliditySolidRender = SOLIDITY_SOLID_RENDER.get( );
		solidityCanOcclude = SOLIDITY_CAN_OCCLUDE.get( );
		solidityFullCollision = SOLIDITY_FULL_COLLISION.get( );
		solidityBlocksMotion = SOLIDITY_BLOCKS_MOTION.get( );
		solidityFluid = SOLIDITY_FLUID.get( );
		solidityDefault = SOLIDITY_DEFAULT.get( );
	}
}