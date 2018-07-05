package netUtils;

import concurrentUtils.Stoppable;
import javafx.util.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


/**
 * Created by Alex on 17.02.2017.
 */
public class Session implements Stoppable {
    private Pair<Socket, String> client;
    private MessageHandler messageHandler;
    private Host host;

    public Session(Pair<Socket, String> client, MessageHandler messageHandler, Host host) {
        this.client = client;
        this.messageHandler = messageHandler;
        this.host = host;
    }

    @Override
    public void run() {
        InputStream inputStream;
        try {
            host.addClient(client);
            Socket clientSocket = client.getKey();
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF("started");

            inputStream = clientSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String message;
            messageHandler.handle("\nAdd", client);
            while (!clientSocket.isClosed()) {
                message = dataInputStream.readUTF();
                messageHandler.handle(message, client);
            }
        } catch (IOException e) {
            System.out.println("Connection interrupted in Session");
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (client.getKey() != null) {
            host.removeClient(client.getKey());
            messageHandler.handle("\nLeft", client);
        }
    }
}
