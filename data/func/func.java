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
    String msg;
    static crypto c=new crypto();
    public void func(String msg){
        this.msg=msg;
    }

    public byte send_block(){
        byte packet=null;

        return packet;
    }

    public void recv_block(){

    }
    
    public void chat(){

    }

    public void recv_public_key(){

    }

    public byte send_public_key(){
        byte packet=null;
        PublicKey publickey=null;
        PrivateKey privatekey=null;

        SecureRandom secureRandom=new SecureRandom();
        KeyPairGenerator keyPairGenerator;

        try{
            keyPairGenerator=keyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512,secureRandom);

            KeyPair keyPair=keyPairGenerator.genKeyPair();
            publickey=keyPair.getPublic();
            privatekey=keyPair.getPrivate();

            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec=keyFactory.getKeySpec(publickey, RSAPublicKeySpec.class);
            RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec=keyFactory.getKeySpec(privatekey, RSAPublicKeySpec.class);
            packet=rsaPublicKeySpec.getModulus();
            c.get_dekey(rsaPrivateCrtKeySpec.getModulus());
        }catch(Exception e){
            e.printStackTrace();
        }
        return packet;
    }
}