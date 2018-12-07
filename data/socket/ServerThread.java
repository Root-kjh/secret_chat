package data.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import data.func.chat;


class ServerThread implements Runnable{
    private static Socket ClientSocket;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static byte[] msg;
    public ServerThread(Socket s){
        ClientSocket=s;

        try{
            br=new BufferedReader(new InputStreamReader(ClientSocket.getInputStream(),"utf-8"));
            bw=new BufferedWriter(new OutputStreamWriter(ClientSocket.getOutputStream(),"utf-8"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            data.func.func f = new data.func.func();
            bw.write('4'+f.send_public_key().toString());
        	chat chat=new chat(bw);
        	Thread thread=new Thread(chat);
        	thread.start();
            while((msg=br.readLine().getBytes())!=null){
                f.get_msg(msg);
                switch (msg[0]) {
                    case '1':
                        bw.write("2"+f.send_block());
                        break;
                    case '2':
                        f.recv_block();
                        break;
                    case '3':
                        f.recv_chat();
                        break;
                    case '4':
                        f.recv_public_key();
                        break;
                    case '5':
                    	f.get_client_list();
                    default:
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
            	Thread.interrupted();
                if(ClientSocket!=null){
                    br.close();
                    bw.close();
                    ClientSocket.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}