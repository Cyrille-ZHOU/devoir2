

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  String loginID;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  
  
  public ChatClient(String loginID,String host, int port, ChatIF clientUI) 
		    throws IOException 
		  {
		    super(host, port); //Call the superclass constructor
		    this.clientUI = clientUI;
		    this.loginID = loginID;

		    this.openConnection();
		    sendToServer("#login"+loginID);
		  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	if(message.equals("#quit")) {
		quit();
	
	}else if(message.equals("#logoff")) {
		 try {
			closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 clientUI.display("Vous avez deja deconnecté."); 

		
	}else if(message.startsWith("#sethost")) {
		if(isConnected()) {
			System.out.println("ne peut pas sethost quand le client est encore connecté");
		}else {
			try {
				String host = message.split(" ")[1];
				setHost(host);
			}catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("host name is required");
			}
		}
	}else if(message.startsWith("#setport")) {
		if(isConnected()) {
			System.out.println("ne peut pas sethost quand le client est encore connecté");
		}else {
			try{
				int port = Integer.parseInt(message.split(" ")[1]);
				setPort(port);
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("port number required");				
			}catch(NumberFormatException e) {
				System.out.println("invalid port number");
			}
		}
	}else if(message.equals("#login")) {
		if(isConnected()) {
			System.out.println("ne peut pas login quand le client est encore connecté");
		}else {
			try {
				openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      try
		      {
		        openConnection();
		      }
		      catch (Exception e)
		      {
		        clientUI.display("Connexion Error");
		      }
		}
	}else if(message.equals("#gethost")) {
		System.out.println(getHost());
	}else if(message.equals("#getport")) {
		System.out.println(getPort());
	}else {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();}
    }
  }
  
  
 
  
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  protected void connectionEstablished() {
	  System.out.println("La connexion est ouverte.");
	  
  }
  
  
  protected void connectionClosed() {
	  System.out.println("La connexion est fermée.");
  }
  
  protected void connectionException(Exception exception) {
	  System.out.println("Le serveur s'est arrêté, veuillez quiter.");
	  quit();
	  
  }
  
}
//End of ChatClient class
