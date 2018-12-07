package func;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import socket.main;

public class func{
    /*
    Signal_number
    1 Send_Block
    2 Recv_Block
    3 Chat
    4 recv_public_key
    */
    byte[] msg;
    static crypto c=new crypto();
    
    FileInputStream fis=null;
    FileOutputStream fos=null;
	int readData=-1;
	int count;
	
    public void get_msg(byte[] msg){
        this.msg=de_packet(substr_byte(msg, 1,-1));
    }

    public byte[] send_block(){
        byte packet[]=null;
        packet=get_chain();
        return en_packet(packet);
    }
    
    public void recv_block(){
    	count=get_block_count();
    	if(count<Integer.parseInt(substr_byte(this.msg, 0, 1).toString())) {
        	if(verification_chain(get_chain(), this.msg)) {
        		save_chain(this.msg);
        	}	
    	}	
    }
    
    public void recv_public_key(){
        System.out.println(this.msg);
        c.get_enkey(new BigInteger(msg.toString()));
    }

    public BigInteger send_public_key(){
        BigInteger packet= null;
        PublicKey publickey=null;
        PrivateKey privatekey=null;

        SecureRandom secureRandom=new SecureRandom();
        KeyPairGenerator keyPairGenerator = null;

        try{
            keyPairGenerator=keyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512,secureRandom);

            KeyPair keyPair=keyPairGenerator.genKeyPair();
            publickey=keyPair.getPublic();
            privatekey=keyPair.getPrivate();

            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec=keyFactory.getKeySpec(publickey, RSAPublicKeySpec.class);
            RSAPublicKeySpec rsaPrivateCrtKeySpec=keyFactory.getKeySpec(privatekey, RSAPublicKeySpec.class);
            packet=rsaPublicKeySpec.getModulus();
            c.get_dekey(rsaPrivateCrtKeySpec.getModulus());
        }catch(Exception e){
            e.printStackTrace();
        }
        return packet;
    }
    
    public byte[] send_client_list() {
    	String temp=null;
    	for(String ip:main.ip) {
    		temp+=ip+'\n';
    	}
    	return en_packet(temp.getBytes());
    }
    
    byte[] en_packet(byte[] msg) {
    	byte[] packet=null;
    	
    	packet=c.encrypt(msg);
    	
    	return packet;
    }
    
    byte[] de_packet(byte[] packet) {
    	byte[] msg=null;
    	
    	msg=c.decrypt(packet);
    	
    	return msg;
    }
    
	private byte[] substr_byte(byte[] msg, int start_index, int end_index) {
        byte[] temp;
        
        if(end_index==-1)
        	end_index=msg.length-1;
        
		temp=new byte[end_index-start_index+1];
        for(int i=start_index;i<temp.length;i++) {
        	this.msg[i]=msg[i];
        }
        
        return temp;
	}
    
    private List<String> get_file_name(byte[] chain){
    	byte[] temp=null;
    	List file_name = new ArrayList();
    	count=0;
    	try {
    		int i=4;
    		int count=(Integer.parseInt(substr_byte(chain, 0, 3).toString())*64)+4;
    		while(i>count) {
    			file_name.add(new String(substr_byte(chain, i, i+64).toString()));
    			i+=64;
    		}
    	}catch (Exception e) {
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
    	List local_file_name = new ArrayList();
    	List syn_file_name = new ArrayList();
    	int file_count;
    	count=0;
    	byte[] temp=null;
    	String prev_hash=null;
    	try {
    			syn_file_name=get_file_name(SYN_chain);
    			local_file_name=get_file_name(local_chain);
    		if(local_file_name.get(0)==syn_file_name.get(0)){
        		file_count=syn_file_name.size();
    			for(Object fn: syn_file_name) {
        			count++;
        			temp=substr_byte(SYN_chain, (count*234)+4+(file_count*64), ((count+1)*234)+4+(file_count*64));
        			if(!fn.equals(sha256(temp).toString()))
        				return false;
        			
        			if(count!=1) {
        				if(!prev_hash.equals(substr_byte(temp, (count*234)+8+(file_count*64), (count*234)+72+(file_count*64))))
        					return false;
        			}
        			prev_hash=(String) fn;
        		}
    			return true;
    		}
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    	}
    	return false;
    }
    
    private int get_block_count() {
    	byte[] count=null;
    	try {
			fis=new FileInputStream("src/block/index");
			fis.read(count,0,3);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("block count : "+count.toString());
    	return Integer.parseInt(count.toString());
    }
    
    private byte[] get_chain() {
    	byte[] temp=null;
    	byte[] chain=null;
    	count=0;
    	try {
    		fis=new FileInputStream("src/block/index");
    		fis.read(temp);
    		fis.close();
    		System.arraycopy(temp, 0, chain, count, temp.length);
    		count+=temp.length;
    		chain=new byte[temp.length+(Integer.parseInt(substr_byte(temp, 0, 3).toString())*234)];
    		List file_name = new ArrayList();
    		file_name=get_file_name(temp);
    		for(Object fn: file_name) {
        		fis=new FileInputStream("src/block/"+(String) fn);
        		fis.read(temp);
        		System.arraycopy(temp, 0, chain, count, temp.length);
        		count+=temp.length;
        		fis.close();
    		}
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    	}
    	System.out.println("get chain : "+chain);
    	return chain;
    }
    
    private void save_chain(byte[] chain) {
    	List file_name = new ArrayList();
		file_name=get_file_name(chain);
		count=0;
		int file_count=file_name.size();
		for(Object fn:file_name) {
			try {
				count++;
				fos=new FileOutputStream("src/block/"+fn);
				fos.write(substr_byte(chain, (count*234)+4+(file_count*64), ((count+1)*234)+4+(file_count*64)));
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
}
