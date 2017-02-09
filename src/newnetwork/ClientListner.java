package newnetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListner extends Thread{
	private Socket server;
	private ObjectInputStream is;
	
	public ClientListner(Socket server) {
		this.server = server;
		System.out.println("IN LISTNER");
	}
	

	@Override
	public void run(){
		try {
			is = new ObjectInputStream(this.server.getInputStream());
			boolean running = true;
			while(running){
				Object input = is.readObject();
				System.out.println("The input is: " + input.toString());
				
				//WHAT TO DO WITH THE DATA
				
			}
			
		} catch (IOException e) {
			System.out.println("ERROR: Can't create Input Stream for Client");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
