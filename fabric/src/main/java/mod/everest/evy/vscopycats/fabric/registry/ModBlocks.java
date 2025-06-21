package mod.everest.evy.vscopycats.fabric.registry;

import mod.everest.evy.vscopycats.fabric.content.blocks.BalloonBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
    public static final Block BALLOON_BLOCK = new BalloonBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));

    public static void registerModBlocks() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("vscopycats", "balloon_block"), BALLOON_BLOCK);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("vscopycats", name), block);
    }
}
