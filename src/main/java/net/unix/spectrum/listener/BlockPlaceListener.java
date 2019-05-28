package net.unix.spectrum.listener;

import net.unix.spectrum.api.helper.ChatHelper;
import net.unix.spectrum.api.helper.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 * @author Unix
 * 15:53, 27.05.2019
 **/
public class BlockPlaceListener implements Listener { //syf kod

    private final String title, subTitle, name;
    private final ItemStack helmet, chestplate, leggings, boots, sword, skull;

    public BlockPlaceListener(FileConfiguration configuration) {
        this.title = ChatHelper.fixColor(configuration.getString("spawn.title"));
        this.subTitle = ChatHelper.fixColor(configuration.getString("spawn.subtitle"));
        this.name = ChatHelper.fixColor(configuration.getString("name"));
        this.helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();

        this.chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();

        this.leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();

        this.boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();

        this.sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.FIRE_ASPECT, 2)
                .build();

        this.skull = new ItemStack(Material.SKULL_ITEM, 1);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        final Block block = event.getBlockPlaced();
        final Material material = block.getType();

        if (material != Material.SKULL) {
            return;
        }

        final Location location = block.getLocation().add(0D, -1D, 0D);

        if (location.getBlock().getType() != Material.DIAMOND_BLOCK) {
            return;
        }

        final Player player = event.getPlayer();

        player.getInventory().removeItem(this.skull);
        ChatHelper.sendTitle(event.getPlayer(), this.title, this.subTitle);

        final Location blockLocation = block.getLocation();
        final LivingEntity livingEntity = (LivingEntity) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.SKELETON);
        final EntityEquipment entityEquipment = livingEntity.getEquipment();

        livingEntity.setCustomName(this.name);

        entityEquipment.setHelmet(this.helmet);
        entityEquipment.setChestplate(this.chestplate);
        entityEquipment.setLeggings(this.leggings);
        entityEquipment.setBoots(this.boots);
        entityEquipment.setItemInHand(this.sword);

        event.setCancelled(true);
    }
}