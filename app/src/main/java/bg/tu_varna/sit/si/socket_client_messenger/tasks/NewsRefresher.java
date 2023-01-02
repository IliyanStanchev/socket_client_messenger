package bg.tu_varna.sit.si.socket_client_messenger.tasks;

import android.os.HandlerThread;
import android.os.Handler;

import bg.tu_varna.sit.si.socket_client_messenger.interfaces.INewRefreshListener;

public class NewsRefresher implements Runnable {

    private Handler handler;
    private HandlerThread handlerThread;
    private INewRefreshListener listener;

    public NewsRefresher(INewRefreshListener listener) {

        this.listener = listener;

        this.handlerThread = new HandlerThread("News refresher");
        this.handlerThread.start();

        this.handler = new Handler(handlerThread.getLooper());

    }

    @Override
    public void run() {

        final long delayMillis = 5000;

        listener.onRefresh();

        handler.postDelayed(this, delayMillis);
    }
}

