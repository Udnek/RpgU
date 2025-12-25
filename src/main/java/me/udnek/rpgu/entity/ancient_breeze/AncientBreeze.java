package me.udnek.rpgu.entity.ancient_breeze;

import io.papermc.paper.event.entity.EntityMoveEvent;
import io.papermc.paper.event.player.PlayerNameEntityEvent;
import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntity;
import me.udnek.coreu.custom.entitylike.entity.CustomEntity;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomTickingEntityType;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class AncientBreeze extends ConstructableCustomEntity<Breeze> {

    protected double step = 1;
    protected List<Shield> shields = new ArrayList<>();
    public static final NamespacedKey ALIVE_SHIELDS_KEY = new NamespacedKey(RpgU.getInstance(), "alive_shields");
    protected int shieldAmount = 0;
    protected int splashAttackRadius = 5;
    protected BossBar bossBar;
    public static final int ANCIENT_BREEZE_HP = 200;

    @Override
    public @Positive int getTickDelay() {
        return 2;
    }

    @Override
    public void load(@NotNull Entity entity) {
        super.load(entity);

        byte[] aliveShields = entity.getPersistentDataContainer().get(ALIVE_SHIELDS_KEY, PersistentDataType.BYTE_ARRAY);
        if (aliveShields == null) {
            aliveShields = new byte[shieldAmount];
            for (int i = 0; i < shieldAmount; i++) {
                aliveShields[i] = (byte) 1;
            }
        }
        entity.getPersistentDataContainer().set(ALIVE_SHIELDS_KEY, PersistentDataType.BYTE_ARRAY, aliveShields);

        for (int i = 0; i < shieldAmount; i++) {
            Shield shield = new Shield(entity.getLocation(), 360 / shieldAmount * i, 1, 20, this);
            shields.add(shield);
            if (aliveShields[i] == 1) shield.spawnShield();
        }

        bossBar = BossBar.bossBar(Objects.requireNonNull(entity.custom.Name()), (float) (((Breeze) entity).getHealth() / ANCIENT_BREEZE_HP),
                BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
    }

    @Override
    public void unload() {
        super.unload();
        shields.forEach(Shield::remove);

        bossBar.viewers().forEach(viewer -> ((Player) viewer).hideBossBar(bossBar));
    }

    @Override
    public void delayedTick() {
        step++;
        shields.forEach(shield -> shield.teleportEntity(entity.getLocation(), step));


        List<Player> playerInRadius = Utils.livingEntitiesInRadius(entity.getLocation(), 238).stream().filter(livingEntity -> livingEntity instanceof Player).map(livingEntity -> (Player) livingEntity).toList();
        playerInRadius.forEach(player -> player.showBossBar(bossBar));

        for (BossBarViewer viewer : bossBar.viewers()) {
            if (!playerInRadius.contains(((Player) viewer))) ((Player) viewer).hideBossBar(bossBar);
        }

    }

    public void onEntityHit(DamageEvent event){
        if (event.getState() != DamageEvent.State.AFTER_EQUIPMENT_RECEIVES) return;
        double hp = entity.getHealth() - event.getDamageInstance().getDamage().getTotal();
        if (hp  < 0) hp = 0;
        bossBar.progress((float) (hp / ANCIENT_BREEZE_HP));
    }

    public void onEntityRename(PlayerNameEntityEvent event){
        Component name = event.getName();
        if (name == null) return;
        bossBar.name(name);
    }

    public void onEntityMove(EntityMoveEvent event) {}

    public void checkAliveShields() {
        byte[] bytes = new byte[shieldAmount];
        IntStream.range(0, shieldAmount).filter(i -> shields.get(i).isAlive()).forEach(i -> bytes[i] = (byte) 1);
        entity.getPersistentDataContainer().set(ALIVE_SHIELDS_KEY, PersistentDataType.BYTE_ARRAY, bytes);
    }

    public void onEntityShoot() {
        LivingEntity target = entity.getTarget();
        if (target == null) throw new RuntimeException("It's impossible. onEntityShoot target == null");
        Location targetLocationEye = target.getEyeLocation();
        Location entityLocationEye = entity.getEyeLocation();
        Vector direction = targetLocationEye.toVector().subtract(entityLocationEye.toVector()).normalize();

        double distance = entityLocationEye.distance(targetLocationEye);
        if (distance <= splashAttackRadius) {
            splashAttack(splashAttackRadius, distance, direction);
            return;
        }

        RayTraceResult rayTraceResult = entity.getWorld().rayTraceBlocks(entity.getEyeLocation(), direction, distance, FluidCollisionMode.NEVER, false);
        if (rayTraceResult != null && rayTraceResult.getHitBlock() != null) {
            explosiveAttack(rayTraceResult.getHitBlock().getLocation());
            return;
        }

        dropAttack(target);
    }

    protected void explosiveAttack(@NotNull Location location){
        World world = location.getWorld();
        Location eyeLocation = entity.getEyeLocation();
        Transformation transformation = new Transformation(
                new Vector3f(0, 0, 0),
                new Quaternionf().rotationTo(eyeLocation.toVector().toVector3f(), location.toVector().setY(location.getY() + 5).toVector3f()),
                new Vector3f( 0.1f, 0.1f, (float) eyeLocation.distance(location)),
                new Quaternionf()
        );

        world.spawn(eyeLocation, BlockDisplay.class, blockDisplay -> {
            blockDisplay.setBlock(Material.SLIME_BLOCK.createBlockData());
            blockDisplay.setTransformation(transformation);
            new BukkitRunnable() {
                @Override
                public void run() {
                    blockDisplay.remove();
                }
            }.runTaskLater(RpgU.getInstance(), 20);
        });
        world.createExplosion(location, 4, false, true, entity);
    }

    protected void splashAttack(int radius, double distance, @NotNull Vector vector) {
        Collection<LivingEntity> livingEntities = Utils.livingEntitiesInRadiusIntersects(entity.getLocation(), radius);
        for (LivingEntity livingEntity : livingEntities) {
            CustomEntity ticking = CustomEntityType.getTicking(livingEntity);
            if (livingEntity == entity || (ticking != null && ticking.getType() == EntityTypes.ANCIENT_BREEZE_SHIELD)) continue;

            DamageUtils.damage(livingEntity, new Damage(Damage.Type.PHYSICAL, 8), DamageSource.builder(DamageType.MOB_ATTACK).build());
            livingEntity.setVelocity(vector.multiply(radius - distance).setY(0.4));
            Effects.STUN_EFFECT.applyInvisible(livingEntity, 20 * 5, 0);

            Location location = Utils.rayTraceBlockUnder(entity.getLocation());
            if (location == null) location = entity.getLocation();


            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -radius; y <= radius; y++) {
                        Location blockLocation = location.clone().toCenterLocation().add(x, y, z);
                        if (blockLocation.distance(entity.getLocation()) > 5) continue;
                        me.udnek.coreu.util.Utils.sendBlockDamageForAllPlayers(blockLocation, 0.6F, 0.1F, 2);
                    }
                }
            }
        }
    }

    protected void dropAttack(@NotNull LivingEntity targetEntity) {
        Location fallingBlockLocation = targetEntity.getLocation().toCenterLocation().clone().add(0, 4, 0);
        targetEntity.getWorld().spawn(fallingBlockLocation, FallingBlock.class, block -> {
            block.setBlockData(Material.STONE.createBlockData());
            block.setDropItem(false);
            block.setCancelDrop(true);
            block.setHurtEntities(true);
            block.setVelocity(new Vector(0, -0.3, 0));
            block.setDamagePerBlock(4f);
        });
    }

    @Override
    public @NotNull CustomTickingEntityType<?> getType() {
        return EntityTypes.ANCIENT_BREEZE;
    }

    public static class Shield {
        protected Piglin shieldEntity = null;
        protected final Location location;
        protected final int angleOffset;
        protected final int radius;
        protected double partOfCircleByStep;
        protected AncientBreeze ancientBreeze;

        public Shield(@NotNull Location location, int angleOffset, int radius, double partOfCircleByStep, @NotNull AncientBreeze ancientBreeze) {
            this.location = location.clone();
            this.angleOffset = angleOffset;
            this.radius = radius;
            this.partOfCircleByStep = partOfCircleByStep / 2;
            this.ancientBreeze = ancientBreeze;
        }

        public void remove(){
            if (shieldEntity == null) return;
            shieldEntity.remove();
        }

        public boolean isAlive() {
            if (shieldEntity == null) return false;
            return shieldEntity.isValid();
        }

        public void teleportEntity(@NotNull Location livingEntityLocation, double step) {
            if (shieldEntity == null) return;
            shieldEntity.teleport(getLocationNearbyEntity(livingEntityLocation, step));
        }

        public @NotNull Location getLocationNearbyEntity(@NotNull Location livingEntityLocation, double step) {
            double x = livingEntityLocation.getX() + radius * Math.cos(Math.PI * step / partOfCircleByStep + Math.toRadians(angleOffset));
            double z = livingEntityLocation.getZ() + radius * Math.sin(Math.PI * step / partOfCircleByStep + Math.toRadians(angleOffset));
            return new Location(livingEntityLocation.getWorld(), x, livingEntityLocation.getY(), z);
        }

        public void spawnShield() {
            AncientBreezeShield ancientBreezeShield = EntityTypes.ANCIENT_BREEZE_SHIELD.spawnAndGet(getLocationNearbyEntity(location.clone(), 0));
            ancientBreezeShield.setSpawnedBy(ancientBreeze);
            this.shieldEntity = ancientBreezeShield.getReal();
        }
    }
}
