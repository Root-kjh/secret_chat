package data.func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import data.socket.get_block_socket;
import data.socket.main;
import data.socket.send_block_socket;

public class func {
	/*
	 * Signal_number 1 Send_Block 2 Recv_Block 3 Chat 4 recv_public_key 5
	 * get_clients
	 */
	byte[] msg;
	static crypto c = new crypto();

	FileInputStream fis = null;
	FileOutputStream fos = null;
	int readData = -1;
	int count;

	public void get_msg(byte[] msg) {
		System.out.println("packet_length : " + msg.length);
		if (msg.length == 1)
			this.msg = msg;
		else if (msg[0] != 4) {
			this.msg = de_packet(substr_byte(msg, 1, -1));
		} else
			this.msg = substr_byte(msg, 1, -1);
	}

	/*
	 * 
	 * Block_structure
	 * 
	 * synchronization Hash_algorithm : sha256 Hash_size : 64
	 * 
	 * packet
	 * 
	 * Block_name : 64
	 * 
	 * HEAD time : 4 prev_hash : 64
	 * 
	 * BODY PW(sha512) :128 IP : 4 idx : 4 name : 20 id : 10 234
	 * 
	 */

	/*
	 * Chain_Structure
	 * 
	 * block_count : 4 block_name : 64*block_count block : 234
	 * 
	 */

	public void get_client_list() {
		String my_ip = null;
		try {
			URL ipAdress = new URL("http://myexternalip.com/raw");
			BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
			my_ip = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] ip = (new String(this.msg)).split("\\n");
		main.ip_s.clear();
		for (String i : ip) {
			if (!i.equals(my_ip))
				main.ip_s.add(i);
		}
		System.out.println(main.ip_s);
	}

	public void syn_block(String ip) {
		for (String b : main.ip_s) {
			try {
				Socket get_socket = new Socket(b, 3141);
				new get_block_socket(get_socket);

				Socket send_socket = new Socket(b, 3141);
				new send_block_socket(send_socket);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return;
	}

	public byte[] send_block() {
		byte packet[] = null;
		packet = get_chain();
		return en_packet(packet);
	}

	public void recv_block() {
		int count = get_block_count();
		if (count < (int) byteToint(substr_byte(this.msg, 0, 3))) {
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

	public void add_block(String pw, String ip, String name, String id) {
		ArrayList<String> file_name = new ArrayList<String>();
		file_name = (ArrayList<String>) get_file_name(get_index());
		String prev_hash = (String) file_name.get(file_name.size() - 1);
		int file_count = get_block_count();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
		byte[] file = new byte[234];

		try {
			System.arraycopy(hexStringToByteArray(Integer.toString((int) timestamp.getTime(), 16)), 0, file, 0, 4);
			System.arraycopy(prev_hash.getBytes(), 0, file, 4, 64);
			System.arraycopy(sha512(pw), 0, file, 68, 128);
			System.arraycopy(ip.getBytes(), 0, file, 196, 4);
			byte[] file_count_byte=new byte[4];
			int count=0;
			for (int i = 1000; i >= 1; i/=10) {
				file_count_byte[count]=(byte) (file_count/i);
				count++;
			}
			System.arraycopy(file_count_byte, 0, file, 200, 4);
			byte[] name_byte = new byte[20];
			count=19;
			for (int i = (name.length()-1); i >=0; i--) {
				name_byte[count]=name.getBytes()[i];
				count--;
			}
			System.arraycopy(name_byte, 0, file, 204, 20);
			
			byte[] id_byte = new byte[10];
			count=9;
			for (int i = (id.length()-1); i >=0; i--) {
				id_byte[count]=id.getBytes()[i];
				count--;
			}
			System.arraycopy(id_byte, 0, file, 224, 10);
			String File_sha256=sha256(file);
			fos = new FileOutputStream("block\\" + File_sha256);
			fos.write(file);
			fos.close();
			byte[] temp=get_index();
			byte[] index_byte=new byte[temp.length+64];
			System.arraycopy(temp, 0, index_byte, 0, temp.length);
			System.arraycopy(File_sha256.getBytes(), 0, index_byte, (file_count*64)+4, 64);
			file_count+=1;
			System.arraycopy(ByteBuffer.allocate(4).putInt(file_count).array(), 0, index_byte, 0, 4);
			fos=new FileOutputStream("block\\index");
			fos.write(index_byte);
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String Search_Block(String id, String passwd) {
		
		List<String> file_names=get_file_name(get_index());
		String file_hash="";
		byte[] chain_id = new byte[10], chain_pw=new byte[128];
		for (String fn : file_names) {
			try {
				fis=new FileInputStream("block\\"+fn);
				fis.skip(68);
				fis.read(chain_pw, 0, 128);
				fis.skip(28);
				fis.read(chain_id, 0, 10);
				if(id.equals(new String(chain_id).trim()) &&new String(sha512(passwd)).equals(new String(chain_pw))) {
					file_hash=fn;
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return file_hash;
		
	}

	byte[] en_packet(byte[] msg) {
		byte[] packet = null;

		packet = c.encrypt(msg);

		return packet;
	}

	byte[] de_packet(byte[] packet) {
		byte[] msg = null;

		msg = c.decrypt(packet);

		return msg;
	}
	
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
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
			int count = (byteToint(substr_byte(chain, 0, 3)) * 64);
			while (i < count) {
				file_name.add(new String(substr_byte(chain, i, i + 63)));
				i += 64;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return file_name;
	}

	private static String sha256(byte[] msg) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(msg);
		String toReturn = String.format("%040x", new BigInteger(1, md.digest()));
		return toReturn;
	}

	private static byte[] sha512(String msg) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.reset();
		md.update(msg.getBytes("utf-8"));
		String toReturn = String.format("%040x", new BigInteger(1, md.digest()));
		return toReturn.getBytes();
	}

	private boolean verification_chain(byte[] local_chain, byte[] SYN_chain) {
		List<String> local_file_name = new ArrayList<String>();
		List<String> syn_file_name = new ArrayList<String>();
		int file_count;
		count = 0;
		byte[] temp = null;
		String prev_hash = null;
		try {
			syn_file_name = get_file_name(SYN_chain);
			local_file_name = get_file_name(local_chain);
			if (local_file_name.get(0).equals(syn_file_name.get(0))) {
				file_count = syn_file_name.size();
				for (Object fn : syn_file_name) {
					temp = substr_byte(SYN_chain, (count * 234) +(file_count * 64)+4,
							((count + 1) * 234) + 3 + (file_count * 64));
					if (!fn.equals(sha256(temp)))
						return false;
					if (count != 0) {
						if (!prev_hash.equals(new String(substr_byte(temp, 4, 67))))
							return false;
					}
					prev_hash = (String) fn;
					count++;
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
			File path = new File("block\\index");
			fis = new FileInputStream(path);
			fis.read(count, 0, 4);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) byteToint(count);
	}

	private byte[] get_index() {
		byte[] temp = null;
		try {
			File index = new File("block\\index");
			fis = new FileInputStream(index);
			temp = new byte[(int) index.length()];
			fis.read(temp);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	private byte[] get_chain() {
		byte[] temp;
		byte[] chain = null;
		int count = 0;
		try {
			temp=get_index();
			chain = new byte[temp.length + (byteToint(substr_byte(temp, 0, 3)) * 234)];
			System.arraycopy(temp, 0, chain, count, temp.length);
			count += temp.length;
			List<String> file_name = new ArrayList<String>();
			file_name = get_file_name(temp);
			for (String fn : file_name) {
				fis = new FileInputStream("block\\" + fn);
				temp = new byte[234];
				fis.read(temp);
				System.arraycopy(temp, 0, chain, count, 234);
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
		List<String> file_name = new ArrayList<String>();
		file_name = get_file_name(chain);
		count = 0;
		int file_count = file_name.size();
		for (Object fn : file_name) {
			try {
				fos = new FileOutputStream("block\\index");
				fos.write(chain,0,(file_count*64)+4);
				fos.close();
				fos = new FileOutputStream("block\\" + fn);
				fos.write(substr_byte(chain, (count * 234) + (file_count * 64) + 4,
						((count + 1) * 234) + 3 + (file_count * 64)));
				fos.close();
				count++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void recv_chat() {
		System.out.println(this.msg.toString());
	}
}