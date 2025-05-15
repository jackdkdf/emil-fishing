package ca.jackfountain.emil_fishing;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        // Starting position (next to the chat box)
        int x = event.getPosX();
        int y = event.getPosY() - 30; // Start above the chat

        String[] lines = {
                "Line 1: Hello!",
                "Line 2: More info",
                String.format("Line 3: Y = %.1f", mc.player != null ? mc.player.getY() : 0)
        };

        for (String line : lines) {
            guiGraphics.drawString(mc.font, line, x, y, 0x00FFAA);
            y += mc.font.lineHeight + 2; // Move down for the next line
        }
    }

}
