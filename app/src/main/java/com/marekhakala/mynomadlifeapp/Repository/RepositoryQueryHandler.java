package com.marekhakala.mynomadlifeapp.Repository;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import java.lang.ref.WeakReference;

public class RepositoryQueryHandler extends AsyncQueryHandler {
    private WeakReference<AsyncQueryListener> mListener;

    public interface AsyncQueryListener {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
    }

    public RepositoryQueryHandler(ContentResolver contentResolver, AsyncQueryListener listener) {
        super(contentResolver);
        mListener = new WeakReference<>(listener);
    }

    public RepositoryQueryHandler(ContentResolver contentResolver) {
        super(contentResolver);
    }

    public void setQueryListener(AsyncQueryListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        final AsyncQueryListener listener = mListener.get();

        if (listener != null)
            listener.onQueryComplete(token, cookie, cursor);
        else if (cursor != null)
            cursor.close();
    }
}