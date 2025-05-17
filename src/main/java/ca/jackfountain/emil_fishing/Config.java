package ca.jackfountain.emil_fishing;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    // Array-based configuration entries
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> HOOKS_DISPLAY = BUILDER
            .comment("Display settings for hooks: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("hooksDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> MAGNETS_DISPLAY = BUILDER
            .comment("Display settings for magnets: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("magnetsDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> CHANCES_DISPLAY = BUILDER
            .comment("Display settings for chances: [Elusive, Wayfinder, Pearl, Treasure, Spirit]")
            .defineList("chancesDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // Translation keys for each display list
    public static final String[] HOOK_KEYS = {"strong hook", "wise hook", "glimmering hook", "greedy hook", "lucky hook"};
    public static final String[] MAGNET_KEYS = {"xp magnet", "fish magnet", "pearl magnet", "treasure magnet", "spirit magnet"};
    public static final String[] CHANCE_KEYS = {"elusive fish chance", "wayfinder data", "pearl chance", "treasure chance", "spirit chance"};

    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    // Array fields for easy access
    public static boolean[] hooksDisplay = new boolean[5];
    public static boolean[] magnetsDisplay = new boolean[5];
    public static boolean[] chancesDisplay = new boolean[5];

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(ResourceLocation.tryParse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(itemName)))
                .collect(Collectors.toSet());

        loadBooleanArray((List<Boolean>) HOOKS_DISPLAY.get(), hooksDisplay, "hooksDisplay");
        loadBooleanArray((List<Boolean>) MAGNETS_DISPLAY.get(), magnetsDisplay, "magnetsDisplay");
        loadBooleanArray((List<Boolean>) CHANCES_DISPLAY.get(), chancesDisplay, "chancesDisplay");
    }

    private static void loadBooleanArray(List<Boolean> configList, boolean[] targetArray, String configName) {
        if (configList.size() != targetArray.length) {
            configList = List.of(true, true, true, true, true);
        }

        for (int i = 0; i < targetArray.length; i++) {
            targetArray[i] = configList.get(i);
        }
    }

    static void save() {
        HOOKS_DISPLAY.set(arrayToList(hooksDisplay));
        MAGNETS_DISPLAY.set(arrayToList(magnetsDisplay));
        CHANCES_DISPLAY.set(arrayToList(chancesDisplay));
    }

    private static List<Boolean> arrayToList(boolean[] array) {
        List<Boolean> list = new ArrayList<>();
        for (boolean b : array) {
            list.add(b);
        }
        return list;
    }
}
