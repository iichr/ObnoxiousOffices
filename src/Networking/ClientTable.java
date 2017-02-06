package Networking;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientTable
{
	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();
	/**
	 * Adds a message to a nickname
	 * @param nickname
	 */
	public void add(String nickname)
	{
		queueTable.put(nickname, new MessageQueue());
	}

	/**
	 * Returns null if the nickname is not in the table:
	 * @param nickname
	 * @return nickname or null
	 */
	public MessageQueue getQueue(String nickname)
	{
		return queueTable.get(nickname);
	}
	/**
	 * Get the list of all nicknames connected to the server
	 * @return keyset as string
	 */
	public String getQueue()
	{
		return queueTable.keySet().toString();
	}

}