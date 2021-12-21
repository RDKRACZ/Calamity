package net.linkle.valley.Registry.Initializers.ConfiguredFeatures;

import static net.linkle.valley.Registry.Utils.Util.register;

import java.util.ArrayList;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.linkle.valley.ValleyMain;
import net.linkle.valley.Registry.Initializers.Aquatic;
import net.linkle.valley.Registry.Initializers.ConfiguredFeatures.Gen.GlowKelpFeature;
import net.linkle.valley.Registry.Initializers.ConfiguredFeatures.Gen.SeaPatchConfig;
import net.linkle.valley.Registry.Initializers.ConfiguredFeatures.Gen.SeaPatchFeature;
import net.linkle.valley.Registry.Utils.SimpleConfig;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.HeightmapPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

public class OceanFeatures {
    /** Custom gen feature to spawn stuffs in ocean. */
    private static final SeaPatchFeature SEA_PATCH = new SeaPatchFeature();
    
    private static final ConfiguredFeature<?, ?> RED_SEAGRASS_PATCH_CONFIG = SEA_PATCH
            .configure(new SeaPatchConfig(Aquatic.RED_SEAGRASS.getDefaultState(), UniformIntProvider.create(10, 16), 9));
    
    private static final ConfiguredFeature<?, ?> CLAM_PATCH_CONFIG = SEA_PATCH
            .configure(new SeaPatchConfig(Aquatic.CLAM.getDefaultState().with(Properties.WATERLOGGED, true), UniformIntProvider.create(3, 5), 7));
    
    private static final ConfiguredFeature<?, ?> SAND_DOllAR_CONFIG = SEA_PATCH
            .configure(new SeaPatchConfig(Aquatic.SAND_DOLLAR.getDefaultState().with(Properties.WATERLOGGED, true), UniformIntProvider.create(1, 3), 5));

    public static void initialize(SimpleConfig config) {
        if (config.get("disable-features-gen", false)) {
            return;
        }
        Registry.register(Registry.FEATURE, new Identifier(ValleyMain.MOD_ID, "sea_patch"), SEA_PATCH);
        
        var vegetal = GenerationStep.Feature.VEGETAL_DECORATION;
        Predicate<BiomeSelectionContext> selection;
        ArrayList<PlacementModifier> list;
        RegistryKey<PlacedFeature> place;

        list = new ArrayList<>();
        list.add(SquarePlacementModifier.of());
        list.add(PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP);
        place = register("red_seagrass_patch", RED_SEAGRASS_PATCH_CONFIG, list);
        selection = BiomeSelectors.categories(Category.RIVER, Category.OCEAN, Category.SWAMP, Category.BEACH);
        BiomeModifications.addFeature(selection, vegetal, place);
        
        list = new ArrayList<>();
        list.add(RarityFilterPlacementModifier.of(3));
        list.add(SquarePlacementModifier.of());
        place = register("clam_patch", CLAM_PATCH_CONFIG, list);
        selection = BiomeSelectors.categories(Category.OCEAN, Category.BEACH);
        BiomeModifications.addFeature(selection, vegetal, place);
        
        list = new ArrayList<>();
        list.add(RarityFilterPlacementModifier.of(6));
        list.add(SquarePlacementModifier.of());
        list.add(PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP);
        place = register("sand_dollar_patch", SAND_DOllAR_CONFIG, list);
        selection = BiomeSelectors.categories(Category.OCEAN, Category.BEACH);
        BiomeModifications.addFeature(selection, vegetal, place);
    }

    private static boolean oceanOnly(BiomeSelectionContext context) {
        return context.getBiome().getCategory() == Category.OCEAN;
    }
}
