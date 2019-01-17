package data.socket;

import java.net.Socket;

public class send_block_socket {
	private static Socket ClientSocket;
	private Socket_parent sp;

	public send_block_socket(Socket s) {
		ClientSocket = s;
		sp = new Socket_parent(ClientSocket, 1);
		sp.run();
	}
}