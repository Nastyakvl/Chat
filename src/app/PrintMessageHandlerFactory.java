package app;

import app.PrintMessageHandler;
import netUtils.Host;
import netUtils.MessageHandler;
import netUtils.MessageHandlerFactory;

/**
 * Created by Alex on 31.03.2017.
 */
public class PrintMessageHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler createMessageHandler() {
        return new PrintMessageHandler();
    }

    @Override
    public MessageHandler createMessageHandler(Host host) {
        return null;
    }
}
