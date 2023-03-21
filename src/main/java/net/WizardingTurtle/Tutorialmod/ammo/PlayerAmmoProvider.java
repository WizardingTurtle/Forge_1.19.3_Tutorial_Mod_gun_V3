package net.WizardingTurtle.Tutorialmod.ammo;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAmmoProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerAmmo> PLAYER_AMMO = CapabilityManager.get(new CapabilityToken<PlayerAmmo>() {
    });
    private PlayerAmmo ammo = null;
    private final LazyOptional<PlayerAmmo> optional = LazyOptional.of(this::createPlayerAmmo);

    private PlayerAmmo createPlayerAmmo() {
        if(this.ammo == null) {
            this.ammo = new PlayerAmmo();
        }
        return this.ammo;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_AMMO){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAmmo().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAmmo().loadNBTData(nbt);
    }
}
