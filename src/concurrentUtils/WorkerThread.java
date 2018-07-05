package concurrentUtils;

import netUtils.Session;

/**
 * Created by Alex on 24.03.2017.
 */
public class WorkerThread implements Stoppable {
    private Thread thread;
    private ThreadPool threadPool;
    private Runnable currentTask;
    private final Object lock = new Object();
    private boolean isAlive;

    public WorkerThread(ThreadPool threadPool) {
        currentTask = null;
        this.threadPool = threadPool;
        this.thread = new Thread(this);
        isAlive = true;
        this.thread.start();
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (isAlive) {
                while (currentTask == null)
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Thread was interrupted while thread was waiting in concurrentUtils.WorkerThread");
                        if(!isAlive)
                            return;
                    }

                try {
                    currentTask.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } finally {
                    currentTask = null;
                    threadPool.onTaskCompletetd(this);
                }
                //Если нет задачи для выполнения, ждем
                //Если есть, выполняем
            }
        }
    }

    public void execute(Runnable task) {
        synchronized (lock) {
            if (currentTask != null)
                throw new IllegalStateException("Current task is not null");
            currentTask = task;
            lock.notifyAll();
        }
    }

    @Override
    public void stop() {
        if (isAlive)
            isAlive = false;
        if (currentTask != null)
            ((Session) currentTask).stop();
        this.thread.interrupt();
    }
}
