import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Message implements Runnable{
	private PrintWriter out;
	private String channel;
	private String server;
	public Message(PrintWriter out, String channel, String server) {
		this.out = out;
		this.channel = channel;
		this.server = server;
	}
	
	public void run() {
		System.out.println("Please wait");
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(20));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Scanner console = new Scanner(System.in);
		
		while(true) {
			System.out.println("1 - write a message");
			System.out.println("2 - leave session");
			System.out.println("3 - change channel");
			System.out.println("4 - check topic");
			System.out.println("5 - list visible users on " + channel);
			System.out.println("6 - list channels and their topics");
			System.out.println("7 - request server info");
			String switchNumS = console.nextLine();
			int switchNum = Integer.parseInt(switchNumS);
			switch(switchNum) {
			case 1:
				System.out.println("Please write your message");
				String message = console.nextLine();
				String fullMessage = "PRIVMSG " + channel + " :" + message;
				System.out.println("YOU: " + message);
				out.print(fullMessage + "\r\n");
				out.flush();
			break;
			case 2:
				System.out.println("YOU: Quit");
				out.print("QUIT :nice" + "\r\n");
				out.flush();
				console.close();
				System.exit(0);
			break;
			case 3:
				System.out.println("Please write new channel");
				message = console.nextLine();
				System.out.println("YOU: Change channel to" + message);
				channel = message;
				out.print("JOIN " + channel + "\r\n");
				out.flush();
			break;
			case 4:
				out.print("TOPIC " + channel + "\r\n");
				out.flush();
			break;
			case 5:
				out.print("NAMES " + channel + "\r\n");
				out.flush();
			break;
			case 6:
				out.print("LIST " + "\r\n");
				out.flush();
			break;
			case 7:
				out.print("INFO " + server + "\r\n");
				out.flush();
			break;
			default:
				System.out.println("Wrong choices");
				System.exit(0);
			break;
			}
		}
	}
}
