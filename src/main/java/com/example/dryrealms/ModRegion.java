package com.example.dryrealms;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModRegion extends Region {
    public ModRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry,
                          Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // Жарко и сухо; два биома различаются по weirdness (странности).
        Climate.Parameter temp = Climate.Parameter.span(0.55F, 1.0F);
        Climate.Parameter humidity = Climate.Parameter.span(-1.0F, -0.35F);
        Climate.Parameter continentalness = Climate.Parameter.span(-0.11F, 0.5F);
        Climate.Parameter erosion = Climate.Parameter.span(0.2F, 1.0F);   // ровный рельеф
        Climate.Parameter depth = Climate.Parameter.point(0.0F);

        mapper.accept(Pair.of(
                Climate.parameters(temp, humidity, continentalness, erosion, depth,
                        Climate.Parameter.span(-1.0F, 0.0F), 0.0F),
                ModBiomes.SCORCHED_LAKE));

        mapper.accept(Pair.of(
                Climate.parameters(temp, humidity, continentalness, erosion, depth,
                        Climate.Parameter.span(0.0F, 1.0F), 0.0F),
                ModBiomes.DESERT_LAKE));
        // Затопленные биомы: океанический диапазон continentalness, поэтому
        // рельеф сам уходит ниже уровня моря и заполняется водой.
        Climate.Parameter shallowSea = Climate.Parameter.span(-0.455F, -0.19F);
        Climate.Parameter anyHumidity = Climate.Parameter.span(-1.0F, 1.0F);

        // Затопленное межгорье: умеренный климат, у гористого берега (низкая erosion)
        mapper.accept(Pair.of(
                Climate.parameters(Climate.Parameter.span(-0.45F, 0.2F), anyHumidity, shallowSea,
                        Climate.Parameter.span(-1.0F, 0.2F), depth,
                        Climate.Parameter.span(-1.0F, 1.0F), 0.0F),
                ModBiomes.FLOODED_VALLEY));

        // Затопленная пустыня: жарко и сухо
        mapper.accept(Pair.of(
                Climate.parameters(temp, Climate.Parameter.span(-1.0F, -0.1F), shallowSea,
                        Climate.Parameter.span(-1.0F, 1.0F), depth,
                        Climate.Parameter.span(-1.0F, 1.0F), 0.0F),
                ModBiomes.FLOODED_DESERT));
    }
}
