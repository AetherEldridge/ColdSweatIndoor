package studio.mnohrato.coldsweatindoor.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class IndoorTempModifier extends TempModifier
{
	@Override
	protected Function< Double, Double > calculate( LivingEntity entity, Temperature.Trait trait )
	{
		return ( temp ) -> temp - ( temp - Temperature.getNeutralWorldTemp( entity ) ) * indoorness( entity );
	}
	
	public static double indoorness( @NotNull LivingEntity entity )
	{
		ServerLevel level = ( ServerLevel )entity.level( );
		BlockPos pos = entity.blockPosition( );
		
		double indoorness = 0.5;
		
		// todo
		
		return Mth.clamp( indoorness, 0d, 1d );
	}
}