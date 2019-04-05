package socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class main {
	public static List<String> ip = new ArrayList<String>();

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(3141);
			Socket ClientSocket = null;
			ServerThread sr;
			ip.add("/222.110.147.53");
			while (true) {
				ClientSocket = server.accept();
				if (ClientSocket != null) {
					ip.add(ClientSocket.getInetAddress().toString());
					sr = new ServerThread(ClientSocket, ClientSocket.getInetAddress().toString());
					Thread t = new Thread(sr);
					t.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
