package Networking;
import java.io.BufferedReader;
import java.io.IOException;

// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client.

public class ServerReceiver extends Thread
{
	private String myClientsName;
	private BufferedReader myClient;
	private ClientTable clientTable;
	private Boolean quit = false;

	/**
	 * Initialises the client and client table
	 * 
	 * @param n
	 * @param c
	 * @param t
	 */
	public ServerReceiver(String n, BufferedReader c, ClientTable t)
	{
		myClientsName = n;
		myClient = c;
		clientTable = t;
		//System.out.println(clientTable.getQueue());
		Message msg = new Message(clientTable.getQueue(), "");
		MessageQueue recipientsQueue = clientTable.getQueue(myClientsName);
		recipientsQueue.offer(msg);
	}

	public void run()
	{
		try
		{
			while (true)
			{
				// Read the recipient and message from client
				String recipient = myClient.readLine();
				String text = myClient.readLine();
				if (recipient != null && text != null)
				{
					// Send message to the recipient if they exist
					{
						Message msg = new Message(myClientsName, text);
						MessageQueue recipientsQueue = clientTable.getQueue(recipient);
						if (recipientsQueue != null)
							recipientsQueue.offer(msg);
						else
							System.err.println("Message for unexistent client " + recipient + ": " + text);
					}
				} 
				else
				{
					myClient.close();
					return;
				}
			}
		} catch (IOException e)
		{
			if(!quit)
			{
			System.err.println("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			}
			// No point in trying to close sockets. Just give up we end this
			// thread (we don't do System.exit(1)).
		}
	}
}
