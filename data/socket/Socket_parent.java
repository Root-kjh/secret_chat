package data.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Socket_parent implements Runnable {
	private static Socket ClientSocket;
	private static byte[] msg;
	private data.func.func f;
	private socket_tool st;
	private InputStream is;
	private OutputStream os;
	int flag;

	public Socket_parent(Socket s, int flag) {
		this.flag = flag;
		ClientSocket = s;
		this.f = new data.func.func();
		st = new socket_tool();
		try {
			is = ClientSocket.getInputStream();
			os = ClientSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			st.write(4, f.send_public_key(), os);
			while ((msg = st.readLine(is)) != null) {
				f.get_msg(msg);
				switch (msg[0]) {
				case 1:
					st.write(2, f.send_block(), os);
					if (flag == 1)
						return;
					break;
				case 2:
					f.recv_block();
					if (flag == 2)
						return;
					break;
				case 3:
					f.recv_chat();
					break;
				case 4:
					f.recv_public_key();
					switch (flag) {
					case 1:
						st.write(2, f.send_block(), os);
						return;
					case 2:
						st.write(1, os);
						break;
					case 5:
						st.write(6, os);
						break;

					default:
						break;
					}
					break;
				case 5:
					f.get_client_list();
					if (flag == 5)
						return;
					break;
					
				default:
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("socket closed");
		} finally {
			try {
				Thread.interrupted();
				st.socket_close(os, is, ClientSocket);
			} catch (Exception e) {
				System.out.println("fail socket close");
			}
		}
	}
}