package vs.github.evy.valkyriensteampunk.content.ropes;

import net.minecraft.server.level.ServerLevel;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.apigame.joints.VSDistanceJoint;
import org.valkyrienskies.core.apigame.joints.VSJointMaxForceTorque;
import org.valkyrienskies.core.apigame.joints.VSJointPose;
import org.valkyrienskies.core.apigame.world.PhysLevelCore;
import org.valkyrienskies.core.apigame.world.ServerShipWorldCore;
import org.valkyrienskies.core.apigame.world.ShipWorldCore;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

import java.lang.reflect.Method;

public class RopeUtils {

    public static void createRopeConstraint(
            ServerLevel level,
            long shipA,
            long shipB,
            Vector3d localPosA,
            Vector3d localPosB,
            float maxForce,
            float ropeLength,
            float stiffness,
            float damping
    ) {
        long groundBodyId = getGroundBodyId(level);

        System.out.println("Creating rope with:");
        System.out.println("shipA: " + shipA);
        System.out.println("shipB: " + shipB);
        System.out.println("localPosA: " + localPosA);
        System.out.println("localPosB: " + localPosB);
        System.out.println("maxForce: " + maxForce);
        System.out.println("ropeLength: " + ropeLength);

        if (Math.abs(localPosA.x) > 1e6 || Math.abs(localPosA.y) > 1e6 || Math.abs(localPosA.z) > 1e6) {
            System.err.println("WARNING: Suspiciously large localPosA coordinates");
        }

        Vector3d worldPosA = transformToWorldPos(level, shipA, localPosA);
        Vector3d worldPosB = transformToWorldPos(level, shipB, localPosB);

        double actualDistance = worldPosA.distance(worldPosB);
        System.out.println("Actual world distance between points: " + actualDistance);

        if (actualDistance < 0.1 || actualDistance > 1000) {
            System.err.println("WARNING: Suspicious distance between rope points");
        }

        if (Math.abs(localPosA.x) > 1e6) {
            localPosA = new Vector3d(0, 0, 0);
            System.out.println("Adjusted localPosA to origin due to extreme values");
        }

        long finalShipA = shipA;
        long finalShipB = shipB;
        Vector3d finalLocalPosA = localPosA;
        Vector3d finalLocalPosB = localPosB;

        boolean shipBIsWorld = shipB == groundBodyId;
        if (shipA == groundBodyId && !shipBIsWorld) {
            finalShipA = shipB;
            finalShipB = shipA;
            finalLocalPosA = localPosB;
            finalLocalPosB = localPosA;
        }

        VSJointPose poseA = new VSJointPose(finalLocalPosA, new Quaterniond());
        VSJointPose poseB = new VSJointPose(finalLocalPosB, new Quaterniond());

        if (maxForce <= 0) maxForce = 10000f;
        if (ropeLength <= 0) ropeLength = (float) worldPosA.distance(worldPosB);
        if (stiffness <= 0) stiffness = 1000f;
        if (damping <= 0) damping = 50f;

        VSDistanceJoint joint = new VSDistanceJoint(
                finalShipA,
                poseA,
                finalShipB,
                poseB,
                new VSJointMaxForceTorque(maxForce, maxForce),
                0f, // Min distance
                ropeLength,
                stiffness,
                damping,
                100f // Precision
        );

        ValkyrienSkiesMod.getOrCreateGTPA(
                ValkyrienSkies.getDimensionId(level)
        ).addJoint(
                joint,
                10,
                id -> System.out.println("Successfully created rope joint with ID: " + id)
        );
    }

    private static Vector3d transformToWorldPos(ServerLevel level, long shipId, Vector3d localPos) {
        if (shipId == getGroundBodyId(level)) {
            return localPos;
        }

        Ship ship = (Ship) VSGameUtilsKt.getShipObjectWorld(level);
        if (ship == null) {
            return localPos;
        }

        Vector3d worldPos = new Vector3d();
        ship.getTransform().getShipToWorld().transformPosition(localPos, worldPos);
        return worldPos;
    }

    public static long getGroundBodyId(ServerLevel level) {
        return VSGameUtilsKt.getShipObjectWorld(level)
                .getDimensionToGroundBodyIdImmutable()
                .get(VSGameUtilsKt.getDimensionId(level));
    }
}
