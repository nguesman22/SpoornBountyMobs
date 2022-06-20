package org.spoorn.spoornbountymobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spoorn.spoornbountymobs.entity.component.EntityDataComponent;
import org.spoorn.spoornbountymobs.util.SpoornBountyMobsUtil;

@Mixin(value = ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;
    
    /**
     * This redirects the squaredDistanceTo() and divides by squared scale, instead of modifying the right-hand side
     * of the expression `...squaredDistanceTo(Entity) < MAX_SQUARED_DISTANCE` to be compatible with mods that modify 
     * that constant such as JamiesWhiteShirt/reach-entity-attributes:
     * https://github.com/JamiesWhiteShirt/reach-entity-attributes/blob/1.18.2/src/main/java/com/jamieswhiteshirt/reachentityattributes/mixin/ServerPlayNetworkHandlerMixin.java#L22-L27
     *
     * Various mods such as Bewitchment make use of that library.
     */
    @Redirect(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D"))
    private double scaleMaxDistanceForInteractingWithEntity(Entity instance, Vec3d vector) {
        double original = instance.squaredDistanceTo(vector);
        if (SpoornBountyMobsUtil.entityIsHostileAndHasBounty(instance)) {
            EntityDataComponent entityDataComponent = SpoornBountyMobsUtil.getSpoornEntityDataComponent(instance);
            float scale = entityDataComponent.getSpoornBountyTier().getMobSizeScale();
            if (scale > 1) {
                return original / scale / scale;
            }
        }
        
        return original;
    }
}
