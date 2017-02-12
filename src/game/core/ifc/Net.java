package game.core.ifc;

import game.core.event.Event;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class Net {

    private static boolean isClient;

    public static void broadcast(Event event) {
        World.world.getPlayers().forEach(p -> send(event, p));
    }

    public static void send(Event event, Player player) {
        if(!isClient) {
            // TODO: Implement with net code on integration branch
        }
    }

    public static void sendToServer(Event event) {
        if (isClient) {
            // TODO: Implement with net code on integration branch
        }
    }

    public void initServer() {
        isClient = false;
    }

    public void initClient() {
        isClient = true;
        ClientSync.init();
    }

}
