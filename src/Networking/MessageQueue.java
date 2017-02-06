package Networking;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue
{
	// implementation of BlockingQueue
	private BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

	// Inserts the specified message into this queue.
	public void offer(Message m)
	{
		queue.offer(m);
	}

	// Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
	public Message take()
	{
		while (true)
		{
			try
			{
				return (queue.take());
			} catch (InterruptedException e)
			{
			}

		}
	}
}