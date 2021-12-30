package io.github.linkle.valleycraft.items.food;

import io.github.linkle.valleycraft.mixins.ItemSettingsAccessor;
import io.github.linkle.valleycraft.utils.FoodStatusEffect;
import org.jetbrains.annotations.Nullable;

import io.github.linkle.valleycraft.init.ItemGroups;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class FoodItemBase extends Item {
    
    public FoodItemBase(Settings settings) {
        super(settings);
    }
    
    public FoodItemBase(Settings settings, int hunger, float saturationModifier) {
        this(settings, hunger, saturationModifier, false);
    }
    
    public FoodItemBase(Settings settings, int hunger, float saturationModifier, boolean isMeat) {
        this(settings, hunger, saturationModifier, isMeat, null);
    }
    
    public FoodItemBase(Settings settings, int hunger, float saturationModifier, @Nullable FoodStatusEffect effects) {
        this(settings, hunger, saturationModifier, false, effects);
    }
    
    public FoodItemBase(Settings settings, int hunger, float saturationModifier, boolean isMeat, @Nullable FoodStatusEffect effects) {
        super(defaultGroup(settings).food(newFoodComponent(hunger, saturationModifier, isMeat, effects)));
    }
    
    /** Sets default item group if there's no item group. */
    protected static Settings defaultGroup(Settings settings) {
        if (((ItemSettingsAccessor)settings).getGroup() == null) {
            settings.group(ItemGroups.COOKING_GROUP);
        }
        return settings;
    }
    
    protected static FoodComponent newFoodComponent(int hunger, float saturationModifier, boolean isMeat, @Nullable FoodStatusEffect statusEffects) {
        var builder = new FoodComponent.Builder();
        builder.hunger(hunger);
        builder.saturationModifier(saturationModifier);
        
        if (isMeat) {
            builder.meat();
        }
        
        if (statusEffects != null) {
            statusEffects.build(builder);
        }
        
        return builder.build();
    }
}
