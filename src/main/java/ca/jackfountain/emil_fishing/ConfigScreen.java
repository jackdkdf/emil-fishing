package ca.jackfountain.emil_fishing;

import com.mojang.logging.LogUtils;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private static final Logger LOGGER = LogUtils.getLogger();

    public ConfigScreen(Screen parent) {
        super(Component.translatable("title.emil_fishing.config"));
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("Fishing Perk Filters"))
                .setTransparentBackground(true)
                .setSavingRunnable(Config::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Hooks Category
        ConfigCategory hooks = builder.getOrCreateCategory(Component.translatable("Hooks"));
        for (int i = 0; i < Config.HOOK_KEYS.length; i++) {
            final int index = i;
            hooks.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.HOOK_KEYS[index]),
                            Config.hooksDisplay[index])
                    .setSaveConsumer(newValue -> Config.hooksDisplay[index] = newValue)
                    .build());
        }

        // Magnets Category
        ConfigCategory magnets = builder.getOrCreateCategory(Component.translatable("Magnets"));
        for (int i = 0; i < Config.MAGNET_KEYS.length; i++) {
            final int index = i;
            magnets.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.MAGNET_KEYS[index]),
                            Config.magnetsDisplay[index])
                    .setSaveConsumer(newValue -> Config.magnetsDisplay[index] = newValue)
                    .build());
        }

        // Chances Category
        ConfigCategory chances = builder.getOrCreateCategory(Component.translatable("Chances"));
        for (int i = 0; i < Config.CHANCE_KEYS.length; i++) {
            final int index = i;
            chances.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.CHANCE_KEYS[index]),
                            Config.chancesDisplay[index])
                    .setSaveConsumer(newValue -> Config.chancesDisplay[index] = newValue)
                    .build());
        }

        this.minecraft.setScreen(builder.build());
    }
}
