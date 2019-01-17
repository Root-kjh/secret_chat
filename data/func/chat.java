package data.func;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import data.socket.socket_tool;

public class chat implements Runnable {

	private socket_tool st;
	private Scanner scan;
	private String chat;
	private func f;
	private OutputStream os;

	public chat(Socket s) {
		st = new socket_tool();
		f = new func();
		chat = null;
		scan = new Scanner(System.in);
		try {
			os = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			chat = scan.nextLine();
			try {
				st.write('3', f.en_packet(chat.getBytes()), os);
			} catch (Exception e) {
				try {
					os.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
