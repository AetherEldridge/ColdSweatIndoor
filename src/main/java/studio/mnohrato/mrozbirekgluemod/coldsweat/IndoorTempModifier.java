package studio.mnohrato.mrozbirekgluemod.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import studio.mnohrato.mrozbirekgluemod.Config;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class IndoorTempModifier extends TempModifier
{
	public static final int[] T_26 = { -1, 0, 1 };
	public static final int[] P_98 = { -2, -1, 0, 1, 2 };
	public static final ArrayList< Vector3f > DIRECTION_26 = new ArrayList<>( );
	public static final ArrayList< Vector3f > DIRECTION_98 = new ArrayList<>( );
	
	static
	{
		for( int i : T_26 ) for( int j : T_26 ) for( int k : T_26 ) DIRECTION_26.add( new Vector3f( i, j, k ) );
		for( int i : P_98 ) for( int j : P_98 ) for( int k : P_98 ) DIRECTION_98.add( new Vector3f( i, j, k ) );
	}
	
	@Override
	protected Function< Double, Double > calculate( LivingEntity entity, Temperature.Trait trait )
	{
		double indoorness = calcIndoorness( entity );
		double neutralTemp = Temperature.getNeutralWorldTemp( entity );
		return ( temp ) -> temp - ( temp - neutralTemp ) * Config.tempModifierExtraScaler * indoorness * indoorness;
	}
	
	public static double calcIndoorness( @NotNull LivingEntity entity )
	{
		AtomicReference< Double > indoorness = new AtomicReference<>( 0d );
		AtomicInteger blocks = new AtomicInteger( );
		ServerLevel level = ( ServerLevel )entity.level( );
		Vector3f current;
		
		for( Vector3f vec : Config.tempModifierCheckMorePrecisely ? DIRECTION_98 : DIRECTION_26 )
		{
			BlockPos.MutableBlockPos mutable = entity.blockPosition( ).above( ).mutable( );
			current = new Vector3f( 0, 0, 0 );
			
			for( int i = 1; i <= Config.tempModifierCheckDistance; i++ )
			{
				current.add( vec.x, vec.y, vec.z );
				mutable.move( ( int )current.x, ( int )current.y, ( int )current.z );
				
				BlockState block = level.getBlockState( mutable );
				
				VoxelShape collisionShape = block.getCollisionShape( level, mutable );
				AtomicReference< Double > volume = new AtomicReference<>( 0d );
				collisionShape.forAllBoxes( ( x1, y1, z1, x2, y2, z2 ) -> {
					double dx = x2 - x1;
					double dy = y2 - y1;
					double dz = z2 - z1;
					volume.updateAndGet( ( v ) -> v + dx * dy * dz );
				} );
				volume.updateAndGet( Math::sqrt );
				
				indoorness.updateAndGet( ( v ) -> v + volume.get( ) / Math.sqrt( i ) );
				blocks.getAndIncrement( );
				if( volume.get( ) >= 1.0 ) break;
			}
		}
		
		return indoorness.get( ) / blocks.get( );
	}
}