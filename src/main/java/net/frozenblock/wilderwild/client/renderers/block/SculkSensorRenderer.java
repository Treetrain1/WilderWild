package net.frozenblock.wilderwild.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.config.WilderWildConfig;
import net.frozenblock.wilderwild.init.WWModelLayers;
import net.frozenblock.wilderwild.util.interfaces.SculkSensorTickInterface;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SculkSensorRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart base;
    private final ModelPart ne;
    private final ModelPart se;

    private static final float pi = (float) Math.PI;
    private static final float merp25 = 25 * (pi / 180);

    private static final RenderType SENSOR_LAYER = RenderType.entityCutout(WilderWild.id("textures/entity/sculk_sensor/inactive.png"));
    private static final RenderType ACTIVE_SENSOR_LAYER = RenderType.entityCutout(WilderWild.id("textures/entity/sculk_sensor/active.png"));

    public SculkSensorRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart root = ctx.bakeLayer(WWModelLayers.SCULK_SENSOR);
        this.base = root.getChild("base");
        this.se = root.getChild("se");
        this.ne = root.getChild("ne");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), PartPose.offsetAndRotation(8.0F, 0.0F, 8.0F, 0, 0.0F, pi));
        modelPartData.addOrReplaceChild("ne", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(3.0F, 8.0F, 3.0F, 0, -0.7854F, pi));
        modelPartData.addOrReplaceChild("se", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), PartPose.offsetAndRotation(3.0F, 8.0F, 13.0F, 0, 0.7854F, pi));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void render(@NotNull T entity, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        if (WilderWildConfig.MC_LIVE_SENSOR_TENDRILS.get()) {
            SculkSensorTickInterface tickInterface = ((SculkSensorTickInterface) entity);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SENSOR_LAYER);
            if (tickInterface.isActive()) {
                int prevTicks = tickInterface.getPrevAnimTicks();
                float pitch = (prevTicks + tickDelta * (tickInterface.getAnimTicks() - prevTicks)) * 0.1F;
                float animProg = (tickInterface.getAge() + tickDelta) * 2.25F;
                this.ne.xRot = pitch * ((float) Math.cos(animProg) * merp25);
                this.se.xRot = pitch * (-(float) Math.sin(animProg) * merp25);
                vertexConsumer = vertexConsumers.getBuffer(ACTIVE_SENSOR_LAYER);
            } else {
                this.ne.xRot = 0;
                this.se.xRot = 0;
            }

            this.base.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.translate(0.625, 0, 0.625);
            this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.translate(0, 0, -1.25);
            this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}