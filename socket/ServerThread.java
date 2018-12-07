package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


class ServerThread implements Runnable{
	private String connect_ip;
    private static Socket ClientSocket;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static byte[] msg;
    public ServerThread(Socket s,String ip){
        ClientSocket=s;
        connect_ip=ip;
        try{
            br=new BufferedReader(new InputStreamReader(ClientSocket.getInputStream(),"utf-8"));
            bw=new BufferedWriter(new OutputStreamWriter(ClientSocket.getOutputStream(),"utf-8"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            func.func f = new func.func();
            bw.write('4'+f.send_public_key().toString());
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
                        bw.write("5"+f.send_client_list());
                        break;
                    case '4':
                        f.recv_public_key();
                        break;
                    default:
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(ClientSocket!=null){
                	for (String ip : main.ip) {
                		if(connect_ip.equals(ip))
                			main.ip.remove(ip);
                	}
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
