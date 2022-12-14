// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.*;

import common.ChatIF;
import ocsf.server.*;


/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	ChatIF serverUI;

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */


	public EchoServer(int port, ChatIF serverUI) throws IOException
	{
		super(port);
		this.serverUI = serverUI;
	}


	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client){
		System.out.println("Message received: " + msg + " from " + client.getInfo("loginID"));
		if (msg.toString().startsWith("#login ")){
			if (client.getInfo("loginID") == null){

				String[] mmmm = msg.toString().split(" ");
				if (mmmm.length == 1) { 
					try
					{
						client.sendToClient("loginID invalid");
						client.close();
					}
					catch (IOException e2) {
						serverUI.display("erreur généré :( ");
					}

				}else {
					client.setInfo("loginID", mmmm[1]);
				}
			}else {
				try {
					client.sendToClient("Vous avez deja login!!!");
				}catch(Exception e) {
					serverUI.display("erreur généré :(");
				}
			}
		}
		else 
		{
			this.sendToAllClients(client.getInfo("loginID") + "> " + msg);
		}
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */

	public void handleMessageFromServerUI(String msg) {
		serverUI.display(msg);
		sendToAllClients(msg);
	}


	public void quit() {
		try {
			close();
		}catch(Exception e) {

		}
		System.exit(0);
	}


	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}


	protected void clientConnected(ConnectionToClient client) {
		System.out.print("client connecte");

	}

	protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
		System.out.print("client disconnecte à cause des erreurs");
		exception.printStackTrace();
	}

	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("client disconnecte");
	}


	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */

}
//End of EchoServer class
