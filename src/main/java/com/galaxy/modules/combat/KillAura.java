package com.galaxy.modules.combat;

import com.galaxy.Category;
import com.galaxy.Module;
import com.galaxy.settings.BoolSetting;
import com.galaxy.settings.IntSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class KillAura extends Module {
    private final IntSetting range;
    private final IntSetting attackSpeed;
    private final BoolSetting targetPlayers;
    private final BoolSetting targetMobs;
    private int attackCooldown;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        this.range = new IntSetting("Range", 4, 1, 6); // Радиус действия (1-6 блоков)
        this.attackSpeed = new IntSetting("Attack Speed", 10, 1, 20); // Скорость атаки (тиков между атаками)
        this.targetPlayers = new BoolSetting("Target Players", false); // Атаковать игроков
        this.targetMobs = new BoolSetting("Target Mobs", true); // Атаковать мобов
        this.settings.add(range);
        this.settings.add(attackSpeed);
        this.settings.add(targetPlayers);
        this.settings.add(targetMobs);
        this.attackCooldown = 0;
    }

    @Override
    public void onEnable() {
        this.attackCooldown = 0;
        System.out.println("KillAura enabled. Target Mobs: " + targetMobs.getValue() + ", Target Players: " + targetPlayers.getValue());
    }

    @Override
    public void onDisable() {
        this.attackCooldown = 0;
        System.out.println("KillAura disabled.");
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) {
            System.out.println("KillAura: Player or world is null.");
            return;
        }

        // Уменьшаем кулдаун атаки
        if (attackCooldown > 0) {
            attackCooldown--;
            System.out.println("KillAura: Cooldown active, remaining: " + attackCooldown);
            return;
        }

        // Ищем ближайшую цель
        System.out.println("KillAura: Searching for target...");
        LivingEntity target = findTarget();
        if (target == null) {
            System.out.println("KillAura: No target found.");
            return;
        }

        // Проверяем, жива ли цель и находится ли она в радиусе действия
        if (!target.isAlive()) {
            System.out.println("KillAura: Target is not alive.");
            return;
        }
        double distance = mc.player.squaredDistanceTo(target);
        if (distance > range.getValue() * range.getValue()) {
            System.out.println("KillAura: Target is too far. Distance: " + Math.sqrt(distance) + ", Range: " + range.getValue());
            return;
        }

        // Атакуем цель
        System.out.println("KillAura: Attacking target: " + target.getName().getString());
        attack(mc, target);
        attackCooldown = 20 - attackSpeed.getValue(); // 20 тиков = 1 секунда, уменьшаем в зависимости от скорости
    }

    private LivingEntity findTarget() {
        MinecraftClient mc = MinecraftClient.getInstance();
        LivingEntity closest = null;
        double closestDistance = Double.MAX_VALUE;

        System.out.println("KillAura: Scanning entities...");
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity livingEntity)) {
                System.out.println("KillAura: Skipping non-living entity: " + entity.getName().getString());
                continue;
            }
            if (entity == mc.player) {
                System.out.println("KillAura: Skipping player (self).");
                continue; // Пропускаем самого игрока
            }
            if (!livingEntity.isAlive()) {
                System.out.println("KillAura: Skipping dead entity: " + livingEntity.getName().getString());
                continue; // Пропускаем мертвых
            }
            double distance = mc.player.squaredDistanceTo(livingEntity);
            if (distance > range.getValue() * range.getValue()) {
                System.out.println("KillAura: Skipping entity too far: " + livingEntity.getName().getString() + ", Distance: " + Math.sqrt(distance));
                continue; // Пропускаем, если слишком далеко
            }

            // Проверяем, подходит ли цель по типу
            boolean isPlayer = livingEntity instanceof PlayerEntity;
            if (isPlayer && !targetPlayers.getValue()) {
                System.out.println("KillAura: Skipping player (targetPlayers disabled): " + livingEntity.getName().getString());
                continue; // Пропускаем игроков, если не выбрано
            }
            if (!isPlayer && !targetMobs.getValue()) {
                System.out.println("KillAura: Skipping mob (targetMobs disabled): " + livingEntity.getName().getString());
                continue; // Пропускаем мобов, если не выбрано
            }

            // Проверяем, ближе ли эта цель
            if (distance < closestDistance) {
                closest = livingEntity;
                closestDistance = distance;
                System.out.println("KillAura: Found potential target: " + closest.getName().getString() + ", Distance: " + Math.sqrt(distance));
            }
        }

        return closest;
    }

    private void attack(MinecraftClient mc, LivingEntity target) {
        // Поворачиваем игрока к цели
        System.out.println("KillAura: Rotating to target...");
        lookAt(mc.player, target);

        // Проверяем, смотрит ли игрок на цель
        Vec3d lookVec = mc.player.getRotationVec(1.0f); // Вектор направления взгляда игрока
        Vec3d toTarget = target.getEyePos().subtract(mc.player.getEyePos()).normalize(); // Вектор от игрока к цели
        double dotProduct = lookVec.dotProduct(toTarget); // Скалярное произведение (косинус угла между векторами)
        System.out.println("KillAura: Dot product (alignment): " + dotProduct);
        if (dotProduct < 0.98) { // Если угол больше ~11 градусов (cos(11°) ≈ 0.98)
            System.out.println("KillAura: Player is not looking at target! Dot product too low.");
        }

        // Атакуем
        System.out.println("KillAura: Sending attack...");
        mc.interactionManager.attackEntity(mc.player, target);

        // Проверяем кулдаун атаки игрока
        if (mc.player.getAttackCooldownProgress(0.5f) < 1.0f) {
            System.out.println("KillAura: Attack on cooldown! Progress: " + mc.player.getAttackCooldownProgress(0.5f));
        }

        // Анимация удара
        System.out.println("KillAura: Swinging hand...");
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private void lookAt(PlayerEntity player, LivingEntity target) {
        // Получаем позицию глаз игрока и цели
        Vec3d playerEyePos = player.getEyePos();
        Vec3d targetPos = target.getEyePos();

        // Вычисляем разницу между позицией цели и игрока
        double dx = targetPos.x - playerEyePos.x;
        double dy = targetPos.y - playerEyePos.y;
        double dz = targetPos.z - playerEyePos.z;

        // Вычисляем yaw (горизонтальный угол)
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        if (yaw < 0) {
            yaw += 360f;
        }

        // Вычисляем pitch (вертикальный угол)
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, distanceXZ));

        // Устанавливаем углы поворота игрока
        System.out.println("KillAura: Player position: " + playerEyePos);
        System.out.println("KillAura: Target position: " + targetPos);
        System.out.println("KillAura: Calculated yaw: " + yaw + ", pitch: " + pitch);
        player.setYaw(yaw);
        player.setPitch(pitch);

        // Дополнительно синхронизируем углы с сервером
        player.setHeadYaw(yaw);
        player.setBodyYaw(yaw);
    }
}