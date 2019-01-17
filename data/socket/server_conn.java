package data.socket;

import java.net.Socket;

public class server_conn {
	private Socket socket;

	public server_conn() {
		socket = null;
		try {
			socket = new Socket("54.180.122.133", 52994);
			Socket_parent sp = new Socket_parent(socket, 5);
			sp.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}