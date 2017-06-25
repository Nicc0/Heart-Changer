package net.nicc0.heartchanger.utils;

import org.bukkit.Bukkit;

/**
 *
 * @author Nicc0
 */
public class Version {
    
    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }
    
    public static Class<?> getClass(String name, boolean bukkit) throws ClassNotFoundException {
        if(bukkit) return Class.forName(getOBC(name));
        return Class.forName(getNMS(name));
    }
    
    public static String getOBC(String name) {
        return "org.bukkit.craftbukkit." + getVersion() + "." + name;
    }
    
    public static String getNMS(String name) {
        return "net.minecraft.server." + getVersion() + "." + name;
    }
}
