package ca.jackfountain.emil_fishing.gui;

import ca.jackfountain.emil_fishing.EmilFishing;
import ca.jackfountain.emil_fishing.data.FishingSpot;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {
    private static final Map<String, Integer> COLOR_MAPPING = Map.of(
            "Fishing Spots", 0x00FF00,  // Green
            "XYZ:", 0xADD8E6,          // Light Blue
            "blocks", 0xFFA500,        // Orange
            "----", 0xFFFFFF           // White
    );

    @SubscribeEvent
    public static void onChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        int x = event.getPosX();
        int y = event.getPosY() - 30;

        Collection<FishingSpot> filteredSpots = FishingSpotManager.getInstance().getFilteredSpots();
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        List<String> lines = new ArrayList<>();
        lines.add(coordinates);
        lines.add("----Fishing Spots----");
        filteredSpots.forEach(spot -> lines.add(spot + calcEuclideanDistance(spot.getPos())));

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

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale, 1.0f);

            int currentX = (int) (x / scale);

            String remainingText = line;
            while (!remainingText.isEmpty()) {
                String match = null;
                int matchLength = 0;
                int color = 0xFFFFFF; // default color

                for (Map.Entry<String, Integer> entry : COLOR_MAPPING.entrySet()) {
                    if (remainingText.startsWith(entry.getKey()) && entry.getKey().length() > matchLength) {
                        match = entry.getKey();
                        matchLength = entry.getKey().length();
                        color = entry.getValue();
                    }
                }

                if (match != null) {
                    drawTextSegment(guiGraphics, mc.font, match, currentX, (int) (yOffset / scale), color);
                    currentX += mc.font.width(match);
                    remainingText = remainingText.substring(matchLength);
                } else {
                    // Draw the next character in default color
                    String segment = remainingText.substring(0, 1);
                    drawTextSegment(guiGraphics, mc.font, segment, currentX, (int) (yOffset / scale), 0xFFFFFF);
                    currentX += mc.font.width(segment);
                    remainingText = remainingText.substring(1);
                }
            }
            guiGraphics.pose().popPose();

            yOffset += textHeight + 2; // Move down for the next line
        }

    private static void drawTextSegment(GuiGraphics gui, Font font, String text,
                                        int x, int y, int color) {
        gui.drawString(
                font,
                text,
                x,
                y,
                color
        );
    }

    private static String calcEuclideanDistance(BlockPos spotPos) {
        Minecraft mc = Minecraft.getInstance();
        double dx = mc.player.getX() - spotPos.getX();
        double dy = mc.player.getY() - spotPos.getY();
        double dz = mc.player.getZ() - spotPos.getZ();
        return String.format(" %.1f blocks", Math.sqrt(dx * dx + dy * dy + dz * dz));
    }
}


