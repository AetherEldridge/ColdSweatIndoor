package studio.mnohrato.mrozbirekgluemod;

import com.mojang.logging.LogUtils;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.api.util.placement.Placement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import studio.mnohrato.mrozbirekgluemod.coldsweat.IndoorTempModifier;

@Mod(
	value = MrozbirekGlueMod.MOD_ID
)
public class MrozbirekGlueMod
{
	public static final String MOD_ID = "mrozbirekgluemod";
	private static final Logger LOGGER = LogUtils.getLogger( );
	
	public MrozbirekGlueMod( @NotNull FMLJavaModLoadingContext ctx )
	{
		LOGGER.info( "Mrozbirek Glue Mod initialization..." );
		IEventBus modEventBus = ctx.getModEventBus( );
		
		MinecraftForge.EVENT_BUS.register( this );
		
		ctx.registerConfig( ModConfig.Type.COMMON, Config.SPEC( ) );
	}
	
	@SubscribeEvent
	public void commonSetup( FMLCommonSetupEvent fmlcs )
	{
	}
	
	@NotNull
	@Contract(
		value = "_ -> new"
	)
	public static ResourceLocation rl( String name )
	{
		return ResourceLocation.fromNamespaceAndPath( MOD_ID, name );
	}
}
