package mod.everest.evy.vscopycats.forge;

import mod.everest.evy.vscopycats.VSAddonTemplateMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mod.everest.evy.vscopycats.VSAddonTemplateMod.init;
import static mod.everest.evy.vscopycats.VSAddonTemplateMod.initClient;

@Mod(VSCopycats.MOD_ID)
public class VSCopycats {
    public static final String MOD_ID = "vs_copycats" ;

    public VSCopycats() {
        init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        initClient();
    }
}
