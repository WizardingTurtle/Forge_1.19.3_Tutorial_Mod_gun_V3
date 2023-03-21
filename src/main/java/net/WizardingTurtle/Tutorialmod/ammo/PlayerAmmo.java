package net.WizardingTurtle.Tutorialmod.ammo;

import net.minecraft.nbt.CompoundTag;

public class PlayerAmmo {
    private int ammo;
    private final int MIN_AMMO = 0;
    private final int MAX_AMMO = 6;

    public int getPlayerAmmo() {
        return ammo;
    }
    public void addAmmo(int add) {
        this.ammo = Math.min(ammo + add, MAX_AMMO);
    }
    public void subAmmo(int sub) {
        this.ammo = Math.max(ammo - sub, MIN_AMMO);
    }

    public int getPlayerMaxAmmo() {
        return MAX_AMMO;
    }
    public void copyFrom(PlayerAmmo source) {
        this.ammo = source.ammo;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("ammo", ammo);
    }
    public void loadNBTData(CompoundTag nbt) {
        ammo = nbt.getInt("ammo");
    }
}
