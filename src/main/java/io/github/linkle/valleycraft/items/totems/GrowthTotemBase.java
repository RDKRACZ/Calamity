package io.github.linkle.valleycraft.items.totems;

import java.util.List;

import io.github.linkle.valleycraft.init.MiscItems;
import io.github.linkle.valleycraft.init.WeaponsAndTools;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class GrowthTotemBase extends AbstractTotemBase {
    public GrowthTotemBase(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canSetWeather(World world) {
        return true;
    }

    @Override
    public void setWeather(ServerWorld world) {
        world.setWeather(MAX_DURATION, 0, false, false);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        
        showFloatingItem(world, user);

        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty() || playerEntity != null) {
                return new ItemStack(WeaponsAndTools.DRUID);
            }
        }

        return stack.isEmpty() ? new ItemStack(MiscItems.G_TOTEM) : stack;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.valley.druid_rune.tooltip").formatted(Formatting.YELLOW) );
        tooltip.add( new TranslatableText("item.valley.druid_rune.tooltip_2").formatted(Formatting.YELLOW) );
    }
}