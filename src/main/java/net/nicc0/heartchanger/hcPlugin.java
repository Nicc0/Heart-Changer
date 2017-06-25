package net.nicc0.heartchanger;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nicc0.heartchanger.listener.PlayerDeathListener;
import net.nicc0.heartchanger.packets.PacketsListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @since 1.0.0 (7.06.2017)
 * @version 1.0.0
 * @author Nicc0
 */
public class hcPlugin extends JavaPlugin {
    
    private static hcPlugin instance;
    private static YamlConfiguration config;
    
    @Override
    public void onEnable() {
        enable();
    }
    
    @Override
    public void onDisable() {
        disable();
    }
    
    public void enable() {
        instance = this;
        
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        
        config = YamlConfiguration.loadConfiguration(loadResource("config.yml"));
        
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketsListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.RESPAWN, PacketType.Play.Server.LOGIN));

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }
    
    public void disable() {
        
    }
    
    public static hcPlugin getInstance() {
        return instance;
    }
    
    public File loadResource(String resource) {
        File resourceFile = new File(getDataFolder(), resource);
        if (!resourceFile.exists()) {
            try {
                resourceFile.createNewFile();
                InputStream in = getResource(resource); 
                OutputStream out = new FileOutputStream(resourceFile);
                ByteStreams.copy(in, out);
            } catch (IOException ex) {
                Logger.getLogger(hcPlugin.class.getName()).log(Level.SEVERE, "Problem with copying Plugin resource!", ex);
            }
        } return resourceFile;
    }
    
    @Override
    public YamlConfiguration getConfig() {
        return config;
    }
}
