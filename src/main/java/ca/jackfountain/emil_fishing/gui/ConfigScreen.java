package ca.jackfountain.emil_fishing.gui;

import ca.jackfountain.emil_fishing.Config;
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

        // General Category
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("General"));
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Utilize AND filtering"),
                        Config.andFilter)
                .setSaveConsumer(newValue -> Config.andFilter = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Display total grottos"),
                        Config.totalGrottosDisplay)
                .setSaveConsumer(newValue -> Config.totalGrottosDisplay = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Display total catches"),
                        Config.totalCatchesDisplay)
                .setSaveConsumer(newValue -> Config.totalCatchesDisplay = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Display fish catches"),
                        Config.fishDisplay)
                .setSaveConsumer(newValue -> Config.fishDisplay = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Display elusive fish catches"),
                        Config.elusiveFishDisplay)
                .setSaveConsumer(newValue -> Config.elusiveFishDisplay = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("Display special catches"),
                        Config.specialDisplay)
                .setSaveConsumer(newValue -> Config.specialDisplay = newValue)
                .build());

        // Hooks Category
        ConfigCategory hooks = builder.getOrCreateCategory(Component.translatable("Hooks"));
        for (int i = 0; i < Config.HOOK_KEYS.length; i++) {
            final int index = i;
            hooks.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.HOOK_KEYS[index]),
                            Config.hooksDisplay.get(index))
                    .setSaveConsumer(newValue -> Config.hooksDisplay.set(index, newValue))
                    .build());
        }

        // Magnets Category
        ConfigCategory magnets = builder.getOrCreateCategory(Component.translatable("Magnets"));
        for (int i = 0; i < Config.MAGNET_KEYS.length; i++) {
            final int index = i;
            magnets.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.MAGNET_KEYS[index]),
                            Config.magnetsDisplay.get(index))
                    .setSaveConsumer(newValue -> Config.magnetsDisplay.set(index, newValue))
                    .build());
        }

        // Chances Category
        ConfigCategory chances = builder.getOrCreateCategory(Component.translatable("Chances"));
        for (int i = 0; i < Config.CHANCE_KEYS.length; i++) {
            final int index = i;
            chances.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.CHANCE_KEYS[index]),
                            Config.chancesDisplay.get(index))
                    .setSaveConsumer(newValue -> Config.chancesDisplay.set(index, newValue))
                    .build());
        }

        // Stabilities Category
        ConfigCategory stabilities = builder.getOrCreateCategory(Component.translatable("Stabilities"));
        for (int i = 0; i < Config.STABILITY_KEYS.length; i++) {
            final int index = i;
            stabilities.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("Display " + Config.STABILITY_KEYS[index]),
                            Config.stabilitiesDisplay.get(index))
                    .setSaveConsumer(newValue -> Config.stabilitiesDisplay.set(index, newValue))
                    .build());
        }

        this.minecraft.setScreen(builder.build());
    }
}
