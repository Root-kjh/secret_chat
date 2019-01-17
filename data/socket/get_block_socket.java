package data.socket;

import java.net.Socket;

public class get_block_socket {
	private static Socket ClientSocket;
	private Socket_parent sp;

	public get_block_socket(Socket s) {
		ClientSocket = s;
		sp = new Socket_parent(ClientSocket, 2);
		sp.run();
	}
}