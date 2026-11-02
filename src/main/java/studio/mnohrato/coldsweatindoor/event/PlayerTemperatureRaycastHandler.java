//package studio.mnohrato.coldsweatindoor.event;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.Vec3;
//import net.minecraft.world.phys.shapes.VoxelShape;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import studio.mnohrato.coldsweatindoor.MrozbirekGlueMod;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Mod.EventBusSubscriber(
//	modid = MrozbirekGlueMod.MOD_ID,
//	bus = Mod.EventBusSubscriber.Bus.MOD
//)
//public class PlayerTemperatureRaycastHandler
//{
//	private static int time = 0;
//
//	@SubscribeEvent
//	public static void onTick( TickEvent.ServerTickEvent ste )
//	{
//		time += 1;
//		if( time >= 20 )
//		{
//			time = 0;
//
//			ste.getServer( ).getPlayerList( ).getPlayers( ).forEach( ( player ) -> {
//
//			} );
//
//			// todo: change temperature according to avgIndoorness
//		}
//	}
//
//}
