package ca.jackfountain.emil_fishing.gui;

import ca.jackfountain.emil_fishing.EmilFishing;
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

        Collection<FishingSpot> filteredSpots = FishingSpotManager.getInstance().getFilteredSpots();
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        List<String> linesList = new ArrayList<>();
        linesList.add(coordinates);
        linesList.add("----Fishing Spots----");
        filteredSpots.forEach(spot -> linesList.add(String.valueOf(spot)));
        String[] lines = linesList.toArray(new String[0]);

        float scale = 0.75f;
        int padding = 1;
        int yOffset = y;

        for (String line : lines) {
            int textWidth = (int) (mc.font.width(line) * scale);
            int textHeight = (int) (mc.font.lineHeight * scale);

            // Draw translucent background with scaled width/height
            guiGraphics.fill(
                    x - padding,
                    yOffset - padding,
                    x + textWidth + padding,
                    yOffset + textHeight + padding,
                    0x88000000 // black, 53% opacity
            );

            // Draw scaled text
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale, 1.0f);
            guiGraphics.drawString(
                    mc.font,
                    line,
                    (int) (x / scale),
                    (int) (yOffset / scale),
                    0xFFFFFF
            );
            guiGraphics.pose().popPose();

            yOffset += textHeight + 2; // Move down for the next line
        }

    }
}
