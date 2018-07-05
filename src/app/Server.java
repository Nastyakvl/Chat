package app;

import concurrentUtils.Channel;
import concurrentUtils.Dispatcher;
import concurrentUtils.ThreadPool;
import netUtils.Host;
import netUtils.*;


/**
 * Created by Alex on 22.03.2017.
 */
public class Server {
    public static void main(String[] args) {
        try {

            MessageHandlerFactory messageHandlerFactory;
            String messageHandlerImpl = args[2];
            Class classFactory = Class.forName(messageHandlerImpl);
            messageHandlerFactory = (MessageHandlerFactory) classFactory.newInstance();
            messageHandlerFactory.createMessageHandler();

            Integer port = Integer.parseInt(args[0]);
            Integer maxSession = Integer.parseInt(args[1]);
            Channel channel = new Channel(maxSession);
            Host host = new Host(port, channel, messageHandlerFactory);
            Thread hostThread = new Thread(host);
            hostThread.start();
            ThreadPool threadPool = new ThreadPool(maxSession);
            Dispatcher dispatcher = new Dispatcher(channel, threadPool);
            Thread dispatcherThread = new Thread(dispatcher);
            dispatcherThread.start();
            Runtime.getRuntime().addShutdownHook(new Thread(()-> {
                host.stop();
                dispatcher.stop();
                threadPool.stop();
            }));


        } catch (NumberFormatException e) {
            System.out.println("Invalid format of an argument: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument: " + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("Class not found\n" + e);
        } catch (SecurityException e) {
            System.out.println("Security exception captured\n" + e);
        } catch (ExceptionInInitializerError e) {
            System.out.println("Security violation\n" + e);
        } catch (LinkageError e) {
            System.out.println("Linkage fails\n" + e);
        }
    }
}
