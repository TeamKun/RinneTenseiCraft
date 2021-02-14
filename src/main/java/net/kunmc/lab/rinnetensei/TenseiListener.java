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

        // ブタの場合
        if(entityType.equals(EntityType.PIG)){
            if(entity.getLastDamageCause().getCause().equals((EntityDamageEvent.DamageCause.FIRE)) ||
                    entity.getLastDamageCause().getCause().equals((EntityDamageEvent.DamageCause.FIRE_TICK))){
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
            if(entity.getLastDamageCause().getCause().equals((EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))){
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
        }
        if(evolveMap.get(entity.getType()).equals(EntityType.ENDER_DRAGON) ){
            EnderDragon dragon = (EnderDragon) spawnEntity;
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

    public EntityType evolveEntity(EntityType entityType){
        return EntityType.ZOMBIE;
    }

}
