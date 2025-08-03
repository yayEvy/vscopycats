package vs.github.evy.valkyriensteampunk.content.ropes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class RopeItem extends Item {
    private static final float DEFAULT_MAX_FORCE = 100000f;
    private static final float DEFAULT_STIFFNESS = 1000f;
    private static final float DEFAULT_DAMPING = 50f;

    public RopeItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }

        BlockPos clickedPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.contains("FirstClickX")) {
            tag.putInt("FirstClickX", clickedPos.getX());
            tag.putInt("FirstClickY", clickedPos.getY());
            tag.putInt("FirstClickZ", clickedPos.getZ());

            long shipId = -1;
            Ship shipObjA = VSGameUtilsKt.getShipObjectManagingPos(serverLevel, clickedPos);
            if (shipObjA != null) {
                shipId = shipObjA.getId();
            }

            tag.putLong("FirstShipId", shipId);


            spawnParticles(serverLevel, clickedPos, ParticleTypes.HAPPY_VILLAGER);
            context.getPlayer().sendSystemMessage(Component.literal("First position set! Click another block to create rope."));
            return InteractionResult.SUCCESS;
        }

        BlockPos firstPos = new BlockPos(
                tag.getInt("FirstClickX"),
                tag.getInt("FirstClickY"),
                tag.getInt("FirstClickZ")
        );

        if (clickedPos.equals(firstPos)) {
            context.getPlayer().sendSystemMessage(Component.literal("Cannot create rope to the same block!"));
            clearTag(tag);
            return InteractionResult.FAIL;
        }

        Vector3d worldPosA = new Vector3d(
                firstPos.getX() + 0.5,
                firstPos.getY() + 0.5,
                firstPos.getZ() + 0.5
        );
        Vector3d worldPosB = new Vector3d(
                clickedPos.getX() + 0.5,
                clickedPos.getY() + 0.5,
                clickedPos.getZ() + 0.5
        );

        float ropeLength = (float) worldPosA.distance(worldPosB);

        long shipAId = tag.getLong("FirstShipId");
        if (shipAId == -1) shipAId = RopeUtils.getGroundBodyId(serverLevel);

        long shipBId = -1;
        Ship shipObjB = VSGameUtilsKt.getShipObjectManagingPos(serverLevel, clickedPos);
        if (shipObjB != null) {
            shipBId = shipObjB.getId();
        }

        System.out.println("Creating rope between:");
        System.out.println("ShipA ID: " + shipAId);
        System.out.println("ShipB ID: " + shipBId);

        RopeUtils.createRopeConstraint(
                serverLevel,
                shipAId,
                shipBId,
                getLocalPosition(serverLevel, shipAId, worldPosA),
                getLocalPosition(serverLevel, shipBId, worldPosB),
                DEFAULT_MAX_FORCE,
                ropeLength,
                DEFAULT_STIFFNESS,
                DEFAULT_DAMPING
        );

        spawnParticlesAlongLine(serverLevel, worldPosA, worldPosB);
        context.getPlayer().sendSystemMessage(Component.literal("Rope created! Length: " + String.format("%.1f", ropeLength)));
        clearTag(tag);

        return InteractionResult.SUCCESS;
    }

    private Vector3d getLocalPosition(ServerLevel level, long shipId, Vector3d worldPos) {
        if (shipId == RopeUtils.getGroundBodyId(level)) {
            return worldPos;
        }

        Ship ship = VSGameUtilsKt.getShipManagingPos(level, BlockPos.of(shipId)); // Use the correct method name
        if (ship == null) {
            return worldPos;
        }

        Vector3d localPos = new Vector3d();
        ship.getTransform().getWorldToShip().transformPosition(worldPos, localPos);
        return localPos;
    }

    private void spawnParticlesAlongLine(ServerLevel level, Vector3d from, Vector3d to) {
        for (double t = 0; t <= 1.0; t += 0.05) {
            Vector3d pos = new Vector3d(
                    from.x * (1 - t) + to.x * t,
                    from.y * (1 - t) + to.y * t,
                    from.z * (1 - t) + to.z * t
            );
            level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }
    }

    private void spawnParticles(ServerLevel level, BlockPos pos, ParticleOptions type) {
        level.sendParticles(type,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                10,
                0.5, 0.5, 0.5, 0.1);
    }

    private void clearTag(CompoundTag tag) {
        tag.remove("FirstClickX");
        tag.remove("FirstClickY");
        tag.remove("FirstClickZ");
        tag.remove("FirstShipId");
    }
}
