package studio.mnohrato.coldsweatindoor;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(
	value = MrozbirekGlueMod.MOD_ID
)
public class MrozbirekGlueMod
{
	public static final String MOD_ID = "coldsweatindoor";
	private static final Logger LOGGER = LogUtils.getLogger( );
	
	public MrozbirekGlueMod( @NotNull FMLJavaModLoadingContext ctx )
	{
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
