package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.entity.api.SilentTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;

public class ChestBubbleTicker extends SilentTicker {

	public ChestBubbleTicker(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	public ChestBubbleTicker(EntityType<?> entityType, Level level, BlockPos pos) {
		super(entityType, level);
		this.setPos(Vec3.atCenterOf(pos));
	}

	public static void createAndSpawn(EntityType<?> entityType, Level level, BlockPos pos) {
		ChestBubbleTicker chestBubbleTicker = new ChestBubbleTicker(entityType, level, pos);
		level.addFreshEntity(chestBubbleTicker);
	}

	@Override
	public void tick(Level level, Vec3 vec3, BlockPos pos, int ticks) {
		if (ticks <= 5) {
			if (level instanceof ServerLevel server) {
				BlockState state = level.getBlockState(pos);
				if (level.getBlockEntity(pos) instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
					if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
						double additionalX = 0;
						double additionalZ = 0;
						if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
							Direction direction = ChestBlock.getConnectedDirection(state);
							additionalX += (double) direction.getStepX() * 0.125;
							additionalZ += (double) direction.getStepZ() * 0.125;
						}
						server.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5 + additionalX, pos.getY() + 0.625, pos.getZ() + 0.5 + additionalZ, level.random.nextInt(4, 10), 0.21875F, 0, 0.21875F, 0.2D);
					} else {
						this.discard();
					}
				} else {
					this.discard();
				}
			} else {
				this.discard();
			}
		} else {
			this.discard();
		}
	}

}