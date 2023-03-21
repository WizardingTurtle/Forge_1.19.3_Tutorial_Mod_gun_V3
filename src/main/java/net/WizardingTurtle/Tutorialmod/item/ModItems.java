package net.WizardingTurtle.Tutorialmod.item;

import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.entity.ModEntityTypes;
import net.WizardingTurtle.Tutorialmod.entity.projectile.BulletEntity;
import net.WizardingTurtle.Tutorialmod.item.Ammunition.RevolverAmmo;
import net.WizardingTurtle.Tutorialmod.item.Guns.RevolverItem;
import net.WizardingTurtle.Tutorialmod.item.Guns.SixShooter;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    public static final RegistryObject<Item> BLACKOPAL = ITEMS.register("black_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWBLACKOPAL = ITEMS.register("raw_black_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SIXSHOOTER = ITEMS.register("sixshooter", () -> new SixShooter(new Item.Properties()));
    public static final RegistryObject<Item> REVOLVER = ITEMS.register("revolveritem", () -> new CrossbowItem(new Item.Properties().durability(300)));
    public static final RegistryObject<Item> GUNREVOLVER = ITEMS.register("revolver", () -> new CrossbowItem(new Item.Properties().durability(300)));
    public static final RegistryObject<Item> KAUPENBOW = ITEMS.register("kaupenbow", () -> new CrossbowItem(new Item.Properties().durability(300)));
    public static final RegistryObject<Item> REVOLVERAMMO = ITEMS.register("revolver_ammo", () -> new RevolverAmmo(new Item.Properties(), 4.5f));

    public static void init() {
        ModEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
