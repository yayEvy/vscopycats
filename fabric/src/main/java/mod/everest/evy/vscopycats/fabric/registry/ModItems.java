package mod.everest.evy.vscopycats.fabric.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final Item BALLOON_BLOCK = Registry.register(BuiltInRegistries.ITEM,
            new ResourceLocation("vscopycats", "balloon"),
            new BlockItem(ModBlocks.BALLOON_BLOCK, new FabricItemSettings()));

    public static void registerModItems() {
    }
}
