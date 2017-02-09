package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.marekhakala.mynomadlifeapp.R;

public class FavouriteActionButton extends FloatingActionButton {

    protected boolean mState = false;

    public FavouriteActionButton(Context context) {
        super(context);
        setState(false);
    }

    public FavouriteActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setState(false);
    }

    public FavouriteActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setState(false);
    }

    public boolean getState() {
        return mState;
    }

    public void setState(boolean enabled) {
        mState = enabled;

        if(enabled)
            setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star));
        else
            setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_border));
    }
}
