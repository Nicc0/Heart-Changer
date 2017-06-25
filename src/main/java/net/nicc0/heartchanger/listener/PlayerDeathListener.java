package net.nicc0.heartchanger.listener;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.nicc0.heartchanger.hcPlugin;
import net.nicc0.heartchanger.utils.Version;
import net.nicc0.heartchanger.wrapper.PlayClientCommand;
import net.nicc0.heartchanger.wrapper.PlayServerTitle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Nicc0
 */
public class PlayerDeathListener implements Listener {
    
    private final hcPlugin plugin = hcPlugin.getInstance();
    
    public PlayerDeathListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeathEvent(PlayerDeathEvent event) throws InvocationTargetException {
        final Player player = event.getEntity();
        final Integer score = player.getTotalExperience();
        final String message = event.getDeathMessage();
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                boolean deathScreen = plugin.getConfig().getBoolean("DeathScreen");
                boolean displayMessage = plugin.getConfig().getBoolean("DisplayMessage");
                boolean autoMessage = plugin.getConfig().getBoolean("AutoMessage");
                
                if (player.isDead() && !deathScreen) {
                    try {
                        PlayClientCommand playClientCommand = new PlayClientCommand();
                        playClientCommand.setCommand(EnumWrappers.ClientCommand.PERFORM_RESPAWN);
                        Object packet = playClientCommand.getHandle().getHandle();
                        Object craftPlayer = Version.getClass("entity.CraftPlayer", true).cast(player);
                        Object playerHandle = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
                        Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
                        playerConnection.getClass().getMethod("a", packet.getClass()).invoke(playerConnection, packet);

                        if(displayMessage) {
                            WrappedChatComponent title = null, subtitle = null;
                            
                            if(autoMessage && message != null && !message.equalsIgnoreCase("")) {
                                title = WrappedChatComponent.fromText(message);
                            }

                            if(title == null) {
                                String titleMessage = plugin.getConfig().getString("Messages.YouDied");
                                title = WrappedChatComponent.fromText(titleMessage.replaceAll("\u0026", "\u00A7"));
                            }
                            
                            if(subtitle == null) {
                                String subtitleMessage = plugin.getConfig().getString("Messages.Score");
                                subtitle = WrappedChatComponent.fromText(subtitleMessage.replaceAll("\u0026", "\u00A7").replaceAll("%score%", score.toString()));
                            }

                            new PlayServerTitle(EnumWrappers.TitleAction.TITLE,    title,    20, 40, 20).sendPacket(player);
                            new PlayServerTitle(EnumWrappers.TitleAction.SUBTITLE, subtitle, 20, 40, 20).sendPacket(player);
                            
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
                        Logger.getLogger(PlayerDeathListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}
