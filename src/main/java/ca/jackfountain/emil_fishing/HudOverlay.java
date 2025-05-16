package ca.jackfountain.emil_fishing;

import ca.jackfountain.emil_fishing.data.FishingSpot;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {

    @SubscribeEvent
    public static void onChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        // Adjust text position
        int x = event.getPosX();
        int y = event.getPosY() - 30;

        Collection<FishingSpot> spots = FishingSpotManager.getInstance().getSpots();
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        List<String> linesList = new ArrayList<>();
        linesList.add(coordinates);
        linesList.add("----Fishing Spots----");
        spots.forEach(spot -> linesList.add(String.valueOf(spot)));
        String[] lines = linesList.toArray(new String[0]);


        for (String line : lines) {
            guiGraphics.drawString(mc.font, line, x, y, 0x00FFAA);
            y += mc.font.lineHeight + 2; // Move down for the next line
        }
    }

}
