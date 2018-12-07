package data.func;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class chat implements Runnable{
	private BufferedWriter bw=null;
	
	public chat(BufferedWriter bw) {
		this.bw=bw;
	}
	
	@Override
	public void run() {
		func f=new func();
		String chat=null;
		Scanner scan=new Scanner(System.in);
		while(!Thread.interrupted()) {
			chat=scan.nextLine();
			try {
				bw.write("3"+f.en_packet(chat.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
