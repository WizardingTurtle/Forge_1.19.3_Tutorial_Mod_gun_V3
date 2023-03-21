package net.WizardingTurtle.Tutorialmod.client;

public class ClientAmmoData {
    private static int playerAmmo;
    public static void set(int ammo) {
        ClientAmmoData.playerAmmo = ammo;
    }
    public static int getPlayerAmmo() {
        return playerAmmo;
    }
    public static int getPlayerMissingAmmo() {
        return 6 - playerAmmo;
    }
}
