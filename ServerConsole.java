import java.io.*;
import client.*;

import common.*;


public class ServerConsole implements ChatIF {
	final public static int DEFAULT_PORT = 5555;
	EchoServer server;
	
	public ServerConsole(int port) {
		try {
			server = new EchoServer(port, this);
			server.listen();
		}catch (IOException e) {
			System.out.println("Error: Can't setup connection!"
	                + " Terminating server.");
	        System.exit(1); 
		}
	}
	
	
	 public void accept() 
	  {
	    try
	    {
	    	BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));

		      String message;

		      while (true) 
		      {
		        message = fromConsole.readLine();
		        
		       if(message.equals("#stop")) {
		    	   server.close();
		    	   
		       }else if(message.equals("#setport")) {
		    	   if(server.isListening()) {
		   			System.out.println("ne peut pas setport quand le server est encore connecté");
		   		}else {
		   			try{
		   				int port = Integer.parseInt(message.split(" ")[1]);
		   				server.setPort(port);
		   			}catch(ArrayIndexOutOfBoundsException e) {
		   				System.out.println("port number required");				
		   			}catch(NumberFormatException e) {
		   				System.out.println("invalid port number");
		   			}
		   		}
		       }else if(message.equals("#start")) {
		    	   server.listen();
		    	   
		       }else if(message.equals("#getport")) {
		    	   System.out.println(server.getPort());
		       }else {server.handleMessageFromServerUI(message);
		       }
		      }}
		      
		     
		   
		    catch (Exception ex) 
		    {
		      System.out.println("Unexpected error while reading from console!");
		    }
	    }

	
	    
	    
	@Override
	public void display(String message) {
		System.out.print(">"+ message);
	}
	
	public static void main(String[] args) 	  	{
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    ServerConsole serverConsole = new ServerConsole(port);
	    serverConsole.accept();
	    
	  }}