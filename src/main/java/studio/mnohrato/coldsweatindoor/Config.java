package studio.mnohrato.coldsweatindoor;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(
	modid = MrozbirekGlueMod.MOD_ID,
	bus = Mod.EventBusSubscriber.Bus.MOD
)
public class Config
{
	private static final ForgeConfigSpec.Builder BUILDER;
	private static final ForgeConfigSpec.DoubleValue TEMP_MODIFIER_EXTRA_SCALER;
	private static final ForgeConfigSpec.IntValue TEMP_MODIFIER_CHECK_DISTANCE;
	private static final ForgeConfigSpec.BooleanValue TEMP_MODIFIER_CHECK_MORE_PRECISELY;
	private static final ForgeConfigSpec SPEC;
	
	static
	{
		BUILDER = new ForgeConfigSpec.Builder( );
		
		BUILDER.push( "TEMP_MODIFIER" );
		{
			TEMP_MODIFIER_EXTRA_SCALER = BUILDER.comment( "Extra scaler from indoorness to temperature offset" )
				.defineInRange( "extra_scaler", 1, Double.MIN_VALUE, Double.MAX_VALUE );
			TEMP_MODIFIER_CHECK_DISTANCE = BUILDER.comment( "The distance of checking surrounding blocks" ).defineInRange( "check_distance", 16, 0, 48 );
			TEMP_MODIFIER_CHECK_MORE_PRECISELY = BUILDER.comment( "Check more precisely" ).define( "check_more_precisely", false );
		}
		BUILDER.pop( );
		
		SPEC = BUILDER.build( );
	}
	
	public static double tempModifierExtraScaler;
	public static int tempModifierCheckDistance;
	public static boolean tempModifierCheckMorePrecisely;
	
	public static ForgeConfigSpec SPEC()
	{
		return SPEC;
	}
	
	@SubscribeEvent
	public static void onLoad( ModConfigEvent event )
	{
		tempModifierExtraScaler = TEMP_MODIFIER_EXTRA_SCALER.get( );
		tempModifierCheckDistance = TEMP_MODIFIER_CHECK_DISTANCE.get( );
		tempModifierCheckMorePrecisely = TEMP_MODIFIER_CHECK_MORE_PRECISELY.get( );
	}
}
