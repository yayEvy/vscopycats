package mod.everest.evy.vscopycats.fabric;

import mod.everest.evy.vscopycats.fabric.registry.ModBlocks;
import mod.everest.evy.vscopycats.fabric.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class VSCopyCats implements ModInitializer {
    public static final String MOD_ID = "vscopycats";

    @Override
    public void onInitialize() {
        System.out.println("VSCopyCats initializing...");
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
    }
}
