package net.nicc0.heartchanger.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

public abstract class AbstractPacket {

    protected PacketContainer handle;

    protected AbstractPacket(PacketContainer handle, PacketType type) {
        if (handle == null) {
            throw new IllegalArgumentException("Packet handle cannot be NULL.");
        }
        if (!Objects.equal(handle.getType(), type)) {
            throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
        }
        this.handle = handle;
    }

    public PacketContainer getHandle() {
        return this.handle;
    }

    public void sendPacket(Player receiver) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver,
                    getHandle());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet.", e);
        }
    }

    @Deprecated
    public void recievePacket(Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender,
                    getHandle());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot recieve packet.", e);
        }
    }

    public void receivePacket(Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender,
                    getHandle());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot recieve packet.", e);
        }
    }
}
