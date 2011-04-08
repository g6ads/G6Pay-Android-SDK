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
        
        Button offersButton = new Button(getApplicationContext());
        offersButton.setText("Show me some offers!");
        offersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showOfferWall();
            }
        }
        );
        offersButton.setId(1000);
        
        RelativeLayout.LayoutParams offersButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        offersButtonLp.addRule(RelativeLayout.BELOW, 999);
        r.addView(offersButton, offersButtonLp);
        
        Button confirmButton = new Button(getApplicationContext());
        confirmButton.setText("Sample confirm");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                installConfirm();
            }
        }
        );
        confirmButton.setId(1001);
        
        RelativeLayout.LayoutParams confirmButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        confirmButtonLp.addRule(RelativeLayout.BELOW, 1000);
        r.addView(confirmButton, confirmButtonLp);
        
        setContentView(r);

        
    }
    
    public void showOfferWall() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.showOffers(this.getApplicationContext(), "", null);
        
    }
    public void installConfirm() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.installConfirm();
        
    }

}