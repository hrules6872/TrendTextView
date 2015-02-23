package com.hrules.trendtextview;


import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;

class TrendFontCache {
    private static Map<String, Typeface> fontMap = new HashMap<>();

    public static Typeface getFont(Context context, String fontName) {
        if (fontMap.containsKey(fontName)) {
            return fontMap.get(fontName);
        } else {
            Typeface typeface;
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e) {
                typeface = Typeface.DEFAULT;
            }
            fontMap.put(fontName, typeface);
            return typeface;
        }
    }
}
