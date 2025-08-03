/*
package vs.github.evy.valkyriensteampunk.content.ropes;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import vs.github.evy.valkyriensteampunk.content.ropes.RopeUtils;

public class RopeEvents {

    private BlockPos firstClickedPos;
    private Long firstShipId;
    private Entity firstEntity;
    private Integer activeConstraintId;
    private ServerLevel ServerLevel;



    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos().immutable();
        Player player = context.getPlayer();

        if (!(level instanceof ServerLevel serverLevel) || player == null)
            return InteractionResult.PASS;

        Long shipId = getShipIdAtPos(serverLevel, blockPos);

        if (firstClickedPos == null) {
            firstClickedPos = blockPos;
            firstShipId = shipId;
            return InteractionResult.SUCCESS;
        }

        if (firstClickedPos.equals(blockPos)) {
            return InteractionResult.FAIL;
        }

        long shipA = firstShipId != null ? firstShipId : RopeUtils.getGroundBodyId(serverLevel);
        long shipB = shipId != null ? shipId : RopeUtils.getGroundBodyId(serverLevel);

        Vector3d worldPosA = new Vector3d(firstClickedPos.getX() + 0.5, firstClickedPos.getY() + 0.5, firstClickedPos.getZ() + 0.5);
        Vector3d worldPosB = new Vector3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        Vector3d localPosA = VSGameUtilsKt.toWorldCoordinates((Level) VSGameUtilsKt.getShipObjectManagingPos(serverLevel, firstClickedPos), worldPosA);
        Vector3d localPosB = VSGameUtilsKt.toWorldCoordinates((Level) VSGameUtilsKt.getShipObjectManagingPos(serverLevel, blockPos), worldPosB);

        RopeUtils.createRopeConstraint(serverLevel, shipA, shipB, localPosA, localPosB, 1000f, 5f, 0.5f, 0.1f);

        // Reset state for next rope
        firstClickedPos = null;
        firstShipId = null;

        return InteractionResult.SUCCESS;
    }


    private Long getShipIdAtPos(ServerLevel level, BlockPos pos) {
        Ship shipObject = VSGameUtilsKt.getShipObjectManagingPos(level, pos);
        return shipObject != null ? shipObject.getId() : null;
    }
}


 */