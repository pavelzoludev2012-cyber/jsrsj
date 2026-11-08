package com.example.dryrealms;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/** Тестовая команда: /dryrealms tp <biome> телепортирует к ближайшему биому мода. */
@Mod.EventBusSubscriber(modid = DryRealmsMod.MOD_ID)
public class ModCommands {
    private static final List<String> NAMES =
            List.of("scorched_lake", "desert_lake", "flooded_valley", "flooded_desert");

    @SubscribeEvent
    public static void onRegister(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("dryrealms")
                .requires(src -> src.hasPermission(2))
                .then(Commands.literal("tp")
                        .then(Commands.argument("biome", StringArgumentType.word())
                                .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(NAMES, builder))
                                .executes(ModCommands::teleport))));
    }

    private static int teleport(CommandContext<CommandSourceStack> ctx) {
        try {
            String name = StringArgumentType.getString(ctx, "biome");
            if (!NAMES.contains(name)) {
                ctx.getSource().sendFailure(Component.literal("Неизвестный биом: " + name));
                return 0;
            }
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            ServerLevel level = player.serverLevel();
            ResourceKey<Biome> key = switch (name) {
                case "scorched_lake" -> ModBiomes.SCORCHED_LAKE;
                case "desert_lake" -> ModBiomes.DESERT_LAKE;
                case "flooded_valley" -> ModBiomes.FLOODED_VALLEY;
                default -> ModBiomes.FLOODED_DESERT;
            };
            Pair<BlockPos, Holder<Biome>> found = level.findClosestBiome3d(
                    h -> h.is(key), player.blockPosition(), 6400, 32, 64);
            if (found == null) {
                ctx.getSource().sendFailure(Component.literal("Биом не найден рядом (радиус 6400)."));
                return 0;
            }
            BlockPos p = found.getFirst();
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, p.getX(), p.getZ());
            player.teleportTo(level, p.getX() + 0.5, y + 1, p.getZ() + 0.5,
                    player.getYRot(), player.getXRot());
            ctx.getSource().sendSuccess(() -> Component.literal("Телепорт в " + name), false);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("Ошибка: " + e.getMessage()));
            return 0;
        }
    }
}
