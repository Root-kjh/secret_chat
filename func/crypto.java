package func;

import java.math.BigInteger;
import java.security.Key;
import javax.crypto.Cipher;

class crypto{

    private static Key enkey,dekey;
    public void get_enkey(BigInteger enkey){
        this.enkey=(Key) enkey;
    }

    public void get_dekey(BigInteger dekey){
        this.dekey=(Key) dekey;
    }

    public byte[] encrypt(byte[] msg){
        byte[] packet=null;
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, enkey);  
	        packet= cipher.doFinal(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return packet;
    }

    public byte[] decrypt(byte[] packet){
        byte[] msg=null;
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.DECRYPT_MODE, enkey);  
	        packet= cipher.doFinal(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return msg;
    }
}