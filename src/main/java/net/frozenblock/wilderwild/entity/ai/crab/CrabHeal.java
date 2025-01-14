/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CrabHeal {

	@Contract(" -> new")
	public static @NotNull BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(RegisterMemoryModuleTypes.IS_UNDERGROUND),
			instance.present(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS)
		).apply(instance, (underground, digCooldown, healCooldown) -> (world, crab, l) -> {
			if (crab.getBrain().getMemory(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS).isPresent()) {
				int cooldownTicks = crab.getBrain().getMemory(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS).get();
				if (cooldownTicks > 0) {
					healCooldown.setWithExpiry(cooldownTicks - 1, 5L);
					return true;
				}
			}
			healCooldown.setWithExpiry(20, 5L);
			crab.heal(0.05F);
			return true;
		}));
	}
}
