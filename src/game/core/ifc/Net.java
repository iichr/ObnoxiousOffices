package game.core.ifc;

import game.DevWars;
import game.core.event.Event;
import game.core.player.Player;
import game.core.world.World;
import game.networking.Server;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class Net {

    private static boolean isClient;

    public static void broadcast(Event event) {
        // server.sendToAll ????
        if(!isClient) World.world.getPlayers().forEach(p -> send(event, p));
    }

    public static void send(Event event, Player player) {
        if(!isClient) {
            // TODO: Implement with net code on integration branch
        }
    }

    public static void sendToServer(Event event) {
        if (isClient) DevWars.client.sendDataToServer(event);
    }

    public static void initServer() {
        isClient = false;
        ServerSync.init();
    }

    public static void initClient() {
        isClient = true;
        ClientSync.init();
    }

}
