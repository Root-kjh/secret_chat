import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ServerThread implements Runnable{
    private static Socket ClientSocket;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static String msg;
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
            func f;
            bw.write(f.send_public_key());
            while((msg=br.readLine())!=null){
                f=new func(msg);
                switch (msg.charAt(0)) {
                    case '1':
                        bw.write(f.send_block());
                        break;
                    case '2':
                        f.recv_block();
                        break;
                    case '3':
                        f.chat();
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