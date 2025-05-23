package ca.jackfountain.emil_fishing.events;

import ca.jackfountain.emil_fishing.data.FishingSpot;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EntityJoin {

    private static final EntityDataAccessor<Component> TEXT_ACCESSOR =
            new EntityDataAccessor<>(23, EntityDataSerializers.COMPONENT);

    private static final Set<Display.TextDisplay> pendingDisplays = new HashSet<>();

    private static double lastX = Double.NaN, lastY = Double.NaN, lastZ = Double.NaN;

    @SubscribeEvent
    public static void onEntityJoinEvent(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        // Only run on client side
        if (Minecraft.getInstance().level == null) return;

        if (entity instanceof Display.TextDisplay textDisplay) {
            pendingDisplays.add(textDisplay);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase != TickEvent.Phase.END) return;

        // Code block for refreshing fishing spots on player teleport
        if (mc.player != null && mc.level != null) {
            double x = mc.player.getX();
            double y = mc.player.getY();
            double z = mc.player.getZ();

            if (!Double.isNaN(lastX)) {
                double dx = x - lastX;
                double dy = y - lastY;
                double dz = z - lastZ;
                double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (dist > 10) { // Threshold for a teleport
                    FishingSpotManager.getInstance().clearSpots();
                }
            }
            lastX = x;
            lastY = y;
            lastZ = z;
        }

        // Clear FishingSpots once per real-life hour
        FishingSpotManager.getInstance().clearIfNewHour();

        pendingDisplays.removeIf(textDisplay -> {
            Component text = textDisplay.getEntityData().get(TEXT_ACCESSOR);
            String sText = text.getString();

            if (sText.startsWith("Fishing Spot")) {
                BlockPos pos = textDisplay.blockPosition();

                FishingSpot spot = new FishingSpot(pos, sText);
                FishingSpotManager.getInstance().addSpot(textDisplay.getId(), spot);


                // Debug / log output
                System.out.println("New TextDisplay:");
                System.out.println("Raw Text: " + sText);
                System.out.println("Position: " + pos);

                // Optional: show in Minecraft chat
                // mc.gui.getChat().addMessage(Component.literal("FishingSpot detected: " + sText));

                return true; // Remove from pendingDisplays
            }
            return false; // Still waiting for synced text
        });
    }
}
