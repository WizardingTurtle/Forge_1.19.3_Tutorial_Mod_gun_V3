package net.WizardingTurtle.Tutorialmod.entity;


import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.entity.projectile.BulletEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY = ENTITY_TYPES.register("bullet_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<BulletEntity>)
                    BulletEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F).build("bullet_entity"));



}
