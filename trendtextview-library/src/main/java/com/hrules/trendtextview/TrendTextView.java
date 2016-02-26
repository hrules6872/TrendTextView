package com.hrules.trendtextview;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TrendTextView extends TrendFontTextView {
  private static final int DEFAULT_DELAY_MILLIS = 125;
  private static final int DEFAULT_DELAY_BETWEEN_WORDS_MILLIS = 250;
  private static final int DEFAULT_DELAY_CURSOR_MILLIS = 500;
  private static final float DEFAULT_LETTER_SPACING = -3f;
  private static final float DEFAULT_LINE_SPACING_MULTIPLIER = 0.85f;
  private static final float DEFAULT_LINE_SPACING_EXTRA = 0.0f;

  private ShapeDrawable cursorDrawable;
  private CharSequence textToAnimate;
  private int position;
  private long delay;
  private long delayBetweenWords;
  private long cursorBlinkRate;
  private boolean cursorBlink;
  private boolean running;
  private TrendTextViewListener listener;
  private float letterSpacing;
  protected static float lineSpacingMultiplier;
  protected static float lineSpacingExtra;
  private CharSequence originalText;

  private final Handler handlerText = new Handler();
  private final Handler handlerCursor = new Handler();

  public TrendTextView(Context context) {
    this(context, null, 0);
  }

  public TrendTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TrendTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    cursorDrawable = new ShapeDrawable(new RectShape());

    delay = DEFAULT_DELAY_MILLIS;
    delayBetweenWords = DEFAULT_DELAY_BETWEEN_WORDS_MILLIS;
    cursorBlinkRate = DEFAULT_DELAY_CURSOR_MILLIS;

    letterSpacing = DEFAULT_LETTER_SPACING;
    lineSpacingExtra = DEFAULT_LINE_SPACING_EXTRA;
    lineSpacingMultiplier = DEFAULT_LINE_SPACING_MULTIPLIER;

    originalText = "";
    textToAnimate = "";

    super.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineSpacingExtra,
        context.getResources().getDisplayMetrics()), lineSpacingMultiplier);
    setCursorSize();
  }

  @Override public void setLineSpacing(float add, float mult) {
    super.setLineSpacing(add, mult);
    lineSpacingExtra = add;
    lineSpacingMultiplier = mult;
    setCursorSize();
  }

  private void setCursorSize() {
    cursorDrawable.setBounds(0, 0, 2, (int) getTextSize());
  }

  public boolean isRunning() {
    return running;
  }

  public void setOnTrendTextFinishListener(TrendTextViewListener listener) {
    this.listener = listener;
  }

  public void animateText(CharSequence text) {
    if (text == null || text.toString().trim().equals("")) {
      return;
    }

    textToAnimate = text;
    reset();

    running = true;
    handlerText.postDelayed(characterAdder, delay);
  }

  private SpannableString getSpannableStringWithCursor(SpannableString text) {
    if (text == null) {
      return SpannableString.valueOf("");
    }
    if (text.toString().trim().equals("")) {
      return text;
    }

    ImageSpan span = new ImageCenteredSpan(cursorDrawable);
    SpannableString spannableString;
    spannableString = new SpannableString(text);
    spannableString.setSpan(span, spannableString.length() - 1, spannableString.length(),
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    return spannableString;
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long millis) {
    delay = millis;
  }

  public long getDelayBetweenWords() {
    return delayBetweenWords;
  }

  public void setDelayBetweenWords(long millis) {
    delayBetweenWords = millis;
  }

  public long getCursorBlinkRate() {
    return cursorBlinkRate;
  }

  public void setCursorBlinkRate(long millis) {
    cursorBlinkRate = millis;
  }

  private void reset() {
    running = false;

    setText("");
    position = 0;
    cursorBlink = true;

    handlerText.removeCallbacks(characterAdder);
    handlerCursor.removeCallbacks(cursorBlinker);
  }

  public void stop() {
    reset();
  }

  public int getCursorColor() {
    return cursorDrawable.getPaint().getColor();
  }

  public void setCursorColor(int color) {
    cursorDrawable.getPaint().setColor(color);
  }

  @Override public void setTextColor(int color) {
    super.setTextColor(color);
    this.setCursorColor(color);
  }

  private final Runnable cursorBlinker = new Runnable() {
    @Override public void run() {
      if (cursorBlink) {
        setText(textToAnimate + " ");
      } else {
        setText(textToAnimate);
      }

      cursorBlink = !cursorBlink;
      handlerCursor.postDelayed(cursorBlinker, cursorBlinkRate);
    }
  };
  private final Runnable characterAdder = new Runnable() {
    @Override public void run() {
      setText(textToAnimate.subSequence(0, position++).toString() + " ");

      if (done()) {
        long extraDelay = 0;
        if (Character.isWhitespace(textToAnimate.charAt(index()))) {
          extraDelay = delayBetweenWords;
        }

        handlerText.postDelayed(characterAdder, delay + extraDelay);
      } else {
        running = false;
        if (listener != null) {
          listener.onFinished();
        }

        handlerCursor.post(cursorBlinker);
      }
    }

    private int index() {
      return position > 1 ? position - 2 : position - 1;
    }

    private boolean done() {
      return position < textToAnimate.length();
    }
  };

  @Override public float getLetterSpacing() {
    return letterSpacing;
  }

  @Override public void setLetterSpacing(float letterSpacing) {
    this.letterSpacing = letterSpacing;
    applyLetterSpacing();
  }

  @Override public CharSequence getText() {
    return originalText;
  }

  @Override public void setText(CharSequence text, BufferType type) {
    originalText = text;
    applyLetterSpacing();
  }

  @Override public void setTextSize(float size) {
    super.setTextSize(size);
    setCursorSize();
  }

  @Override public void setTextSize(int unit, float size) {
    super.setTextSize(unit, size);
    setCursorSize();
  }

  private void applyLetterSpacing() {
    if (originalText == null) {
      return;
    }

    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < originalText.length(); i++) {
      stringBuilder.append(originalText.charAt(i));

      if (i + 1 < originalText.length()) {
        stringBuilder.append("\u00A0");
      }
    }

    SpannableString spannableString = new SpannableString(stringBuilder.toString());
    if (cursorBlink) {
      spannableString = getSpannableStringWithCursor(SpannableString.valueOf(spannableString));
    }

    if (stringBuilder.toString().length() > 1) {
      for (int i = 1; i < stringBuilder.toString().length(); i += 2) {
        spannableString.setSpan(new ScaleXSpan((letterSpacing + 1) / 10), i, i + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    super.setText(spannableString, BufferType.SPANNABLE);
  }
}