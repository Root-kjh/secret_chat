package data.socket;

import java.net.Socket;

import data.func.chat;

public class ServerThread implements Runnable {
	private static Socket ClientSocket;
	private chat chat;
	private Thread thread;
	private Socket_parent sp;	

	public ServerThread(Socket s) {
		ClientSocket = s;
		this.chat = new chat(ClientSocket);
		this.thread = new Thread(chat);
		sp = new Socket_parent(ClientSocket, 0);
	}

	public void run() {
		try {
			thread.start();
			sp.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Thread.interrupted();
		}
	}
}