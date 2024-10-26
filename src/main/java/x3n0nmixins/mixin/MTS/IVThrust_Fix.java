package x3n0nmixins.mixin.MTS;

import minecrafttransportsimulator.baseclasses.Point3D;
import minecrafttransportsimulator.entities.components.AEntityF_Multipart;
import minecrafttransportsimulator.entities.instances.APart;
import minecrafttransportsimulator.entities.instances.PartEngine;
import minecrafttransportsimulator.items.components.AItemPart;
import minecrafttransportsimulator.jsondefs.JSONPartDefinition;
import minecrafttransportsimulator.mcinterface.IWrapperNBT;
import minecrafttransportsimulator.mcinterface.IWrapperPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

// MTS THRUST VECTORING FIX
@Mixin(PartEngine.class)
public abstract class IVThrust_Fix extends APart {
    public IVThrust_Fix(AEntityF_Multipart<?> entityOn, IWrapperPlayer placingPlayer, JSONPartDefinition placementDefinition, AItemPart item, IWrapperNBT data){
        super(entityOn, placingPlayer, placementDefinition, item, data);
    }

    @Final
    @Shadow
    private Point3D engineForce;

    @Redirect(method = "Lminecrafttransportsimulator/entities/instances/PartEngine;addToForceOutput(Lminecrafttransportsimulator/baseclasses/Point3D;Lminecrafttransportsimulator/baseclasses/Point3D;)D",
            at = @At(value = "INVOKE", target = "Lminecrafttransportsimulator/baseclasses/Point3D;add(Lminecrafttransportsimulator/baseclasses/Point3D;)Lminecrafttransportsimulator/baseclasses/Point3D;",
            ordinal = 2, opcode = Opcodes.PUTFIELD), remap = false)
    public Point3D addCorrectThrust(Point3D force, Point3D torque){
        // REVERT THRUST VECTORING MADNESS
        torque.y -= engineForce.z * localOffset.x + engineForce.x * localOffset.z;
        torque.z += engineForce.y * localOffset.x - engineForce.x * localOffset.y;
        return torque;
    }

//    public double addToForceOutput(Point3D force, Point3D torque) {
//        // REVERT THRUST VECTORING MADNESS
//        torque.y -= engineForce.z * localOffset.x + engineForce.x * localOffset.z;
//        torque.z += engineForce.y * localOffset.x - engineForce.x * localOffset.y;
//        // torque.add(localOffset.crossProduct(engineForce)); // NICE SHITTY CODE DON
//    }
}
