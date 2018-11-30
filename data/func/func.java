package data.func;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

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
    public void get_msg(byte[] msg){
        this.msg=msg;
    }

    public byte send_block(){
        byte packet=(Byte) null;

        return packet;
    }

    public void recv_block(){
    	
    }
    
    public void chat(){
    	System.out.println(msg.toString());
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
}