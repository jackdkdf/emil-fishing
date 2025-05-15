package ca.jackfountain.emil_fishing;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

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
                .setSavingRunnable(() -> {
                        Config.save();
                        LOGGER.info("SAVED: {}", Config.displayStrongHook);
                        });


        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.emil_fishing.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Add your config entries here
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("Display strong hook"), Config.displayStrongHook)
                .setSaveConsumer(newValue -> Config.displayStrongHook = newValue)
                .build());

        this.minecraft.setScreen(builder.build());
    }
}
