package Networking;
import java.io.PrintStream;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread
{
	private MessageQueue queue;
	private PrintStream client;
	private boolean notQuit = true;
	
	/**
	 * intialises message and client
	 * @param q
	 * @param c
	 */
	public ServerSender(MessageQueue q, PrintStream c)
	{
		queue = q;
		client = c;
	}
	/**
	 * Forward message to client
	 */
	public void run()
	{
		while (notQuit)
		{
			Message msg = queue.take();
			String newmsg = msg.toString();
			int l = newmsg.length();
			char f = newmsg.charAt(l - 1);
			if(f == 'q')
			{
				notQuit = false;
			}
			client.println(msg);
		}
	}
}
