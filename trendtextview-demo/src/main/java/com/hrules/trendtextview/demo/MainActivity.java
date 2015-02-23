package com.hrules.trendtextview.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import com.hrules.trendtextview.TrendTextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final TrendTextView trendTextView = (TrendTextView) findViewById(R.id.trendTextView);
        trendTextView.setTextColor(Color.WHITE);
        trendTextView.setBackgroundColor(getResources().getColor(R.color.material_green));
        trendTextView.animateText(getString(R.string.hello_world));

        trendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trendTextView.animateText(getString(R.string.hello_world));
            }
        });

    }
}
