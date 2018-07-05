package netUtils;

import javafx.util.Pair;

import java.net.Socket;

/**
 * Created by Alex on 31.03.2017.
 */
public interface MessageHandler {
    public String handle(String message);
    public String handle(String message, Pair<Socket, String> client);

}
