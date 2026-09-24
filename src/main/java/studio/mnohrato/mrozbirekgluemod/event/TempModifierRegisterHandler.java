package studio.mnohrato.mrozbirekgluemod.event;

import com.momosoftworks.coldsweat.api.event.core.registry.TempModifierRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import studio.mnohrato.mrozbirekgluemod.MrozbirekGlueMod;
import studio.mnohrato.mrozbirekgluemod.coldsweat.IndoorTempModifier;

@Mod.EventBusSubscriber(
	modid = MrozbirekGlueMod.MOD_ID,
	bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class TempModifierRegisterHandler
{
	@SubscribeEvent
	public static void onRegisterModifiers( @NotNull TempModifierRegisterEvent event )
	{
		event.register( MrozbirekGlueMod.rl( "indoor" ), IndoorTempModifier::new );
	}
}
