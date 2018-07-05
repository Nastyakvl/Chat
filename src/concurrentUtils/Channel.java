package concurrentUtils;

import java.io.IOError;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Alex on 17.03.2017.
 */
public class Channel {
    private final int maxCounter;
    private final LinkedList<Runnable> queue = new LinkedList<>();
    private final Object lock = new Object();

    public Channel(int maxCounter) {
        this.maxCounter = maxCounter;
    }

    public void put(Runnable x) {
        synchronized(lock) {
            try {
                while (queue.size() >= maxCounter)
                    lock.wait();
                queue.addLast(x);
                lock.notifyAll();
            } catch (InterruptedException e) {
                System.out.println("Interrupted \n" + e.getMessage());
            }
        }
    }

    public Runnable take() {
        synchronized(lock) {
            try {
                while (queue.isEmpty())
                    lock.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            lock.notifyAll();
            return queue.removeFirst();
        }
    }

    public Integer getSize()
    {
        synchronized (lock)
        {
            return queue.size();
        }
    }
}
