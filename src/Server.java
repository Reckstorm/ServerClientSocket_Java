import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.UUID;

public class Server {
    public static final int PORT = 8080;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей

    public static class ReadAndSend extends Thread{
        @Override
        public void run()
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while(true)
                {
                    if (!serverList.isEmpty())
                    {
                        String msg = reader.readLine();
                        if (msg.equals("exit")) break;
                        for (ServerSomthing vr : serverList){
                            vr.send(msg);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {}
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        ReadAndSend broadcast = new ReadAndSend();
        broadcast.start();
        try {

            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomthing(socket)); // добавить новое соединенние в список
                    String name = String.valueOf(UUID.randomUUID());
                    serverList.getLast().setName(name);
                    System.out.println("New connection " + name);
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
