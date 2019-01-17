package data.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class socket_tool {

	public void write(int number, byte[] msg, OutputStream os) {
		byte[] temp = new byte[msg.length + 1];
		try {
			temp[0]=(byte) number;
			System.arraycopy(msg, 0, temp, 1, msg.length);
			os.flush();
			os.write(temp);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(int i, OutputStream os) throws IOException {
		os.write(i);
		os.flush();
	}

	public byte[] readLine(InputStream is) throws IOException {
		byte[] temp=new byte[65535];
		int packet_length= is.read(temp);
			if(packet_length==-1)
				return null;
			byte[] msg=new byte[packet_length];
			System.arraycopy(temp, 0, msg, 0, packet_length);
		return msg;

	}

	public void socket_close(OutputStream os, InputStream is, Socket ClientSocket) {
		if (ClientSocket != null) {
			try {
				os.close();
				is.close();
				ClientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
}
