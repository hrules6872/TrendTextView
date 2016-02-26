package com.hrules.trendtextview;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;

class TrendFontCache {
  private static Map<String, Typeface> mapFont = new HashMap<>();

  public static Typeface getFont(Context context, String fontName) {
    Typeface typeface = mapFont.get(fontName);

    if (typeface == null) {
      try {
        typeface = Typeface.createFromAsset(context.getAssets(), fontName);
      } catch (Exception e) {
        typeface = Typeface.DEFAULT;
      }
      mapFont.put(fontName, typeface);
    }
    return typeface;
  }
}
