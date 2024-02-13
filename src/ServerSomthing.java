import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSomthing extends Thread {
    private Socket socket; // сокет, через который сервер общается с клиентом,
    private BufferedWriter out; // поток записи в сокет
    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }
//    @Override
//    public void run() {
////        String word;
////        try {
////
////            while (true) {
////                word = in.readLine();
////                if(word.equals("stop")) {
////                    break;                }
////                for (ServerSomthing vr : Server.serverList) {
////                    vr.send(word); // отослать принятое сообщение с
////                    // привязанного клиента всем остальным включая его
////                }
////            }
////        } catch (IOException e) {
////        }
//    }

    public void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
