package net.kunmc.lab.rinnetensei;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RinneTenseiCraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TenseiListener.evolveMap = new HashMap<EntityType, EntityType>(){
            {
                put(EntityType.BEE, EntityType.PHANTOM);
                put(EntityType.BAT, EntityType.PHANTOM);
                put(EntityType.CHICKEN, EntityType.PHANTOM);
                put(EntityType.PARROT, EntityType.PHANTOM);
                put(EntityType.PHANTOM, EntityType.GHAST);
                put(EntityType.GHAST, EntityType.ENDER_DRAGON);

                put(EntityType.COW, EntityType.MUSHROOM_COW);
                put(EntityType.MUSHROOM_COW, EntityType.RAVAGER);

                put(EntityType.SQUID, EntityType.PUFFERFISH);
                put(EntityType.DOLPHIN, EntityType.PUFFERFISH);
                put(EntityType.TURTLE, EntityType.PUFFERFISH);
                put(EntityType.SALMON, EntityType.PUFFERFISH);
                put(EntityType.COD, EntityType.PUFFERFISH);
                put(EntityType.TROPICAL_FISH, EntityType.PUFFERFISH);
                put(EntityType.PUFFERFISH, EntityType.DROWNED);
                put(EntityType.DROWNED, EntityType.GUARDIAN);
                put(EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN);

                put(EntityType.PIG, EntityType.CREEPER);
                put(EntityType.CREEPER, EntityType.ENDERMAN);
                put(EntityType.ENDERMAN, EntityType.ENDER_DRAGON);
                put(EntityType.HORSE, EntityType.SKELETON_HORSE);
                put(EntityType.DONKEY, EntityType.SKELETON_HORSE);
                put(EntityType.MULE, EntityType.SKELETON_HORSE);
                put(EntityType.SKELETON_HORSE, EntityType.WITHER_SKELETON);

                put(EntityType.SHEEP, EntityType.SKELETON);
                put(EntityType.SKELETON, EntityType.WITHER_SKELETON);
                put(EntityType.PIG_ZOMBIE, EntityType.WITHER_SKELETON);
                put(EntityType.WITHER_SKELETON, EntityType.BLAZE);
                put(EntityType.BLAZE, EntityType.WITHER);

                put(EntityType.LLAMA, EntityType.SPIDER);
                put(EntityType.PANDA, EntityType.SPIDER);
                put(EntityType.POLAR_BEAR, EntityType.SPIDER);
                put(EntityType.SPIDER, EntityType.CAVE_SPIDER);
                put(EntityType.SNOWMAN, EntityType.IRON_GOLEM);
                put(EntityType.CAVE_SPIDER, EntityType.IRON_GOLEM);

                put(EntityType.CAT, EntityType.SLIME);
                put(EntityType.OCELOT, EntityType.SLIME);
                put(EntityType.FOX, EntityType.SLIME);
                put(EntityType.RABBIT, EntityType.SLIME);
                put(EntityType.SLIME, EntityType.MAGMA_CUBE);
                put(EntityType.MAGMA_CUBE, EntityType.VEX);

                put(EntityType.SILVERFISH, EntityType.ENDERMITE);
                put(EntityType.ENDERMITE, EntityType.SHULKER);

                put(EntityType.STRAY, EntityType.HUSK);

                put(EntityType.WANDERING_TRADER, EntityType.PILLAGER);
                put(EntityType.VILLAGER, EntityType.PILLAGER);
                put(EntityType.PILLAGER, EntityType.VINDICATOR);
                put(EntityType.VINDICATOR, EntityType.WITCH);
                put(EntityType.WITCH, EntityType.EVOKER);

            }
        };
        getServer().getPluginManager().registerEvents(new TenseiListener(), this);
        getLogger().info("輪廻転生プラグインが有効になりました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("輪廻転生プラグインが無効になりました");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(!cmd.getName().equalsIgnoreCase("rinnetensei")){
            return false;
        }
        if(args.length < 1){
            sender.sendMessage("引数が足りません");
            return false;
        }else if(args.length > 1){
            sender.sendMessage("引数が多すぎます");
            return false;
        }
        String arg1 = args[0];

        if(arg1.equalsIgnoreCase("on")){
            TenseiListener.isEnabled = true;
            sender.sendMessage("輪廻転生プラグインをオンにしました");
            return true;
        } else if(arg1.equalsIgnoreCase("off")){
            TenseiListener.isEnabled = false;
            sender.sendMessage("輪廻転生プラグインをオフにしました");
            return true;
        } else {
            sender.sendMessage("「on」または「off」と入力してください");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return Stream.of("on", "off")
                        .filter(e -> e.startsWith(args[0]))
                        .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
