package net.unix.spectrum.api.helper;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Unix
 * 15:54, 27.05.2019
 **/
public final class ChatHelper {

    private ChatHelper() {
    }

    @NotNull
    public static String fixColor(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text)
                .replace(">>", "»")
                .replace("<<", "«");
    }

    @NotNull
    public static List<String> fixColor(@NotNull List<String> messages) {
        return messages.stream()
                .map(ChatHelper::fixColor)
                .collect(Collectors.toList());
    }

    public static void sendMessage(@NotNull Player player, String message) {
        player.sendMessage(fixColor(message));
    }

    public static void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subTitle) {
        final CraftPlayer craftplayer = (CraftPlayer) player;
        final PlayerConnection connection = craftplayer.getHandle().playerConnection;

        final IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + fixColor(title) + "'}");
        final IChatBaseComponent subTitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + fixColor(subTitle) + "'}");

        final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, 30, 30, 30);
        final PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleJSON);

        connection.sendPacket(titlePacket);
        connection.sendPacket(subTitlePacket);
    }
}