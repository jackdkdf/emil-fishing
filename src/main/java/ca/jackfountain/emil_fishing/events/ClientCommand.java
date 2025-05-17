package ca.jackfountain.emil_fishing.events;

import ca.jackfountain.emil_fishing.gui.ConfigScreen;
import ca.jackfountain.emil_fishing.EmilFishing;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class ClientCommand {
    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("emil")
                        .executes(context -> {
                            Minecraft.getInstance().setScreen(new ConfigScreen(null));
                            return 1;
                        })
        );
    }
}

