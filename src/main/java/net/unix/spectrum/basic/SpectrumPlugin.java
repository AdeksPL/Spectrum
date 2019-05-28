package net.unix.spectrum.basic;

import net.unix.spectrum.listener.BlockPlaceListener;
import net.unix.spectrum.listener.EntityDeathListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * @author Unix
 * 15:46, 27.05.2019
 **/
public class SpectrumPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        final FileConfiguration configuration = this.getConfig();

        this.registerListeners(
                new BlockPlaceListener(configuration),
                new EntityDeathListener(configuration)
        );
    }

    private void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEachOrdered(this::registerListener);
    }
}