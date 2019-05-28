package net.unix.spectrum.listener;

import net.unix.spectrum.api.helper.ChatHelper;
import net.unix.spectrum.api.helper.ItemBuilder;
import net.unix.spectrum.api.helper.ItemHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * @author Unix
 * 17:21, 27.05.2019
 **/
public class EntityDeathListener implements Listener {

    private final String name;
    private final Random random;
    private final List<ItemStack> items;

    public EntityDeathListener(FileConfiguration configuration) {
        this.name = ChatHelper.fixColor(configuration.getString("name"));
        this.random = new Random();
        this.items = new ArrayList<>();

        final ConfigurationSection configurationSection = configuration.getConfigurationSection("items");

        configurationSection.getKeys(false)
                .stream()
                .map(configurationSection::getConfigurationSection)
                .forEachOrdered(c -> this.items.add(new ItemBuilder(
                        requireNonNull(ItemHelper.getMaterial(c.getString("material"))),
                        (short) c.getInt("data"))
                        .setAmount(c.getInt("amount"))
                        .build()));
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        final Entity entity = event.getEntity();

        if (entity.getType() != EntityType.SKELETON) {
            return;
        }

        if (entity.getCustomName() == null) {
            return;
        }

        if (!entity.getCustomName().equals(this.name)) {
            return;
        }

        entity.getWorld().dropItemNaturally(entity.getLocation(), this.items.get(this.random.nextInt(this.items.size())));
    }
}