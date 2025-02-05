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

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.lib.tag.api.FrozenItemTags;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

final class WWItemTagProvider extends FabricTagProvider.ItemTagProvider {

	WWItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@NotNull
	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, new ResourceLocation(id));
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(getTag("c:stripped_logs"))
			.add(RegisterBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(RegisterBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(RegisterBlocks.STRIPPED_PALM_LOG.asItem());

		this.getOrCreateTagBuilder(getTag("c:stripped_wood"))
			.add(RegisterBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.add(RegisterBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.add(RegisterBlocks.STRIPPED_PALM_WOOD.asItem());

		this.getOrCreateTagBuilder(FrozenItemTags.ALWAYS_SAVE_COOLDOWNS)
			.add(RegisterItems.ANCIENT_HORN);

		this.getOrCreateTagBuilder(WilderItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM)
			.addOptional(WilderSharedConstants.id("brown_shelf_fungus"));

		this.getOrCreateTagBuilder(WilderItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM)
			.addOptional(WilderSharedConstants.id("red_shelf_fungus"));
	}
}
