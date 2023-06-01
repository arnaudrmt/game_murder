package fr.first92.murder.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import fr.first92.murder.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {

    private int bonusTests;

    private Location lobby;
    private Location cage;

    private int questsCompleted;

    private List<Location> spawns = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private List<String> tunnelPoints = new ArrayList<>();

    private List<Player> victimsList = new ArrayList<>();

    private Map<Player, Boolean> guilt = new HashMap<>();

    public int getBonusTests() {
        return bonusTests;
    }

    public void addBonusTest() {
        bonusTests++;
    }

    public boolean isGuilty(Player p) {
        return guilt.get(p);
    }

    public boolean hasBeenTested(Player p) {
        return guilt.containsKey(p);
    }

    public void setJudgment(Player p, boolean judgment) {
        guilt.put(p, judgment);
    }

    public int numberOfTested() {
        return guilt.size();
    }

    public List<String> getRooms() {
        return rooms;
    }

    public List<Location> getRoomsLocations() {

        List<Location> locs = new ArrayList<>();

        rooms.forEach(r -> locs.add(new MapUtils().locationDeSerializer(r.split("`")[1])));

        return locs;
    }

    public Map<String, Location> getRoomsMap() {

        Map<String, Location> m = new HashMap<>();

        rooms.forEach(l -> {

            String name = l.split("`")[0];

            m.put(name, new MapUtils().locationDeSerializer(l.split("`")[1]));
        });

        return m;
    }

    public void addRoom(String name, Location loc) {

        rooms.add(name + new MapUtils().locationSerializer(loc));
    }

    public void addTunnelPoint(String name, Location loc) {

        tunnelPoints.add(name + new MapUtils().locationSerializer(loc));
    }

    public List<String> getTunnelPoints() {
        return tunnelPoints;
    }

    public Map<String, Location> getTunnelPointsMap() {

        Map<String, Location> m = new HashMap<>();

        tunnelPoints.forEach(l -> {

            String name = l.split("`")[0];

            m.put(name, new MapUtils().locationDeSerializer(l.split("`")[1]));
        });

        return m;
    }

    public List<Location> getTunnelLocations() {

        List<Location> locs = new ArrayList<>();

        tunnelPoints.forEach(r -> locs.add(new MapUtils().locationDeSerializer(r.split("`")[1])));

        return locs;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public Location getCage() {
        return cage;
    }

    public void addCage(Location loc) {
        cage.add(loc);
    }

    public void addSpawn(Location loc) {
        spawns.add(loc);
    }

    public void setCage(Location cage) {
        this.cage = cage;
    }

    public List<Player> getVictimsList() {
        return victimsList;
    }

    public Player nextVictim() {

        return victimsList.get(0);
    }

    public void removeVictim(Player p) {

        victimsList.remove(p);
    }

    public void setVictimsList(List<Player> victimsList) {
        this.victimsList = victimsList;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public void completeQuest() {

        questsCompleted++;
    }
}
