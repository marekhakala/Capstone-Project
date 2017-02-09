package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.marekhakala.mynomadlifeapp.R;

public class FilterSingleSelectionButtons extends LinearLayout {

    public interface OnButtonPressedListener {
        void onButtonSelected(int index);
        void onButtonSelectionReset();
    }

    private int mSelectedButtonId;

    private FilterSingleButton mFirstButton;
    private String mFirstButtonText;

    private FilterSingleButton mSecondButton;
    private String mSecondButtonText;

    private FilterSingleButton mThirdButton;
    private String mThirdButtonText;

    private ImageButton mClearButton;

    private OnButtonPressedListener mListener;

    public FilterSingleSelectionButtons(Context context) {
        super(context);
        init(context, null);
    }

    public FilterSingleSelectionButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FilterSingleSelectionButtons(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.filter_single_selection_buttons, this);

        this.mSelectedButtonId = -1;
        this.mFirstButton = (FilterSingleButton) findViewById(R.id.ssbp_first_button);
        this.mSecondButton = (FilterSingleButton) findViewById(R.id.ssbp_second_button);
        this.mThirdButton = (FilterSingleButton) findViewById(R.id.ssbp_third_button);
        this.mClearButton = (ImageButton) findViewById(R.id.ssbp_clear_button);
        initAttrs(context, attrs);

        this.mFirstButton.setOnClickListener(v -> clickButton(0));
        this.mSecondButton.setOnClickListener(v -> clickButton(1));
        this.mThirdButton.setOnClickListener(v -> clickButton(2));
        this.mClearButton.setOnClickListener(v -> clickButton(-1));
    }

    public void setOnButtonPressedListener(OnButtonPressedListener listener) {
        this.mListener = listener;
    }

    private void setCurrentButtonsColor() {
        this.mFirstButton.setEnabled(this.mSelectedButtonId == 0);
        this.mSecondButton.setEnabled(this.mSelectedButtonId == 1);
        this.mThirdButton.setEnabled(this.mSelectedButtonId == 2);
    }

    public void clickButton(int id) {
        this.setCurrentButtonId(id);
        setCurrentButtonsColor();

        if(mListener == null)
            return;

        if(id == -1)
            mListener.onButtonSelectionReset();
        else if(id >= 0 && id < 3)
            mListener.onButtonSelected(id);
    }

    public int getCurrentButtonId() {
        return this.mSelectedButtonId;
    }

    private void setCurrentButtonId(int id) {
        if(id < 0 || id > 2) {
            this.mSelectedButtonId = -1;

            if(mListener != null)
                mListener.onButtonSelectionReset();
        }

        this.mSelectedButtonId = id;
    }

    public void setCurrentButtonReset() {
        this.setCurrentButtonId(-1);
    }

    public String getFirstButtonText() {
        return this.mFirstButtonText;
    }

    public void setFirstButtonText(String value) {
        this.mFirstButtonText = value;
        this.mFirstButton.setText(this.mFirstButtonText);
    }

    public String getSecondButtonText() {
        return this.mFirstButtonText;
    }

    public void setSecondButtonText(String value) {
        this.mSecondButtonText = value;
        this.mSecondButton.setText(this.mSecondButtonText);
    }

    public String getThirdButtonText() {
        return this.mFirstButtonText;
    }

    public void setThirdButtonText(String value) {
        this.mThirdButtonText = value;
        this.mThirdButton.setText(this.mThirdButtonText);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterSingleSelectionButtons);
        this.mSelectedButtonId = typedArray.getInteger(R.styleable.FilterSingleSelectionButtons_ssbpSelectedButton, -1);
        setFirstButtonText(typedArray.getString(R.styleable.FilterSingleSelectionButtons_ssbpFirstButton));
        setSecondButtonText(typedArray.getString(R.styleable.FilterSingleSelectionButtons_ssbpSecondButton));
        setThirdButtonText(typedArray.getString(R.styleable.FilterSingleSelectionButtons_ssbpThirdButton));
        typedArray.recycle();
    }
}
