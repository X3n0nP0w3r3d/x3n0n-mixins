package x3n0nmixins.mixin.MTS;

import minecrafttransportsimulator.baseclasses.Point3D;
import minecrafttransportsimulator.entities.components.AEntityF_Multipart;
import minecrafttransportsimulator.entities.instances.APart;
import minecrafttransportsimulator.entities.instances.PartPropeller;
import minecrafttransportsimulator.items.components.AItemPart;
import minecrafttransportsimulator.jsondefs.JSONPartDefinition;
import minecrafttransportsimulator.mcinterface.IWrapperNBT;
import minecrafttransportsimulator.mcinterface.IWrapperPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PartPropeller.class)
public abstract class IVThrust_FixPropeller extends APart {
    public IVThrust_FixPropeller(AEntityF_Multipart<?> entityOn, IWrapperPlayer placingPlayer, JSONPartDefinition placementDefinition, AItemPart item, IWrapperNBT data) {
        super(entityOn, placingPlayer, placementDefinition, item, data);
    }

    @Final
    @Shadow
    private Point3D propellerForce;

    @Redirect(method = "Lminecrafttransportsimulator/entities/instances/PartPropeller;addToForceOutput(Lminecrafttransportsimulator/baseclasses/Point3D;Lminecrafttransportsimulator/baseclasses/Point3D;)D",
            at = @At(value = "INVOKE", target = "Lminecrafttransportsimulator/baseclasses/Point3D;add(Lminecrafttransportsimulator/baseclasses/Point3D;)Lminecrafttransportsimulator/baseclasses/Point3D;",
                    ordinal = 1, opcode = Opcodes.PUTFIELD), remap = false)
    public Point3D addCorrectThrustPropeller(Point3D force, Point3D torque) {
        // REVERT THRUST VECTORING MADNESS
        torque.y -= propellerForce.z * localOffset.x;
        torque.z += propellerForce.y * localOffset.x;
        return torque;
    }
}
