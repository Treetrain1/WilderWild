package net.frozenblock.wilderwild.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovingLoopingFadingDistanceSoundEntityManager {
    private final ArrayList<FadingDistanceSoundLoopNBT> sounds = new ArrayList();
    public final LivingEntity entity;

    public MovingLoopingFadingDistanceSoundEntityManager(LivingEntity entity) {
        this.entity = entity;
    }

    public void load(CompoundTag nbt) {
        if (nbt.contains("frozenDistanceSounds", 9)) {
            this.sounds.clear();
            DataResult<List<FadingDistanceSoundLoopNBT>> var10000 = MovingLoopingFadingDistanceSoundEntityManager.FadingDistanceSoundLoopNBT.CODEC.listOf().parse(new Dynamic(NbtOps.INSTANCE, nbt.getList("frozenDistanceSounds", 10)));
            Logger var10001 = WilderWild.LOGGER4;
            Objects.requireNonNull(var10001);
            Objects.requireNonNull(var10001);
            Optional<List<FadingDistanceSoundLoopNBT>> list = var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List<FadingDistanceSoundLoopNBT> allSounds = (List)list.get();
                this.sounds.addAll(allSounds);
            }
        }

    }

    public void save(CompoundTag nbt) {
        DataResult<Tag> var10000 = MovingLoopingFadingDistanceSoundEntityManager.FadingDistanceSoundLoopNBT.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.sounds);
        Logger var10001 = WilderWild.LOGGER4;
        Objects.requireNonNull(var10001);
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("frozenDistanceSounds", cursorsNbt);
        });
    }

    public void addSound(ResourceLocation soundID, ResourceLocation soundID2, SoundSource category, float volume, float pitch, ResourceLocation restrictionId, float fadeDist, float maxDist) {
        this.sounds.add(new FadingDistanceSoundLoopNBT(soundID, soundID2, category, volume, pitch, restrictionId, fadeDist, maxDist));
    }

    public ArrayList<FadingDistanceSoundLoopNBT> getSounds() {
        return this.sounds;
    }

    public void tick() {
        if (!this.sounds.isEmpty()) {
            ArrayList<FadingDistanceSoundLoopNBT> soundsToRemove = new ArrayList();
            Iterator var2 = this.sounds.iterator();

            while(var2.hasNext()) {
                FadingDistanceSoundLoopNBT nbt = (FadingDistanceSoundLoopNBT)var2.next();
                SoundPredicate.LoopPredicate<LivingEntity> predicate = SoundPredicate.getPredicate(nbt.restrictionID);
                if (!predicate.test(this.entity)) {
                    soundsToRemove.add(nbt);
                    predicate.onStop(this.entity);
                }
            }

            this.sounds.removeAll(soundsToRemove);
        }

    }

    public static class FadingDistanceSoundLoopNBT {
        public final ResourceLocation soundEventID;
        public final ResourceLocation sound2EventID;
        public final String categoryOrdinal;
        public final float volume;
        public final float pitch;
        public final float fadeDist;
        public final float maxDist;
        public final ResourceLocation restrictionID;
        public static final Codec<FadingDistanceSoundLoopNBT> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ResourceLocation.CODEC.fieldOf("soundEventID").forGetter(FadingDistanceSoundLoopNBT::getSoundEventID), ResourceLocation.CODEC.fieldOf("sound2EventID").forGetter(FadingDistanceSoundLoopNBT::getSound2EventID), Codec.STRING.fieldOf("categoryOrdinal").forGetter(FadingDistanceSoundLoopNBT::getOrdinal), Codec.FLOAT.fieldOf("volume").forGetter(FadingDistanceSoundLoopNBT::getVolume), Codec.FLOAT.fieldOf("pitch").forGetter(FadingDistanceSoundLoopNBT::getPitch), ResourceLocation.CODEC.fieldOf("restrictionID").forGetter(FadingDistanceSoundLoopNBT::getRestrictionID), Codec.FLOAT.fieldOf("fadeDist").forGetter(FadingDistanceSoundLoopNBT::getFadeDist), Codec.FLOAT.fieldOf("maxDist").forGetter(FadingDistanceSoundLoopNBT::getMaxDist)).apply(instance, FadingDistanceSoundLoopNBT::new));

        public FadingDistanceSoundLoopNBT(ResourceLocation soundEventID, ResourceLocation sound2EventID, String ordinal, float vol, float pitch, ResourceLocation restrictionID, float fadeDist, float maxDist) {
            this.soundEventID = soundEventID;
            this.sound2EventID = sound2EventID;
            this.categoryOrdinal = ordinal;
            this.volume = vol;
            this.pitch = pitch;
            this.restrictionID = restrictionID;
            this.fadeDist = fadeDist;
            this.maxDist = maxDist;
        }

        public FadingDistanceSoundLoopNBT(ResourceLocation soundEventID, ResourceLocation sound2EventID, SoundSource category, float vol, float pitch, ResourceLocation restrictionID, float fadeDist, float maxDist) {
            this.soundEventID = soundEventID;
            this.sound2EventID = sound2EventID;
            this.categoryOrdinal = category.toString();
            this.volume = vol;
            this.pitch = pitch;
            this.restrictionID = restrictionID;
            this.fadeDist = fadeDist;
            this.maxDist = maxDist;
        }

        public ResourceLocation getSoundEventID() {
            return this.soundEventID;
        }

        public ResourceLocation getSound2EventID() {
            return this.sound2EventID;
        }

        public String getOrdinal() {
            return this.categoryOrdinal;
        }

        public float getVolume() {
            return this.volume;
        }

        public float getPitch() {
            return this.pitch;
        }

        public float getFadeDist() {
            return this.fadeDist;
        }

        public float getMaxDist() {
            return this.maxDist;
        }

        public ResourceLocation getRestrictionID() {
            return this.restrictionID;
        }
    }
}