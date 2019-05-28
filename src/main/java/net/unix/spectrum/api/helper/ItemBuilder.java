package net.unix.spectrum.api.helper;


import lombok.Getter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import java.util.HashMap;

import static net.unix.spectrum.api.helper.ChatHelper.fixColor;
import static org.bukkit.Bukkit.*;

/**
 * @author Unix
 * 16:23, 27.05.2019
 **/
@Getter
public class ItemBuilder implements Cloneable {

    private Material mat;
    private int amount;
    private final short data;
    private String title;
    private final HashMap<Enchantment, Integer> enchants;

    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    private ItemBuilder(Material mat, int amount) {
        this(mat, amount, (short)0);
    }

    public ItemBuilder(Material mat, short data) {
        this(mat, 1, data);
    }

    private ItemBuilder(Material mat, int amount, short data) {
        this.title = null;
        this.enchants = new HashMap<>();
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        this.enchants.remove(enchant);
        this.enchants.put(enchant, level);

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {
        Material mat = this.mat;

        if (mat == null) {
            getLogger().warning("Null material!");
        }

        final ItemStack item = new ItemStack(this.mat, this.amount, this.data);
        final ItemMeta meta = item.getItemMeta();

        if (this.title != null) {
            meta.setDisplayName(fixColor(this.title));
        }

        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);

        return item;
    }
}