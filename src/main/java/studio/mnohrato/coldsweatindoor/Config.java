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
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder( );
	private static final ForgeConfigSpec.DoubleValue MAX_DISTANCE = BUILDER
		.comment( "How far, in blocks, a sample ray looks for cover. Beyond this the world is treated as open." )
		.defineInRange( "max_distance", 14d, 0, Double.MAX_VALUE );
	private static final ForgeConfigSpec.DoubleValue STEP = BUILDER.comment( "Distance between two samples along a ray." ).defineInRange( "distance", 0.75d, 0, Double.MAX_VALUE );
	private static final ForgeConfigSpec.IntValue UPDATE_INTERVAL = BUILDER
		.comment( "How often the (relatively expensive) scan is recalculated, in ticks." )
		.defineInRange( "update_interval", 2, 0, Integer.MAX_VALUE );
	private static final ForgeConfigSpec SPEC = BUILDER.build( );
	
	public static double maxDistance = 14d;
	public static double step = 0.75d;
	public static int updateInterval = 2;
	
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
	}
}
