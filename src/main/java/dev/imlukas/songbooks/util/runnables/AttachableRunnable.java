package dev.imlukas.songbooks.util.runnables;

public class AttachableRunnable {

    private Runnable runnable = () -> {
    };

    public AttachableRunnable() {
    }

    public AttachableRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void attach(Runnable runnable) {
        Runnable old = this.runnable;

        this.runnable = () -> {
            old.run();
            runnable.run();
        };
    }

    public void run() {
        runnable.run();
    }

}