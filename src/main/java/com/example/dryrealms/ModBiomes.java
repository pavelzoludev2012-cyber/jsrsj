package com.example.dryrealms;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> SCORCHED_LAKE = key("scorched_lake");
    public static final ResourceKey<Biome> DESERT_LAKE = key("desert_lake");
    public static final ResourceKey<Biome> FLOODED_VALLEY = key("flooded_valley");
    public static final ResourceKey<Biome> FLOODED_DESERT = key("flooded_desert");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(DryRealmsMod.MOD_ID, name));
    }
}
