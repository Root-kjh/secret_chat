package func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import socket.main;

public class func {
	/*
	 * Signal_number 1 Send_Block 2 Recv_Block 3 Chat 4 recv_public_key
	 */
	byte[] msg;
	static crypto c = new crypto();

	FileInputStream fis = null;
	FileOutputStream fos = null;
	int readData = -1;
	int count;

	public void get_msg(byte[] msg) {
		System.out.println("packet_length : "+msg.length);
		if (msg.length == 1)
			this.msg = msg;
		else if (msg[0] != 4)
			this.msg = de_packet(substr_byte(msg, 1, -1));
		else
			this.msg = substr_byte(msg, 1, -1);

	}

	public byte[] send_block() {
		byte packet[] = null;
		packet = get_chain();
		System.out.println(packet);
		return en_packet(packet);
	}

	public void recv_block() {
		count = get_block_count();
		if (count < Integer.parseInt(substr_byte(this.msg, 0, 1).toString())) {
			if (verification_chain(get_chain(), this.msg)) {
				save_chain(this.msg);
			}
		}
	}

	public void recv_public_key() {
		PublicKey publickey = null;
		try {
			publickey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(this.msg));
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.get_enkey(publickey);
	}

	public byte[] send_public_key() {
		byte[] packet = null;
		PublicKey publickey;
		PrivateKey privatekey;

		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator = null;

		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048, secureRandom);

			KeyPair keyPair = keyPairGenerator.genKeyPair();
			publickey = keyPair.getPublic();
			privatekey = keyPair.getPrivate();
			packet = publickey.getEncoded();
			c.get_dekey(privatekey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
	}

	public byte[] send_client_list() {
		String temp = "";
		for (String ip : main.ip) {	
			temp += ip.substring(1) + '\n';
		}
		return en_packet(temp.getBytes());
	}

	byte[] en_packet(byte[] msg) {
		byte[] packet = null;

		try {
			packet = c.encrypt(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return packet;
	}

	byte[] de_packet(byte[] packet) {
		byte[] msg = null;

		try {
			msg = c.decrypt(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;
	}

	private byte[] substr_byte(byte[] msg, int start_index, int end_index) {
		if (end_index == -1)
			end_index = msg.length - 1;
		byte[] temp = new byte[end_index - start_index + 1];
		int count = 0;
		for (int i = start_index; i <= end_index; i++) {
			temp[count] = msg[i];
			count++;
		}

		return temp;
	}

	private List<String> get_file_name(byte[] chain) {
		byte[] temp = null;
		List<String> file_name = new ArrayList<String>();
		count = 0;
		try {
			int i = 4;
			int count = (byteToint(substr_byte(chain, 0, 3)) * 63) + 4;
			while (i < count) {
				file_name.add(new String(substr_byte(chain, i, i + 63)));
				i += 63;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return file_name;
	}

	private static byte[] sha256(byte[] msg) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg);

		return md.digest();
	}

	private static byte[] sha512(byte[] msg) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(msg);

		return md.digest();
	}

	private boolean verification_chain(byte[] local_chain, byte[] SYN_chain) {
		List<?> local_file_name = new ArrayList<Object>();
		List<?> syn_file_name = new ArrayList<Object>();
		int file_count;
		count = 0;
		byte[] temp = null;
		String prev_hash = null;
		try {
			syn_file_name = get_file_name(SYN_chain);
			local_file_name = get_file_name(local_chain);
			if (local_file_name.get(0) == syn_file_name.get(0)) {
				file_count = syn_file_name.size();
				for (Object fn : syn_file_name) {
					count++;
					temp = substr_byte(SYN_chain, (count * 234) + 4 + (file_count * 64),
							((count + 1) * 234) + 4 + (file_count * 64));
					if (!fn.equals(sha256(temp).toString()))
						return false;

					if (count != 1) {
						if (!prev_hash.equals(substr_byte(temp, (count * 234) + 8 + (file_count * 64),
								(count * 234) + 72 + (file_count * 64))))
							return false;
					}
					prev_hash = (String) fn;
				}
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	private int get_block_count() {
		byte[] count = new byte[4];
		try {
			fis = new FileInputStream("/block/index");
			fis.read(count, 0, 3);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("block count : " + count.toString());
		return Integer.parseInt(count.toString());
	}

	private byte[] get_chain() {
		byte[] temp;
		byte[] chain = null;
		int count = 0;
		try {
			File index=new File("/block/index");
			fis = new FileInputStream("/block/index");
			temp=new byte[(int) index.length()];
			fis.read(temp);
			fis.close();
			chain = new byte[temp.length + (byteToint(substr_byte(temp, 0, 3)) * 234)];
			System.arraycopy(temp, 0, chain, count, temp.length);
			count += temp.length;
			List<String> file_name = new ArrayList<String>();
			file_name = get_file_name(temp);
			for (String fn : file_name) {
				fis = new FileInputStream("/block/" + fn);
				temp=new byte[234];
				fis.read(temp);
				System.arraycopy(temp, 0, chain, count, temp.length);
				count += temp.length;
				fis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return chain;
	}

	private int byteToint(byte[] arr) {
		return (arr[0] & 0xff) << 24 | (arr[1] & 0xff) << 16 | (arr[2] & 0xff) << 8 | (arr[3] & 0xff);
	}

	private void save_chain(byte[] chain) {
		List<?> file_name = new ArrayList<Object>();
		file_name = get_file_name(chain);
		count = 0;
		int file_count = file_name.size();
		for (Object fn : file_name) {
			try {
				count++;
				fos = new FileOutputStream("/block/" + fn);
				fos.write(substr_byte(chain, (count * 234) + 4 + (file_count * 64),
						((count + 1) * 234) + 4 + (file_count * 64)));
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
