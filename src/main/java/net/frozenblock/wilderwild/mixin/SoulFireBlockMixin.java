package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.config.WilderWildConfig;
import net.frozenblock.wilderwild.init.WWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoulFireBlock.class)
public class SoulFireBlockMixin extends BaseFireBlock {

    private SoulFireBlockMixin(Properties properties, float fireDamage) {
        super(properties, fireDamage);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (WilderWildConfig.SOUL_FIRE_SOUNDS.get()) {
            if (random.nextInt(48) == 0) {
                level.playLocalSound(
                        (double) pos.getX() + 0.5,
                        (double) pos.getY() + 0.5,
                        (double) pos.getZ() + 0.5,
                        WWSoundEvents.BLOCK_SOUL_FIRE_AMBIENT.get(),
                        SoundSource.BLOCKS,
                        0.6F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }
        } else {
            super.animateTick(state, level, pos, random);
        }
    }

    @Shadow
    protected boolean canBurn(@NotNull BlockState state) {
		throw new AssertionError("Mixin injection failed - WilderWild SoulFireBlockMixin.");
    }

}