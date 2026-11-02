package studio.mnohrato.coldsweatindoor.event;

import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.api.util.placement.Placement;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import studio.mnohrato.coldsweatindoor.ColdSweatIndoor;
import studio.mnohrato.coldsweatindoor.coldsweat.IndoorTempModifier;

@Mod.EventBusSubscriber(
	modid = ColdSweatIndoor.MOD_ID,
	bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class AddTempModifierHandler
{
	@SubscribeEvent
	public static void onEntityJoin( @NotNull EntityJoinLevelEvent event )
	{
		if( event.getEntity( ) instanceof LivingEntity living && !event.getLevel( ).isClientSide )
		{
			if( Temperature.getModifier( living, Temperature.Trait.WORLD, IndoorTempModifier.class ).isPresent( ) ) return;
			
			Temperature.addModifier( living, new IndoorTempModifier( ), Temperature.Trait.WORLD, Placement.LAST );
		}
	}
}