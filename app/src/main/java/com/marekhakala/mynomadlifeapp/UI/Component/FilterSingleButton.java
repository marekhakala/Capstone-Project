package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import com.marekhakala.mynomadlifeapp.R;

public class FilterSingleButton extends Button {

    private boolean mButtonState;

    public FilterSingleButton(Context context) {
        super(context);
        init(context, null);
    }

    public FilterSingleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FilterSingleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setEnabled(false);
        this.setOnClickListener(v -> setEnabled(!mButtonState));
    }

    public boolean getEnabled() {
        return this.mButtonState;
    }

    public void setEnabled(boolean state) {
        this.mButtonState = state;

        if(this.mButtonState) {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_primary));
            setTextColor(ContextCompat.getColor(getContext(), R.color.theme_light));
        } else {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_light));
            setTextColor(ContextCompat.getColor(getContext(), R.color.theme_primary));
        }
    }
}
