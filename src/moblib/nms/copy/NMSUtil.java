package moblib.nms.copy;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_10_R1.util.UnsafeList;

import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class NMSUtil {

	public static void clearPathfinderGoals(PathfinderGoalSelector goalSelector) {
		/* Use reflection to edit the default PathfinderGoalSelector fields.
		 * Use this to delete pre-made pathfinder goals. */

		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);

			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			
/*			gsa = net.minecraft.server.v1_10_R1.PathfinderGoalSelector.class.getDeclaredField("b");
			gsa.setAccessible(true);

			//Clear out the goals.
			gsa.set(selector, new UnsafeList());
			
			//Clear out the 'c' list as well.
			gsa = net.minecraft.server.v1_10_R1.PathfinderGoalSelector.class.getDeclaredField("c");
			gsa.setAccessible(true);

			//Clear out the goals.
			gsa.set(selector, new UnsafeList());*/
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
}
