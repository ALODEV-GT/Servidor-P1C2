package midik.sockets;

import com.example.pruebaclientemusic.message.SimpleMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import midik.recibirCancion.ProcesarCancion;

public class Listener extends Thread {

    private int portServer;

    public Listener(int portServer) {
        this.portServer = portServer;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(this.portServer)) {
            while (true) {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                System.out.printf("Listening on %s:%d\n", hostAddress, this.portServer);
                Socket socket = server.accept();
                System.out.println("Cliente recibido");
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                //Leyendo el mensaje
                SimpleMessage request = (SimpleMessage) inputStream.readObject();
                ProcesarCancion pc = new ProcesarCancion(request.getMessage());
                boolean procesado = pc.procesar();

                //Respondiendo al cliente
                if (procesado) {
                    SimpleMessage response = new SimpleMessage("Se ha guardado la cancion " + LocalDateTime.now());
                    outputStream.writeObject(response);
                } else {
                    SimpleMessage response = new SimpleMessage("No se pudo guardar la cancion" + LocalDateTime.now());
                    outputStream.writeObject(response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
    }

    public int getPortServer() {
        return portServer;
    }

    public void setPortServer(int portServer) {
        this.portServer = portServer;
    }
}
