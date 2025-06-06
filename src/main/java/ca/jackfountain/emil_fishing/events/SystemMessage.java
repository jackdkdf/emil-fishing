package ca.jackfountain.emil_fishing.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.SystemMessageReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class SystemMessage {

    @SubscribeEvent
    public static void onSystemMessageReceivedEventEvent(SystemMessageReceivedEvent event) {
        String msg = event.getMessage().getString();

        // Only run on client side
        if (Minecraft.getInstance().level == null) return;
        appendTextToFile(msg);
    }

    private static void appendTextToFile(String text) {
        String userHome = System.getProperty("user.home");
        Path filePath = Paths.get(userHome, "Downloads", "chats.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write fishing spot to Downloads folder: " + e.getMessage());
        }
    }
}
