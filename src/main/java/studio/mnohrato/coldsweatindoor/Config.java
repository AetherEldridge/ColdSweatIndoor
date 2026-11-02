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
	private static final ForgeConfigSpec SPEC;
	
	static
	{
		BUILDER = new ForgeConfigSpec.Builder( );
		
		// todo
		
		SPEC = BUILDER.build( );
	}
	
	public static ForgeConfigSpec SPEC()
	{
		return SPEC;
	}
	
	@SubscribeEvent
	public static void onLoad( ModConfigEvent event )
	{
	}
}
