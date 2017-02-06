package Networking;
public class Message
{

	private final String sender;
	private final String text;
	/**
	 * Initalises the sender of the message and the message
	 * @parm Sender
	 * @Param Message
	 */
	Message(String sender, String text)
	{
		this.sender = sender;
		this.text = text;
	}
	
	/**
	 * Returns the sender
	 * @return sender
	 */
	public String getSender()
	{
		return sender;
	}
	
	/**
	 * Gets the message
	 * @return message
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * Returns the sender and message in one string
	 * @return sender and message string
	 */
	public String toString()
	{
		return "From " + sender + ": " + text;
	}
}
