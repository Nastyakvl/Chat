package netUtils;

/**
 * Created by Alex on 31.03.2017.
 */
public interface MessageHandlerFactory {
    MessageHandler createMessageHandler();
    MessageHandler createMessageHandler(Host host);
}
