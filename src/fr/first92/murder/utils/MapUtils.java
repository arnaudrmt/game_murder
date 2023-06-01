package fr.first92.murder.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Map;

public class MapUtils {

    public <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String locationSerializer(Location loc) {

        return "`" + loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    public Location locationDeSerializer(String l) {

        String[] s = l.split(",");

        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }
}
