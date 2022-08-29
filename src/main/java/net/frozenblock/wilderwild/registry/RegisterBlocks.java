package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.block.FrozenSignBlock;
import net.frozenblock.lib.block.FrozenWallSignBlock;
import net.frozenblock.lib.mixin.server.SignTypeAccessor;
import net.frozenblock.lib.replacements_and_lists.BonemealBehaviors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.*;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.world.gen.sapling.CypressSaplingGenerator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterItems.BAOBAB_SIGN;
import static net.frozenblock.wilderwild.registry.RegisterItems.CYPRESS_SIGN;

public final class RegisterBlocks {
    private static final MaterialColor BAOBAB_PLANKS_COLOR = MaterialColor.COLOR_ORANGE;
    private static final MaterialColor BAOBAB_BARK_COLOR = MaterialColor.COLOR_BROWN;
    private static final MaterialColor CYPRESS_PLANKS_COLOR = MaterialColor.COLOR_LIGHT_GRAY;
    private static final MaterialColor CYPRESS_BARK_COLOR = MaterialColor.STONE;

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresCorrectToolForDrops().sound(SoundType.MUD_BRICKS));

    public static void registerOtherBB() {
        // BB = Building Blocks
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), FabricBlockSettings.copy(BAOBAB_PLANKS));
    public static final Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), FabricBlockSettings.copy(CYPRESS_PLANKS));

    public static final Block BAOBAB_BUTTON = new WoodButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).color(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_BUTTON = new WoodButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).color(CYPRESS_PLANKS_COLOR));

    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).noCollission().strength(0.5F).sound(SoundType.WOOD));
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).noCollission().strength(0.5F).sound(SoundType.WOOD));

    public static final Block BAOBAB_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block CYPRESS_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion());

    public static final Block BAOBAB_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(RegisterBlocks::never));
    public static final Block CYPRESS_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_NUT = new BaobabNutBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(RegisterBlockSoundGroups.BAOBAB_NUT).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block POTTED_BAOBAB_NUT = new FlowerPotBlock(RegisterBlocks.BAOBAB_NUT, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());
    public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(BlockBehaviour.Properties.of(Material.CACTUS).randomTicks().strength(0.4F).sound(SoundType.SWEET_BERRY_BUSH).noOcclusion());
    public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());

    public static final Block BAOBAB_LEAVES = new BaobabLeaves(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(RegisterBlocks::canSpawnOnLeaves).isSuffocating(RegisterBlocks::never).isViewBlocking(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(RegisterBlocks::canSpawnOnLeaves).isSuffocating(RegisterBlocks::never).isViewBlocking(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final WoodType BAOBAB_SIGN_TYPE = SignTypeAccessor.newSignType("wilderwildbaobab");
    public static final Block BAOBAB_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), BAOBAB_SIGN_TYPE, WilderWild.MOD_ID);
    public static final Block BAOBAB_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_SIGN_TYPE, WilderWild.MOD_ID);

    public static final WoodType CYPRESS_SIGN_TYPE = SignTypeAccessor.newSignType("wilderwildcypress");
    public static final Block CYPRESS_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), CYPRESS_SIGN_TYPE, WilderWild.MOD_ID);
    public static final Block CYPRESS_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_SIGN_TYPE, WilderWild.MOD_ID);

    public static void registerWoods() {
        String baobab = "baobab";
        String cypress = "cypress";

        SignTypeAccessor.registerNew(BAOBAB_SIGN_TYPE);
        SignTypeAccessor.registerNew(CYPRESS_SIGN_TYPE);

        registerBlock(baobab + "_planks", BAOBAB_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_planks", CYPRESS_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_log", BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_log", CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_wood", BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_wood", CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_slab", BAOBAB_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_slab", CYPRESS_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_stairs", BAOBAB_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_stairs", CYPRESS_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_button", BAOBAB_BUTTON, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_button", CYPRESS_BUTTON, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_door", BAOBAB_DOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_door", CYPRESS_DOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);

        registerBlockWithoutBlockItem(baobab + "_nut", BAOBAB_NUT);
        registerBlockWithoutBlockItem("potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);
        registerBlockWithoutBlockItem("prickly_pear_cactus", PRICKLY_PEAR_CACTUS);
        registerBlock(cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);

        registerBlock(baobab + "_leaves", BAOBAB_LEAVES, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_leaves", CYPRESS_LEAVES, CreativeModeTab.TAB_DECORATIONS);

        registerBlock(baobab + "_fence", BAOBAB_FENCE, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_fence", CYPRESS_FENCE, CreativeModeTab.TAB_DECORATIONS);

        Registry.register(Registry.ITEM, WilderWild.id("baobab_sign"), BAOBAB_SIGN);
        registerBlockWithoutBlockItem(baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
        Registry.register(Registry.ITEM, WilderWild.id("cypress_sign"), CYPRESS_SIGN);
        registerBlockWithoutBlockItem(cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
    }

    // HOLLOWED LOGS
    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MaterialColor.COLOR_RED, MaterialColor.PODZOL);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MaterialColor.COLOR_LIGHT_GRAY, MaterialColor.STONE);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTab.TAB_DECORATIONS);
    }

    // SCULK
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), FabricBlockSettings.of(Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(2.0F).sound(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).lightLevel((state) -> 1)
            .sound(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveRendering((state, world, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(0.3F).noOcclusion().sound(RegisterBlockSoundGroups.ECHO_GLASS));

    public static void registerDeepDark() {
        registerBlock("sculk_stairs", SCULK_STAIRS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_slab", SCULK_SLAB, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_wall", SCULK_WALL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("osseous_sculk", OSSEOUS_SCULK, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hanging_tendril", HANGING_TENDRIL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("echo_glass", ECHO_GLASS, CreativeModeTab.TAB_DECORATIONS);
    }

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }
    
    // Mesoglea
    public static final Block MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(Material.CLAY, MaterialColor.QUARTZ).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().emissiveRendering(RegisterBlocks::always).lightLevel((state) -> 3));
    
    // MISC
    private static final Material ALGAE_MATERIAL = new FabricMaterialBuilder(MaterialColor.PLANT)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.3F).sound(RegisterBlockSoundGroups.COARSEDIRT));
    public static final Block STONE_CHEST = new StoneChestBlock(FabricBlockSettings.copy(Blocks.CHEST).sound(SoundType.DEEPSLATE).strength(35.0f, 12.0f), () -> RegisterBlockEntities.STONE_CHEST);

    // PLANTS
    public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, FabricBlockSettings.copy(Blocks.DANDELION).sound(RegisterBlockSoundGroups.FLOWER).strength(0.0F).noOcclusion());
    public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());
    public static final Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, FabricBlockSettings.copy(Blocks.DANDELION).sound(RegisterBlockSoundGroups.FLOWER).strength(0.0F).noOcclusion());
    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copy(Blocks.DANDELION).sound(RegisterBlockSoundGroups.FLOWER).strength(0.0F).noOcclusion().randomTicks(), List.of(FlowerColors.BLUE, FlowerColors.PINK, FlowerColors.PURPLE, FlowerColors.WHITE));

    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.QUARTZ).sound(SoundType.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.CRIMSON_STEM).sound(SoundType.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_PURPLE).sound(SoundType.VINE));
    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_BLUE).sound(SoundType.VINE));

    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).noOcclusion());
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).noOcclusion());

    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).sound(SoundType.WET_GRASS).strength(0.0F).noOcclusion());
    public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copy(Blocks.LILY_PAD).sound(RegisterBlockSoundGroups.LILYPAD));
    public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of(ALGAE_MATERIAL).instabreak().noOcclusion().noCollission().sound(SoundType.SLIME_BLOCK));

    public static final Block POTTED_BIG_DRIPLEAF = new FlowerPotBlock(Blocks.BIG_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());
    public static final Block POTTED_SMALL_DRIPLEAF = new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());
    public static final Block POTTED_GRASS = new FlowerPotBlock(Blocks.GRASS, FabricBlockSettings.of(Material.DECORATION).instabreak().noOcclusion());

    public static void registerPlants() {
        registerBlockWithoutBlockItem("potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
        registerBlockWithoutBlockItem("potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
        registerBlockWithoutBlockItem("potted_grass", POTTED_GRASS);
        registerBlock("seeding_dandelion", SEEDING_DANDELION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlock("carnation", CARNATION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_carnation", POTTED_CARNATION);
        registerBlock("glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("datura", DATURA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("milkweed", MILKWEED, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("cattail", CATTAIL, CreativeModeTab.TAB_DECORATIONS);
    }

    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).noOcclusion().sound(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).noOcclusion().sound(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block POLLEN_BLOCK = new PollenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.SAND).sound(SoundType.VINE));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        Registry.register(Registry.BLOCK, WilderWild.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
        Registry.register(Registry.ITEM, WilderWild.id("flowering_lily_pad"), new FloweredLilyPadItem(FLOWERING_LILY_PAD, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.BLOCK, WilderWild.id("algae"), ALGAE);
        Registry.register(Registry.ITEM, WilderWild.id("algae"), new AlgaeItem(ALGAE, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
    }

    // BLOCK FAMILIES
    public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
            .button(BAOBAB_BUTTON)
            .slab(BAOBAB_SLAB)
            .stairs(BAOBAB_STAIRS)
            .fence(BAOBAB_FENCE)
            .fenceGate(BAOBAB_FENCE_GATE)
            .pressurePlate(BAOBAB_PRESSURE_PLATE)
            .sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
            .door(BAOBAB_DOOR)
            .trapdoor(BAOBAB_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();


    public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
            .button(CYPRESS_BUTTON)
            .slab(CYPRESS_SLAB)
            .stairs(CYPRESS_STAIRS)
            .fence(CYPRESS_FENCE)
            .fenceGate(CYPRESS_FENCE_GATE)
            .pressurePlate(CYPRESS_PRESSURE_PLATE)
            .sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
            .door(CYPRESS_DOOR)
            .trapdoor(CYPRESS_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static final Block NULL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).sound(RegisterBlockSoundGroups.NULL_BLOCK)); // B) -merp

    public static final Block DISPLAY_LANTERN = new DisplayLanternBlock(FabricBlockSettings.of(Material.METAL).strength(3.5f).sound(SoundType.LANTERN).noOcclusion().lightLevel((state) -> state.getValue(RegisterProperties.LIGHT)));

    public static void registerMisc() {
        registerBlock("termite_mound", TERMITE_MOUND, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("null_block", NULL_BLOCK, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("stone_chest", STONE_CHEST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("display_lantern", DISPLAY_LANTERN, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("mesoglea", MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
    }

    public static void registerBlocks() {
        WilderWild.logWild("Registering Blocks for", WilderWild.UNSTABLE_LOGGING);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerPlants();
        registerNotSoPlants();
        registerMisc();
        registerBlockProperties();
    }

    private static void registerBlockWithoutBlockItem(String name, Block block) {
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlock(String name, Block block, CreativeModeTab tab) {
        registerBlockItem(name, block, tab);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, CreativeModeTab tab) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new FabricItemSettings().tab(tab)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MaterialColor topMapColor, MaterialColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sound(RegisterBlockSoundGroups.HOLLOWED_LOG));
    }

    public static void registerBlockProperties() {
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        registerStrippable();
        registerComposting();
        registerFlammability();
        registerFuels();
        registerBonemeal();
    }

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerStrippable() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
    }

    private static void registerComposting() {
        CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.MILKWEED_POD, 0.25F);
        CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.BAOBAB_NUT, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BLUE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PINK_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
    }

    private static void registerFlammability() {
        WilderWild.logWild("Registering Flammability for", WilderWild.UNSTABLE_LOGGING);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.POLLEN_BLOCK, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.SEEDING_DANDELION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CARNATION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CATTAIL, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.DATURA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.MILKWEED, 100, 60);

        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_BIRCH_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_ACACIA_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_JUNGLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_MANGROVE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_SPRUCE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_WALL_SIGN, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_WALL_SIGN, 5, 20);
    }

    private static void registerFuels() {
        WilderWild.logWild("Registering Fuels for", WilderWild.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(BAOBAB_FENCE.asItem(), 300);
        registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.add(CYPRESS_FENCE.asItem(), 300);
        registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
    }

    private static void registerBonemeal() {
        BonemealBehaviors.bonemeals.put(Blocks.LILY_PAD, (context, world, pos, state, face, horizontal) -> {
            WilderWild.log(Blocks.LILY_PAD, pos, "Bonemeal", WilderWild.DEV_LOGGING);
            if (!world.isClientSide) {
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                world.setBlockAndUpdate(pos, RegisterBlocks.FLOWERING_LILY_PAD.defaultBlockState());
                return true;
            }
            return false;
        });
        BonemealBehaviors.bonemeals.put(BROWN_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
            if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
                WilderWild.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderWild.DEV_LOGGING);
                if (!world.isClientSide) {
                    world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                    world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
                    return true;
                }
            }
            return false;
        });
        BonemealBehaviors.bonemeals.put(RED_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
            if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
                WilderWild.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderWild.DEV_LOGGING);
                if (!world.isClientSide) {
                    world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                    world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
                    return true;
                }
            }
            return false;
        });
        BonemealBehaviors.bonemeals.put(ALGAE, (context, world, pos, state, face, horizontal) -> {
            WilderWild.log("Algae Bonemealed @ " + pos, WilderWild.DEV_LOGGING);
            if (!world.isClientSide) {
                for (Direction offset : shuffleOffsets(world.getRandom())) {
                    BlockPos blockPos = pos.relative(offset);
                    if (world.getBlockState(blockPos).isAir() && state.getBlock().canSurvive(state, world, blockPos)) {
                        world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                        world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                        world.setBlockAndUpdate(blockPos, state);
                        return true;
                    }
                }
            }
            return false;
        });
        BonemealBehaviors.bonemeals.put(RegisterBlocks.GLORY_OF_THE_SNOW, (context, world, pos, state, face, horizontal) -> {
            if (state.getValue(RegisterProperties.FLOWER_COLOR) == FlowerColors.NONE) {
                WilderWild.log("Glory Of The Snow Bonemealed @ " + pos, WilderWild.DEV_LOGGING);
                if (!world.isClientSide && state.getBlock() instanceof GloryOfTheSnowBlock glory) {
                    world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                    world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FLOWER_COLOR, glory.COLOR_LIST.get(WilderWild.random().nextInt(glory.COLOR_LIST.size()))));
                    return true;
                }
            }
            return false;
        });
    }

    private static final List<Direction> offsets = new ArrayList<>() {{
        add(Direction.EAST);
        add(Direction.NORTH);
        add(Direction.SOUTH);
        add(Direction.WEST);
    }};

    private static List<Direction> shuffleOffsets(RandomSource random) {
        return Util.toShuffledList(offsets.stream(), random);
    }
}

