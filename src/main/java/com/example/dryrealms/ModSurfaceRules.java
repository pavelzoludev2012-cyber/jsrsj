package com.example.dryrealms;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static SurfaceRules.RuleSource block(net.minecraft.world.level.block.Block b) {
        return SurfaceRules.state(b.defaultBlockState());
    }

    public static SurfaceRules.RuleSource makeRules() {
        // Выжженная земля: камень, гравий, coarse dirt, терракота. Травы нет.
        SurfaceRules.RuleSource scorched = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.SCORCHED_LAKE),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.15D, 0.15D), block(Blocks.GRAVEL)),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.2D, 0.2D), block(Blocks.COARSE_DIRT)),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SURFACE, -0.3D, 0.1D), block(Blocks.BLACKSTONE)),
                                block(Blocks.STONE))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.2D, 0.2D), block(Blocks.TERRACOTTA)),
                                block(Blocks.COARSE_DIRT)))));

        // Пустынное дно: песок и песчаник.
        SurfaceRules.RuleSource desert = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.DESERT_LAKE),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, block(Blocks.SAND)),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, block(Blocks.SANDSTONE))));

        // Дно затопленных биомов
        SurfaceRules.RuleSource floodedValley = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.FLOODED_VALLEY),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.2D, 0.2D), block(Blocks.GRAVEL)),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.2D, 0.2D), block(Blocks.CLAY)),
                        block(Blocks.DIRT))));

        SurfaceRules.RuleSource floodedDesert = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.FLOODED_DESERT),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, block(Blocks.SAND)),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, block(Blocks.SANDSTONE))));

        return SurfaceRules.sequence(scorched, desert, floodedValley, floodedDesert);
    }
}
