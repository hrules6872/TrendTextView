package com.hrules.trendtextview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class TrendFontTextView extends TextView {
    private static String TAG = "TrendFontTextView";

    private static final String FONT_TREND_BOLD = "fonts/RobotoCondensed-Bold.ttf";
    private static final String TEXT_SHADOW_COLOR = "#33000000";
    private String font;
    private int color;
    private boolean hasShadow;

    public TrendFontTextView(Context context) {
        this(context, null);
    }

    public TrendFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrendFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        font = FONT_TREND_BOLD;
        color = Color.parseColor(TEXT_SHADOW_COLOR);
        hasShadow = true;

        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

        setFont(font);
        showShadow(hasShadow);
    }

    public void setFont(String font) {
        this.font = font;
        setTypeface(TrendFontCache.getFont(getContext(), font));
    }

    public int getShadowColor() {
        return color;
    }

    public void setShadowColor(int color) {
        this.color = color;
        hasShadow = true;
        setShadowLayer(0.5f, 0, 2f, color);
    }

    public void showShadow(boolean showShadow) {
        hasShadow = showShadow;

        if (showShadow) {
            setShadowColor(color);
        } else {
            setShadowLayer(0f, 0, 0f, Color.TRANSPARENT);
        }
    }

    public boolean hasShadow() {
        return hasShadow;
    }
}
