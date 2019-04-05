package data.socket;

import java.net.Socket;

public class server_conn {
	private Socket socket;

	public server_conn() {
		socket = null;
		try {
			socket = new Socket("222.110.147.53", 3141);
			Socket_parent sp = new Socket_parent(socket, 5);
			sp.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}