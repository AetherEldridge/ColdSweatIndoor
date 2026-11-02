package studio.mnohrato.coldsweatindoor.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import studio.mnohrato.coldsweatindoor.Config;

import java.util.function.Function;

public class IndoorTempModifier extends TempModifier
{
	/**
	 * Sampled directions: 8 horizontal, 8 diagonal-up and 1 straight-up.
	 * The downward hemisphere is deliberately ignored because a floor does not
	 * make a place feel indoors.
	 */
	private static final double[][] DIRECTIONS = createDirections( );
	/** Occlusion multiplier for each sample step; nearby cover counts much more. */
	private static final double[] DISTANCE_FALLOFF = createDistanceFalloff( );
	
	public IndoorTempModifier( )
	{
		tickRate( Config.updateInterval );
	}
	
	@Override
	protected Function< Double, Double > calculate( @NotNull LivingEntity entity, Temperature.Trait trait )
	{
		// Evaluate the scan once per update instead of on every temperature apply.
		double indoorness = indoorness( entity );
		double neutralTemp = Temperature.getNeutralWorldTemp( entity );
		return ( temp ) -> temp - ( temp - neutralTemp ) * indoorness;
	}
	
	/**
	 * Estimates how "indoors" an entity is, in the range [0, 1].
	 * <p>
	 * The value approaches 1 for a tiny, fully enclosed space and approaches 0
	 * for open terrain. A larger enclosed space scores lower than a smaller one
	 * with the same walls, and denser/less transparent cover scores higher.
	 */
	public static double indoorness( @NotNull LivingEntity entity )
	{
		Level level = entity.level( );
		Vec3 eye = entity.getEyePosition( );
		return indoorness( level, eye.x, eye.y, eye.z );
	}
	
	/**
	 * Estimates how "indoors" the given block position is, in the range [0, 1].
	 */
	public static double indoorness( @NotNull Level level, @NotNull BlockPos pos )
	{
		return indoorness( level, pos.getX( ) + 0.5, pos.getY( ) + 0.5, pos.getZ( ) + 0.5 );
	}
	
	private static double indoorness( Level level, double originX, double originY, double originZ )
	{
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos( );
		double total = 0.0;
		
		for( double[] direction : DIRECTIONS )
		{
			double transparency = 1.0;
			double directionScore = 0.0;
			
			for( int step = 0; step < DISTANCE_FALLOFF.length; step++ )
			{
				double distance = ( step + 1 ) * Config.step;
				cursor.set(
					Mth.floor( originX + direction[ 0 ] * distance ),
					Mth.floor( originY + direction[ 1 ] * distance ),
					Mth.floor( originZ + direction[ 2 ] * distance )
				);
				
				if( !level.isInWorldBounds( cursor ) ) break;
				
				BlockState state = level.getBlockState( cursor );
				if( state.isAir( ) ) continue;
				
				double solidity = solidity( level, cursor, state );
				if( solidity <= 0.0 ) continue;
				
				directionScore += solidity * DISTANCE_FALLOFF[ step ] * transparency;
				
				// Fully opaque blocks stop the ray. Translucent ones (leaves, glass,
				// water, ...) accumulate a little more score per layer and let the
				// ray continue with reduced strength.
				if( solidity >= 0.75 ) break;
				
				transparency *= 1.0 - solidity * 0.75;
				if( transparency < 0.05 ) break;
			}
			
			total += directionScore;
		}
		
		return Mth.clamp( total / DIRECTIONS.length, 0.0, 1.0 );
	}
	
	/**
	 * How much a block blocks the view, in the range [0, 1]. Full solid blocks
	 * count the most, translucent blocks count partially, and non-solid blocks
	 * count little or not at all.
	 */
	@SuppressWarnings(
		value = "deprecation"
	)
	private static double solidity( Level level, BlockPos pos, @NotNull BlockState state )
	{
		if( state.isAir( ) ) return 0.0;
		
		if( state.is( BlockTags.LEAVES ) ) return Config.solidityLeaves;
		if( state.is( BlockTags.IMPERMEABLE ) || state.getBlock( ) instanceof HalfTransparentBlock || state.getBlock( ) instanceof IronBarsBlock )
			return Config.solidityImpermeable;
		
		if( state.isSolidRender( level, pos ) ) return Config.soliditySolidRender;
		if( state.canOcclude( ) ) return Config.solidityCanOcclude;
		if( state.isCollisionShapeFullBlock( level, pos ) ) return Config.solidityFullCollision;
		if( state.blocksMotion( ) ) return Config.solidityBlocksMotion;
		
		if( !state.getFluidState( ).isEmpty( ) ) return Config.solidityFluid;
		
		return Config.solidityDefault;
	}
	
	private static double @NotNull [][] createDirections( )
	{
		double[][] directions = new double[ 17 ][];
		
		for( int i = 0; i < 8; i++ )
		{
			double angle = Math.toRadians( i * 45.0 );
			directions[ i ] = new double[]{ Math.cos( angle ), 0.0, Math.sin( angle ) };
		}
		
		double diagonal = Math.sqrt( 0.5 );
		for( int i = 0; i < 8; i++ )
		{
			double angle = Math.toRadians( i * 45.0 + 22.5 );
			directions[ 8 + i ] = new double[]{ Math.cos( angle ) * diagonal, diagonal, Math.sin( angle ) * diagonal };
		}
		
		directions[ 16 ] = new double[]{ 0.0, 1.0, 0.0 };
		return directions;
	}
	
	private static double @NotNull [] createDistanceFalloff( )
	{
		double[] falloff = new double[ ( int )Math.ceil( Config.maxDistance / Config.step ) ];
		
		for( int i = 0; i < falloff.length; i++ )
		{
			double distance = ( i + 1 ) * Config.step;
			double closeness = Math.max( 0.0, 1.0 - distance / Config.maxDistance );
			falloff[ i ] = closeness * Math.sqrt( closeness );
		}
		
		return falloff;
	}
}
