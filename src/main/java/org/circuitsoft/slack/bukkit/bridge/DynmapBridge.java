package org.circuitsoft.slack.bukkit.bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.circuitsoft.slack.bukkit.SlackBukkit;
import org.dynmap.DynmapAPI;
import org.dynmap.DynmapWebChatEvent;

/**
 * @author Davy
 */
public class DynmapBridge implements Listener {
    private final SlackBukkit mPlugin;
    private final DynmapAPI mDynmap;

    public static DynmapBridge load(final SlackBukkit plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final Plugin dynmapPlugin = pluginManager.getPlugin("dynmap");
        if (dynmapPlugin == null || !(dynmapPlugin instanceof DynmapAPI))
            return null;

        final DynmapBridge instance = new DynmapBridge(plugin, (DynmapAPI) dynmapPlugin);
        pluginManager.registerEvents(instance, plugin);

        return instance;
    }

    private DynmapBridge(final SlackBukkit plugin, final DynmapAPI dynmap) {
        mPlugin = plugin;
        mDynmap = dynmap;
    }

    public void broadcast(final String message) {
        if (mDynmap == null)
            return;

        mDynmap.sendBroadcastToWeb(null, ChatColor.stripColor(message));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDynmapWebChat(final DynmapWebChatEvent ev) {
        mPlugin.send(ev.getMessage(), ev.getName() + " (Dynmap)", "http://i.imgur.com/5o9aAUs.png");
    }
}
