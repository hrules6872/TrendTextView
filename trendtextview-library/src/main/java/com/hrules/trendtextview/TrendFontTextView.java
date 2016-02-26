package com.hrules.trendtextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class TrendFontTextView extends TextView {
  private static final String DEFAULT_FONT_TREND_BOLD = "fonts/RobotoCondensed-Bold.ttf";
  private static final String DEFAULT_TEXT_SHADOW_COLOR = "#33000000";
  private static final boolean DEFAULT_SHADOW_STATE = true;
  public static final float DEFAULT_SHADOWLAYER_RADIUS = 0.5f;
  public static final int DEFAULT_SHADOWLAYER_DX = 0;
  public static final float DEFAULT_SHADOWLAYER_DY = 2f;

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

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public TrendFontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    font = DEFAULT_FONT_TREND_BOLD;
    color = Color.parseColor(DEFAULT_TEXT_SHADOW_COLOR);
    hasShadow = DEFAULT_SHADOW_STATE;

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
    setShadowLayer(DEFAULT_SHADOWLAYER_RADIUS, DEFAULT_SHADOWLAYER_DX, DEFAULT_SHADOWLAYER_DY,
        color);
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
