package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ServerThread implements Runnable {
	private String connect_ip;
	private Socket ClientSocket;
	private InputStream is;
	private OutputStream os;
	private byte[] temp=new byte[65535];
	private func.func f;
	private int packet_length;
	
	public ServerThread(Socket s, String ip) {
		ClientSocket = s;
		ClientSocket = s;
		connect_ip = ip;
		f = new func.func();
		try {
			is = ClientSocket.getInputStream();
			os = ClientSocket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(int number, byte[] msg) throws IOException {
		byte[] temp=new byte[msg.length+1];
		temp[0]=(byte) number;
		System.arraycopy(msg, 0, temp, 1, msg.length);
		os.write(temp);
		os.flush();
	}

	public void write(char number) throws IOException {
		os.write(number);
		os.flush();
	}

	public void run() {
		try {
			os.flush();
			while ((packet_length= is.read(temp)) != -1) {
				byte[] msg=new byte[packet_length];
				System.arraycopy(temp, 0, msg, 0, packet_length);
				f.get_msg(msg);
				switch (msg[0]) {
				case 1:
					write(2, f.send_block());
					break;
				case 2:
					f.recv_block();
					break;
				case 4:
					f.recv_public_key();
					write(4, f.send_public_key());
					break;
				case 6:
					write(5, f.send_client_list());
				default:
					break;
				}
			}
			System.out.println("while stop!!");
		} catch (Exception e) {
			System.out.println("socket closed");
		} finally {		
			try {
				if (ClientSocket != null) {
					System.out.println("close");
					for (String ip : main.ip) {
						if (connect_ip.equals(ip)) {
							main.ip.remove(ip);
							break;
						}
					}
					os.close();
					is.close();
					ClientSocket.close();
				}
			} catch (Exception e) {
				System.out.println("fail socket close");
			}
		}
	}
}
