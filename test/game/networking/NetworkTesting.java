package test.game.networking;

import static org.junit.Assert.*;

import org.junit.Test;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.Events;
import game.core.sync.ClientSync;
import game.networking.Client;
import game.networking.Server;

public class NetworkTesting extends Thread{
Server server;
	public void run(){
		System.out.println("RunningThread");
		server = new Server();
	}
	@Test
	public void test() {
		NetworkTesting thread = new NetworkTesting();
		thread.start();	
		ClientSync.init();
		Client client = new Client();
		Events.trigger(new ConnectionAttemptEvent("Client1", "Localhost"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(client.connected == true);
	}

}
