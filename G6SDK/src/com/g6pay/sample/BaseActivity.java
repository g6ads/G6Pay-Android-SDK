package com.g6pay.sample;

import com.g6pay.sdk.G6Pay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        RelativeLayout r = new RelativeLayout(getApplicationContext());
        
        TextView tv = new TextView(this);
        tv.setText("Hello, G6ers");
        tv.setId(999);
        r.addView(tv);
        
        Button tapButton = new Button(getApplicationContext());
        tapButton.setText("Show me some offers!");
        tapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showOfferWall();
            }
        }
        );
        tapButton.setId(1000);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, 999);
        r.addView(tapButton, lp);
        
        setContentView(r);

//        setContentView(R.layout.main);
        
        
    }
    
    public void showOfferWall() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.showOffers(this.getApplicationContext(), "", null);
        
    }
}