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

package net.frozenblock.wilderwild.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.entity.AncientHornVibration;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
	private static final float PI = (float) Math.PI;
	private static final float BONE_PITCH_YAW = 1.57079632F;
	private static final float PULSE_2_EXTRA = 8.0F / 1.5F;
	private static final float PULSE_3_EXTRA = 8.0F / 3.0F;
	private final ModelPart bone;
	private final ModelPart front;
	private final ModelPart middle;
	private final ModelPart back;
	public AncientHornVibration projectile;
	public float partialTick;

	public AncientHornProjectileModel(@NotNull ModelPart root) {
		super(FrozenRenderType::entityTranslucentEmissiveFixed);
		this.bone = root.getChild("bone");
		this.front = bone.getChild("front");
		this.middle = bone.getChild("middle");
		this.back = bone.getChild("back");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, BONE_PITCH_YAW, BONE_PITCH_YAW, 0));
		bone.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
		bone.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		bone.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float aliveDelta = this.projectile.getAliveTicks() + this.partialTick;

		float pulse = (((float) Math.sin((aliveDelta * PI) * 0.2F) * 0.16666667F) + 1);
		float pulse2 = (((float) Math.sin(((aliveDelta + PULSE_2_EXTRA) * PI) * 0.2F) * 0.16666667F) + 1);
		float pulse3 = (((float) Math.sin(((aliveDelta + PULSE_3_EXTRA) * PI) * 0.2F) * 0.16666667F) + 1);

		this.front.xScale = pulse;
		this.front.yScale = pulse;
		this.front.z = pulse3 * 2.0F - 6.0F;

		this.middle.xScale = pulse2;
		this.middle.yScale = pulse2;
		this.middle.z = pulse * 2.0F - 2.0F;

		this.back.xScale = pulse3;
		this.back.yScale = pulse3;
		this.back.z = pulse2 * 2.0F + 2.0F;

		this.bone.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
