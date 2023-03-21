package net.WizardingTurtle.Tutorialmod;

import com.mojang.logging.LogUtils;
import net.WizardingTurtle.Tutorialmod.entity.ModEntityTypes;
import net.WizardingTurtle.Tutorialmod.item.ModCreativeModeTabs;
import net.WizardingTurtle.Tutorialmod.item.ModItems;
import net.WizardingTurtle.Tutorialmod.particle.ModParticles;
import net.WizardingTurtle.Tutorialmod.util.ModItemProperties;
import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "tutorialmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public TutorialMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        modEventBus.addListener(this::clientSetup);

        ModParticles.register(modEventBus);

        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        ModItemProperties.addCustomItemProperties();
    }
    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BLACKOPAL);
            event.accept(ModItems.RAWBLACKOPAL);
        }

        if(event.getTab() == ModCreativeModeTabs.Tutorial_Tab) {
            event.accept(ModItems.BLACKOPAL);
            event.accept(ModItems.RAWBLACKOPAL);

            event.accept(ModItems.REVOLVER);
            event.accept(ModItems.GUNREVOLVER);
            event.accept(ModItems.SIXSHOOTER);

            event.accept(ModItems.REVOLVERAMMO);
        }
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
