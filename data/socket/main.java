package data.socket;

import java.net.ServerSocket;
import java.net.Socket;

public class main {

    public static void main(String[] args) {
        try{
            ServerSocket server=new ServerSocket(3141);
            Socket ClientSocket=null;   
            ServerThread sr; 
            while(true){
                ClientSocket=server.accept();
                if(ClientSocket!=null){
                    sr=new ServerThread(ClientSocket);
                    Thread t=new Thread(sr);
                    t.start();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}