package com.g6pay.view;

import com.g6pay.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class OffersWebView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
        
        // Turn off status bars
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // TODO: R is not available, may need to load the package name
        // from the app so we know where to load the layout..
        setContentView(R.layout.main);
        TextView tv = new TextView(this);
        tv.setText("Offerwall coming soon!");
        setContentView(tv);
        
        
    }
    

}
