/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.mod_compat.clothconfig;

import net.frozenblock.lib.config.frozenlib_config.getter.FrozenLibConfigValues;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;

public abstract class AbstractClothConfigIntegration extends ModIntegration {

	public AbstractClothConfigIntegration() {
		super("cloth-config");
	}

	@Override
	public void init() {
	}

	public abstract boolean mcLiveSensorTendrils();

	public abstract boolean billboardTendrils();

	public abstract boolean pollenParticles();

	public abstract boolean cactusPlacement();

	public abstract boolean cactusSounds();

	public abstract boolean claySounds();

	public abstract boolean coarseDirtSounds();

	public abstract boolean cobwebSounds();

	public abstract boolean deadBushSounds();

	public abstract boolean flowerSounds();

	public abstract boolean gravelSounds();

	public abstract boolean frostedIceSounds();

	public abstract boolean leafSounds();

	public abstract boolean saplingSounds();

	public abstract boolean lilyPadSounds();

	public abstract boolean mushroomBlockSounds();

	public abstract boolean podzolSounds();

	public abstract boolean reinforcedDeepslateSounds();
	public abstract boolean sandstoneSounds();

	public abstract boolean sugarCaneSounds();

	public abstract boolean witherRoseSounds();

	public abstract boolean snowballLandingSounds();

	public abstract boolean eggLandingSounds();

	public abstract boolean enderPearlLandingSounds();

	public abstract boolean potionLandingSounds();

	public abstract int stoneChestTimer();

	public abstract boolean shriekerGargling();

	public abstract boolean soulFireSounds();

	// ENTITY

	public abstract boolean unpassableRail();

	public abstract boolean keyframeAllayDance();

	public abstract boolean angerLoopSound();

	public abstract boolean movingStareSound();

	public abstract int fireflySpawnCap();

	public abstract int jellyfishSpawnCap();

	public abstract int tumbleweedSpawnCap();

	public abstract boolean leashedTumbleweed();

	public abstract boolean wardenAttacksImmediately();

	public abstract boolean wardenCustomTendrils();

	public abstract boolean wardenDyingAnimation();

	public abstract boolean wardenEmergesFromCommand();
	public abstract boolean wardenEmergesFromEgg();

	public abstract boolean wardenSwimAnimation();

	public abstract boolean wardenBedrockSniff();

	// ITEM

	public abstract boolean hornCanSummonWarden();

	public abstract int hornLifespan();

	public abstract int hornMobDamage();

	public abstract int hornPlayerDamage();

	public abstract boolean hornShattersGlass();

	public abstract float hornSizeMultiplier();

	public abstract boolean projectileBreakParticles();

	public abstract boolean itemCooldownsSave();

	// WORLDGEN

	public abstract boolean betaBeaches();

	public abstract boolean modifyJunglePlacement();

	public abstract boolean modifySwampPlacement();

	public abstract boolean modifyMangroveSwampPlacement();

	public abstract boolean modifyWindsweptSavannaPlacement();

	public abstract boolean generateCypressWetlands();

	public abstract boolean generateJellyfishCaves();

	public abstract boolean generateMixedForest();

	public abstract boolean generateOasis();

	public abstract boolean generateWarmRiver();

	public abstract boolean generateBirchTaiga();

	public abstract boolean generateOldGrowthBirchTaiga();

	public abstract boolean generateFlowerField();

	public abstract boolean generateAridSavanna();

	public abstract boolean generateParchedForest();

	public abstract boolean generateAridForest();

	public abstract boolean generateOldGrowthSnowyTaiga();

	public abstract boolean generateBirchJungle();

	public abstract boolean generateSparseBirchJungle();

	public abstract boolean generateOldGrowthDarkForest();

	public abstract boolean generateDarkBirchForest();

	public abstract boolean generateSemiBirchForest();

	public abstract boolean generateTemperateRainforest();

	public abstract boolean generateRainforest();

	public abstract boolean dyingTrees();

	public abstract boolean fallenLogs();

	public abstract boolean wildTrees();

	public abstract boolean wildGrass();
	public abstract boolean snowBelowTrees();

	public abstract boolean newWitchHuts();

	// MISC

	public abstract boolean deepDarkAmbience();

	public abstract boolean dripstoneCavesAmbience();

	public abstract boolean lushCavesAmbience();

	public abstract boolean wilderForestMusic();

	public abstract boolean cloudMovement();
}