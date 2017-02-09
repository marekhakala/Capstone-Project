package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.R;

public class ColorBarView extends PercentRelativeLayout {

    private int yellowColorFrom;
    private int greenColorFrom;

    private int redColor;
    private int yellowColor;
    private int greenColor;

    private String mText;
    private int percentage;

    private View percentBackground;
    private TextView textValue;

    public static int DEFAULT_PERCENTAGE_RED = 10;
    public static int DEFAULT_PERCENTAGE_YELLOW = 30;
    public static int DEFAULT_PERCENTAGE_GREEN = 70;

    public ColorBarView(Context context) {
        super(context);
        init(context, null);
    }

    public ColorBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ColorBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.color_bar_view, this);
        this.percentBackground = findViewById(R.id.color_bar_percent_background);
        this.textValue = (TextView) findViewById(R.id.color_bar_text_value);
        initAttrs(context, attrs);
    }

    public void setPercentage(int value) {
        if(value < 0 || value > 100)
            return;

        this.percentage = value;

        if(value >= this.greenColorFrom) {
            this.percentBackground.setBackgroundColor(this.greenColor);
        } else if(value >= this.yellowColorFrom) {
            this.percentBackground.setBackgroundColor(this.yellowColor);
        } else {
            this.percentBackground.setBackgroundColor(this.redColor);
        }

        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) this.percentBackground.getLayoutParams();
        if(params != null) {
            PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
            info.widthPercent = (value * 0.01f);
            this.percentBackground.requestLayout();
        }
    }

    public void setYellowPercentage(int value) {
        if(value >= 0 && value <= 100) {
            yellowColorFrom = value;
            setPercentage(this.percentage);
        }
    }

    public void setGreenPercentage(int value) {
        if(value >= 0 && value <= 100) {
            greenColorFrom = value;
            setPercentage(this.percentage);
        }
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setText(String value) {
        this.mText = value;
        this.textValue.setText(value);

    }

    public String getText() {
        return this.mText;
    }

    public void setTextSize(float value) {
        this.textValue.setTextSize(value);
    }

    public float getTextSize() {
        return this.textValue.getTextSize();
    }

    private int validatePercentageRange(int value) {
        if(value < 0 || value > 100)
            return 0;
        return value;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorBarView);

        this.yellowColorFrom = validatePercentageRange(typedArray.getInteger(R.styleable.ColorBarView_yellowColorFrom, DEFAULT_PERCENTAGE_YELLOW));
        this.greenColorFrom = validatePercentageRange(typedArray.getInteger(R.styleable.ColorBarView_greenColorFrom, DEFAULT_PERCENTAGE_GREEN));

        this.redColor = typedArray.getColor(R.styleable.ColorBarView_redColor, ContextCompat.getColor(context, R.color.color_bar_red_default));
        this.yellowColor = typedArray.getColor(R.styleable.ColorBarView_yellowColor, ContextCompat.getColor(context, R.color.color_bar_yellow_default));
        this.greenColor = typedArray.getColor(R.styleable.ColorBarView_greenColor, ContextCompat.getColor(context, R.color.color_bar_green_default));

        setText(typedArray.getString(R.styleable.ColorBarView_textValue));
        setTextSize(typedArray.getDimension(R.styleable.ColorBarView_textSize, getResources().getDimension(R.dimen.color_bar_text_size_default)));
        setPercentage(validatePercentageRange(typedArray.getInteger(R.styleable.ColorBarView_percentage, DEFAULT_PERCENTAGE_RED)));
        typedArray.recycle();
    }
}