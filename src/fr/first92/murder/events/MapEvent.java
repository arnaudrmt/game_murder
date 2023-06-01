package fr.first92.murder.events;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.Variables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.stream.IntStream;

public class MapEvent implements Listener {

    BufferedImage image;
    BufferedImage state;
    BufferedImage bg;

    Variables var = Murder.getInstance().getVariables();

    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) throws IOException {

        MapView mapView = e.getMap();

        mapView.setScale(MapView.Scale.FARTHEST);

        mapView.getRenderers().clear();

        bg = ImageIO.read(new File(System.getProperty("user.dir") + "/plugins/murder/murder_chart.png"));

        mapView.removeRenderer(e.getMap().getRenderers().get(0));
        mapView.addRenderer(new Renderer());
    }

    public class Renderer extends MapRenderer {

        @Override
        public void render(MapView mapView, MapCanvas mapCanvas, Player player) {

            mapCanvas.drawImage(0, 0, bg);

            try {

                IntStream.range(0, 3).forEach(rs -> {

                    try {

                        if(rs <= var.getVictimsList().size() - 1) {

                            Player p = var.getVictimsList().get(rs);

                            image = ImageIO.read(new URL("https://minotar.net/avatar/" + p.getName() + "/16.png"));

                            state = ImageIO.read(new File(System.getProperty("user.dir") +
                                    "/plugins/murder/redstone_lamp.png"));

                            if(var.hasBeenTested(p)) {

                                if(var.isGuilty(p)) {

                                    state = ImageIO.read(new File(System.getProperty("user.dir") +
                                            "/plugins/murder/iron_sword.png"));
                                } else {
                                    state = ImageIO.read(new File(System.getProperty("user.dir") +
                                            "/plugins/murder/redstone_lamp_on.png"));
                                }
                            }

                            if(p.getName().length() < 10) {
                                mapCanvas.drawText(35, 42, MinecraftFont.Font, p.getName());
                            } else {
                                mapCanvas.drawText(35, 42 + (33 * rs),
                                        MinecraftFont.Font, p.getName().substring(0, 9) + "...");
                            }

                        } else {

                            image = ImageIO.read(new File(System.getProperty("user.dir") +
                                    "/plugins/murder/barrier.png"));

                            state = ImageIO.read(new File(System.getProperty("user.dir") +
                                    "/plugins/murder/redstone_lamp.png"));

                            mapCanvas.drawText(35, 42 + (33 * rs), MinecraftFont.Font, "No one");
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    mapCanvas.drawImage(13, 38 + (33 * rs), image);
                    mapCanvas.drawImage(99, 38 + (33 * rs), state);
                });

            } catch (Exception e) {
                e.printStackTrace();;
            }

        }
    }
}
