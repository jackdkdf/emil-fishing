package ca.jackfountain.emil_fishing.events;

import net.minecraft.client.Minecraft;
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

    private static final EntityDataAccessor<Component> TEXT_ACCESSOR = new EntityDataAccessor<>(23, EntityDataSerializers.COMPONENT);

    private static final Set<Display.TextDisplay> pendingDisplays = new HashSet<>();

    @SubscribeEvent
    public static void onEntityJoinEvent(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        // Only run on client
        if (Minecraft.getInstance().level == null) return;

        if (entity instanceof Display.TextDisplay textDisplay) {
            pendingDisplays.add(textDisplay);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        pendingDisplays.removeIf(textDisplay -> {
            Component text = textDisplay.getEntityData().get(TEXT_ACCESSOR);
            String sText = text.getString();
            if (!sText.isEmpty()) {
                // sout goes to latest.log in /logs
                System.out.println(text);
                System.out.println(sText);
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal(textDisplay.getName().getString() + " id: " + textDisplay.getId()));
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal(sText));
                return true; // remove from pending
            }
            return false; // still waiting for sync
        });
    }
}
