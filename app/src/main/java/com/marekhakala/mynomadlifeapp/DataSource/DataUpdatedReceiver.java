package com.marekhakala.mynomadlifeapp.DataSource;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DataUpdatedReceiver extends ResultReceiver {
    protected Receiver receiver;

    public DataUpdatedReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null)
            receiver.onReceiveResult(resultCode, resultData);
    }
}