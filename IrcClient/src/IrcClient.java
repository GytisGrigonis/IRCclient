import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class IrcClient{
	
	private static String nick;
	private static String username;
	private static String realName;
	private static PrintWriter out;
	private static Scanner in;
	

	public static void main(String[] args) throws IOException{
		 Scanner console = new Scanner(System.in);
		 
		 System.out.print("Enter server name: ");
		 String server = console.nextLine();
		 System.out.print("Enter channel: ");
		 String channel = console.nextLine();
		 System.out.print("Enter port: ");
		 String portS = console.nextLine();
		 int port = Integer.parseInt(portS);
		 
		 System.out.print("Enter nickname: ");
		 nick = console.nextLine();
		 System.out.print("Enter username: ");
		 username = console.nextLine();
		 System.out.print("Enter real name: ");
		 realName = console.nextLine();		 
		
		 Socket socket = new Socket(server, port);
		 
		 out = new PrintWriter(socket.getOutputStream(), true);
		 in = new Scanner(socket.getInputStream());
		 
		 write("NICK", nick);
		 write("USER", username + " 0 * :" + realName);
		 write("JOIN", channel);
		 Runnable m1 = new Message(out, channel, server);
		 new Thread(m1).start();
		 
		 while(in.hasNext()) {
			 String serverMessage = in.nextLine();
			 System.out.println("SERVER: " + serverMessage);
			 if(serverMessage.startsWith("PING")) {
				 String pingContents = serverMessage.split(" ", 2)[1];
				 write("PONG", pingContents);
			 }
		 }
		 
		 in.close();
		 out.close();
		 socket.close();
		 console.close();
	}
	
	private static void write(String command, String message) {
		String fullMessage = command + " " + message;
		System.out.println("YOU: " + fullMessage);
		out.print(fullMessage + "\r\n");
		out.flush();
	}

}

