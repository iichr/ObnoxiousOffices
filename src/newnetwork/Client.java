package newnetwork;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket server;
	
	public Client(String name){
		
		int port = 8942;
		String hostname = "localhost";
		
		try {
			this.server = new Socket(hostname, port);
			System.out.println("Client Connected To Server!");
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}
		
		this.sendDataToServer(name);

		new ClientListner(this.server).start();
	}
	
	/**
	 * Sends Data to the server for updating the players
	 * @param data The data to end
	 */
	public void sendDataToServer(Object data){
		try {
			ObjectOutputStream od = new ObjectOutputStream(this.server.getOutputStream());
			od.writeObject(data);
			od.flush();
			System.out.println("Data Sent!");
		
		} catch (IOException e) {
			System.out.println("ERROR: Can't create Object Stream to send");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * FOR TESTING!!!
	 * @param args
	 */
	public static void main(String[] args){
		new Client("Test1");
		//new Client("Test1");
		//new Client("Test2");
		//new Client("Test3");
	}

}
