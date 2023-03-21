package net.WizardingTurtle.Tutorialmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.tutorialmod.tutorial";
    public static final String KEY_RELOAD_AMMO = "key.tutorialmod.reload_ammo";

    public static final KeyMapping RELOADING_KEY = new KeyMapping(KEY_RELOAD_AMMO, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_TUTORIAL);


}
