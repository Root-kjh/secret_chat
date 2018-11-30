package data.func;

import java.math.BigInteger;

class crypto{

    private static BigInteger enkey,dekey;
    public void get_enkey(BigInteger enkey){
        this.enkey=enkey;
    }

    public void get_dekey(BigInteger dekey){
        this.dekey=dekey;
    }

    public byte encrypt(byte msg){
        byte packet=(Byte) null;

        return packet;
    }

    public byte decrypt(byte packet){
        byte msg=(Byte) null;

        return msg;
    }
}