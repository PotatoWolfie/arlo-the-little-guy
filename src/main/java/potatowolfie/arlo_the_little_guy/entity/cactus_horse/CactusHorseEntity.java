package potatowolfie.arlo_the_little_guy.entity.cactus_horse;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Markings;
import net.minecraft.world.entity.animal.equine.Variant;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class CactusHorseEntity extends Horse {

    public CactusHorseEntity(EntityType<? extends Horse> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && this.isVehicle()) {
            if (this.tickCount % 10 == 0) {
                for (Entity passenger : this.getPassengers()) {
                    passenger.hurt(this.damageSources().cactus(), 1.0F);
                }
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        ((HorseAccessBridge) this).arlo$setVariantAndMarkings(Variant.WHITE, Markings.NONE);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }
}