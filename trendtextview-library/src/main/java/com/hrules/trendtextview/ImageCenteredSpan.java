package com.hrules.trendtextview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.style.ImageSpan;

class ImageCenteredSpan extends ImageSpan {
    public ImageCenteredSpan(Drawable d) {
        super(d);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        int transY = (int) ((bottom - TrendTextView.lineSpacingExtra) - (b.getBounds().bottom * TrendTextView.lineSpacingMultiplier));
        transY -= isLollipop() ? paint.getFontMetricsInt().descent : paint.getFontMetricsInt().descent / 2;

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    private boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
