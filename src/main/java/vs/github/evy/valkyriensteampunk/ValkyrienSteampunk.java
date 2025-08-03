package vs.github.evy.valkyriensteampunk;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vs.github.evy.valkyriensteampunk.registry.ModItems;

@Mod(ValkyrienSteampunk.MOD_ID)
public class ValkyrienSteampunk {
    public static final String MOD_ID = "valkyrien_steampunk";

    public ValkyrienSteampunk() {
        // Common Code Here
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

    }
}
