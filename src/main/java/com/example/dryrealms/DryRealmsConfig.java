package com.example.dryrealms;

import net.minecraftforge.common.ForgeConfigSpec;

public class DryRealmsConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue TEST_MODE;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        TEST_MODE = b.comment("Тестовый режим: биомы мода занимают почти весь мир.",
                "Нужно создать НОВЫЙ мир, чтобы изменение сработало.")
                .define("testMode", false);
        SPEC = b.build();
    }
}
