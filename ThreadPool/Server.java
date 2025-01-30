package ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public final ExecutorService threadPool;

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) throws IOException {
        try(PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)){
            toSocket.println("Hello from Server" + clientSocket.getInetAddress());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("server listening on port " + port);
            while(true){
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(() -> {
                    try {
                        server.handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            server.threadPool.shutdown();
        }
    }
}
