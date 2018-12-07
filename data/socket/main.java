package data.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import GUI.index.Login;
import javafx.application.Application;

public class main{
	
	public static List<String> ip_s=new ArrayList<String>();
	
    public static void main(String[] args) {
        try{
            ServerSocket server=new ServerSocket(3141);
            Socket ClientSocket=null;   
            ServerThread sr;
            GUI.index.Login index=new Login();
            Application.launch(index.getClass(), args);
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