package net.unix.spectrum.api.helper;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.Bukkit.getLogger;

/**
 * @author Unix
 * 16:19, 28.05.2019
 **/
public final class ItemHelper {

    private ItemHelper() {
    }

    @Nullable
    public static Material getMaterial(String mat) {
        final Material material = Material.matchMaterial(mat);

        if (material == null) {
            getLogger().warning("Error! The material named " + mat + " does not exist!");
            return null;
        }

        return material;
    }
}