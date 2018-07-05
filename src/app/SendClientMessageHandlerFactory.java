package app;

import netUtils.Host;
import netUtils.MessageHandler;
import netUtils.MessageHandlerFactory;

/**
 * Created by Alex on 12.05.2017.
 */
public class SendClientMessageHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler createMessageHandler() {
        return null;
    }

    @Override
    public MessageHandler createMessageHandler(Host host) {
        return new SendClientsMessageHandler(host);
    }
}
