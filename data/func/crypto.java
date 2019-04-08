package data.func;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.crypto.Cipher;

class crypto {

	private static PublicKey enkey;
	private static PrivateKey dekey;

	public void get_enkey(PublicKey enkey) {
		crypto.enkey = enkey;
	}

	public void get_dekey(PrivateKey dekey) {
		crypto.dekey = dekey;
	}

	public byte[] encrypt(byte[] msg) {
		byte[] packet = null;
		byte[] temp = null;
		Cipher cipher;
		int count = 0;
		int msg_length = msg.length;
		int packet_count = 0;
		byte[] packet_temp = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, enkey);
			if (msg_length > 245) {
				packet = new byte[((int) Math.ceil(msg_length / 245.0) * 256)];
				while (count <= msg_length) {
					temp = new byte[245];
					System.arraycopy(msg, count, temp, 0,((msg.length-count>245)?245:msg.length-count));
					packet_temp = cipher.doFinal(temp);
					System.arraycopy(packet_temp, 0, packet, packet_count, 256);
					count += 245;
					packet_count += 256;
				}
			} else {
				packet = cipher.doFinal(msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packet;
	}

	public byte[] decrypt(byte[] packet) {
		byte[] msg = null;
		byte[] temp = null;
		Cipher cipher;
		int count = 0;
		int packet_length = packet.length;
		byte[] msg_temp = null;
		ArrayList<Byte> msg_list = new ArrayList<Byte>();
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, dekey);
			if (packet_length > 256) {
				while (count < packet_length) {
					temp = new byte[256];
					System.arraycopy(packet, count, temp, 0, 256);
					msg_temp = cipher.doFinal(temp);
					for (Byte b : msg_temp) {
						msg_list.add(b);
					}
					count += 256;
				}
				msg = new byte[msg_list.size()];
				count = 0;
				for (Byte b : msg_list) {
					msg[count] = b;
					count += 1;
				}
			} else {
				msg = cipher.doFinal(packet);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

}