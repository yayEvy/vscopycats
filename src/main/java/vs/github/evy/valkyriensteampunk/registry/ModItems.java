package vs.github.evy.valkyriensteampunk.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vs.github.evy.valkyriensteampunk.content.ropes.RopeItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "valkyrien_steampunk");

    public static final RegistryObject<Item> ROPE_ITEM = ITEMS.<Item>register("rope_item",
            () -> new RopeItem());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
