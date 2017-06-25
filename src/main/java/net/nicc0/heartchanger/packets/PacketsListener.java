package net.nicc0.heartchanger.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.nicc0.heartchanger.hcPlugin;
import net.nicc0.heartchanger.wrapper.PlayServerLogin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @since 1.0.0
 * @version 1.0.0 (15.05.2017)
 * @author Nicc0
 */
public class PacketsListener extends PacketAdapter {
    /**
     * Constructor z ProtocolLiba
     * 
     * @param plugin
     * @param listenerPriority
     * @param types 
     */
    public PacketsListener(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
    }
    
    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.LOGIN && event.getPlayer().getHealth() > 0) {
            Player player = event.getPlayer();
            int type = hcPlugin.getInstance().getType();
            if(player.hasPermission("heartchanger.hardcore") && type == 0 || type == 2) {
                PacketContainer packet = event.getPacket();
                PlayServerLogin playServerLogin = new PlayServerLogin(packet);
                playServerLogin.setHardcore(true);
            }
        } else if(event.getPacketType() == PacketType.Play.Client.CLIENT_COMMAND) {

        }
        
    }
}