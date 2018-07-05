package app;

import concurrentUtils.Stoppable;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import netUtils.Host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Alex on 12.05.2017.
 */
public class ClientListener implements Stoppable {

    private Socket socket;
    private TextArea chat;
    private TextArea online;
    private boolean isAlive;
    private Thread thread;

    public ClientListener(Socket socket, TextArea chat, TextArea online) {
        this.socket = socket;
        this.chat = chat;
        this.thread = new Thread(this);
        this.isAlive = true;
        this.thread.start();
        this.online = online;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message;
            while (!socket.isClosed()) {
                if (dataInputStream.available() > 0) {
                    message = dataInputStream.readUTF();
                    if (message.charAt(0) == '\n') {
                        String msg = message.replaceFirst("\n", "");
                        online.clear();
                        online.appendText(msg);
                    } else
                        chat.appendText(message + "\n");
                }
            }

        } catch (IOException e) {
            if (isAlive)
                isAlive = false;
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        isAlive = false;
    }

}


