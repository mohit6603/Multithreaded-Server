package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(10000);

        while (true) {
            try {
                System.out.println("server is listening on port " + port);
                Socket acceptConnection = socket.accept();
                System.out.println("connection accepted from " + acceptConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptConnection.getInputStream()));
                toClient.println(" hello from server");
                toClient.close();
                fromClient.close();
                acceptConnection.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("namaste");
        Server server = new Server();
        try {
            server.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
