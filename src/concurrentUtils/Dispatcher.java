package concurrentUtils;

import netUtils.Session;

/**
 * Created by Alex on 17.03.2017.
 */
public class Dispatcher implements Stoppable {

    private final Channel channel;
    private ThreadPool threadPool;
    private volatile boolean isActive;

    public Dispatcher(Channel channel, ThreadPool threadPool) {
        this.channel = channel;
        this.threadPool = threadPool;
        isActive = true;
    }

    @Override
    public void run() {
        while (isActive) {
            Runnable session = channel.take();
            threadPool.execute(session);
        }
    }

    @Override
    public void stop() {
        if (isActive)
            isActive = false;
        while (channel.getSize() > 0)
            ((Session) channel.take()).stop();

    }
}
