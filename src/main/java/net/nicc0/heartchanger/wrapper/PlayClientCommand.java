package net.nicc0.heartchanger.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ClientCommand;

public class PlayClientCommand extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Client.CLIENT_COMMAND;
            
    public PlayClientCommand() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }
    
    public PlayClientCommand(PacketContainer packet) {
        super(packet, TYPE);
    }
    
    /**
     * Retrieve whether or not we're logging in or respawning.
     * @return The current command
    */
    public ClientCommand getCommand() {
        return handle.getClientCommands().read(0);
    }
    
    /**
     * Set whether or not we're logging in or respawning.
     * @param value - new value.
    */
    public void setCommand(ClientCommand value) {
        handle.getClientCommands().write(0, value);
    }
}
