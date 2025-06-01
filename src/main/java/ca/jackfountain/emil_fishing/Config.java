package ca.jackfountain.emil_fishing;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Boolean> AND_FILTER = BUILDER
            .comment("Enable this to use AND across filter categories instead of OR")
            .define("andFilter", false);

    // Fishing spot display configs are each stored in a boolean array
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> HOOKS_DISPLAY = BUILDER
            .comment("Display settings for hooks: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("hooksDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> MAGNETS_DISPLAY = BUILDER
            .comment("Display settings for magnets: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("magnetsDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> CHANCES_DISPLAY = BUILDER
            .comment("Display settings for chances: [Elusive, Wayfinder, Pearl, Treasure, Spirit]")
            .defineList("chancesDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> STABILITIES_DISPLAY = BUILDER
            .comment("Display settings for stabilities: [Yellow, Green, Blue]")
            .defineList("chancesDisplay", List.of(true, true, true), entry -> entry instanceof Boolean);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // Color values for text, used internally; no need to export to config file
    public static final Integer WHITE = 0xffffff;
    public static final Integer RED = 0xfc5454;
    public static final Integer BLUE = 0x2199f0;
    public static final Integer PURPLE = 0x8632fc;
    public static final Integer ORANGE = 0xfc7c3c;
    public static final Integer GREEN = 0x23c525;
    public static final Integer STOCK_COLOR_LOW = 0xf47404;
    public static final Integer STOCK_COLOR_MEDIUM = 0xfcfc54;
    public static final Integer STOCK_COLOR_HIGH = 0x54fc54;
    public static final Integer STOCK_COLOR_VERY_HIGH = 0x68e3ea;
    public static final Integer STOCK_COLOR_PLENTIFUL = 0xac6cfc;

    // Translation keys for each display list, used internally; no need to export to config file
    public static final String[] HOOK_KEYS = {"strong hook", "wise hook", "glimmering hook", "greedy hook", "lucky hook"};
    public static final String[] MAGNET_KEYS = {"xp magnet", "fish magnet", "pearl magnet", "treasure magnet", "spirit magnet"};
    public static final String[] CHANCE_KEYS = {"elusive fish chance", "wayfinder data", "pearl chance", "treasure chance", "spirit chance"};
    public static final String[] STABILITY_KEYS = {"yellow", "green", "blue"};
    public static final Integer[] STABILITY_HEX_KEYS = {STOCK_COLOR_MEDIUM, STOCK_COLOR_HIGH, STOCK_COLOR_VERY_HIGH};

    public static boolean andFilter;
    public static boolean[] hooksDisplay = new boolean[5];
    public static boolean[] magnetsDisplay = new boolean[5];
    public static boolean[] chancesDisplay = new boolean[5];
    public static boolean[] stabilitiesDisplay = new boolean[3];


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        andFilter = AND_FILTER.get();
        loadBooleanArray((List<Boolean>) HOOKS_DISPLAY.get(), hooksDisplay);
        loadBooleanArray((List<Boolean>) MAGNETS_DISPLAY.get(), magnetsDisplay);
        loadBooleanArray((List<Boolean>) CHANCES_DISPLAY.get(), chancesDisplay);
        loadBooleanArray((List<Boolean>) STABILITIES_DISPLAY.get(), stabilitiesDisplay);
    }

    private static void loadBooleanArray(List<Boolean> configList, boolean[] targetArray) {
        if (configList.size() != targetArray.length) {
            configList = List.of(true, true, true, true, true);
        }

        for (int i = 0; i < targetArray.length; i++) {
            targetArray[i] = configList.get(i);
        }
    }

    public static void save() {
        AND_FILTER.set(andFilter);
        HOOKS_DISPLAY.set(arrayToList(hooksDisplay));
        MAGNETS_DISPLAY.set(arrayToList(magnetsDisplay));
        CHANCES_DISPLAY.set(arrayToList(chancesDisplay));
        STABILITIES_DISPLAY.set(arrayToList(stabilitiesDisplay));
    }

    private static List<Boolean> arrayToList(boolean[] array) {
        List<Boolean> list = new ArrayList<>();
        for (boolean b : array) {
            list.add(b);
        }
        return list;
    }
}
