package net.kunmc.lab.rinnetensei;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.HashMap;
import java.util.Random;

public class TenseiListener implements Listener {

    public static HashMap<EntityType, EntityType> evolveMap;
    public static boolean isEnabled = false;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(!isEnabled){
            return;
        }

        Entity entity = e.getEntity();
        Location loc = entity.getLocation();
        EntityType entityType = entity.getType();

        if(entity instanceof Player){
            return;
        }

        Random random = new Random();
        int num = random.nextInt(20);
        if(num == 0){
            loc.getWorld().spawnEntity(loc, EntityType.ILLUSIONER);
            return;
        }

        // ヴェックスの場合
        if(entityType.equals(EntityType.VEX)){
            if(num <= 4){
                loc.getWorld().spawnEntity(loc, EntityType.PIG);
            }else if(num <= 9){
                loc.getWorld().spawnEntity(loc, EntityType.COW);
            }else if(num <= 14){
                loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
            }else{
                loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
            }
            return;
        }
        boolean isBurn = false;
        EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
        try{
            cause = entity.getLastDamageCause().getCause();
            isBurn = cause.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK);
        }catch(Exception ignored){

        }

        if(entityType.equals(EntityType.ZOMBIE)){
            if(isBurn){
                PigZombie zombiePig = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
                zombiePig.setAngry(true);
                return;
            }
            if(cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) ||
                    cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ){
                loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
                return;
            }
            loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
        }

        // ブタの場合
        if(entityType.equals(EntityType.PIG)){
            if(isBurn){
                PigZombie zombiePig = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
                zombiePig.setAngry(true);
                return;
            }
        }
        // クリーパーの場合
        if(entityType.equals(EntityType.CREEPER)){
            Creeper creeper = (Creeper) entity;
            if(creeper.isPowered()){
                SkeletonHorse horse = (SkeletonHorse) loc.getWorld().spawnEntity(loc, EntityType.SKELETON_HORSE);
                horse.setTrap(true);
                return;
            }
            if(cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                    cause.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK) ||
                    cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
               Creeper creeper2 = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
               creeper2.setPowered(true);
               return;
            }
        }

        Entity spawnEntity = loc.getWorld().spawnEntity(loc, evolveMap.get(entity.getType()));

        // ボスのヘルス調整
        if(evolveMap.get(entity.getType()).equals(EntityType.WITHER) ){
            Wither wither = (Wither) spawnEntity;
            wither.setHealth(100D);
            return;
        }
        if(evolveMap.get(entity.getType()).equals(EntityType.ENDER_DRAGON) ){
            EnderDragon dragon = (EnderDragon) spawnEntity;
            dragon.setAI(true);
            dragon.setPhase(EnderDragon.Phase.CIRCLING);
            dragon.setHealth(100D);
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e){
        if(!isEnabled){
            return;
        }
        e.setCancelled(true);
    }

}
