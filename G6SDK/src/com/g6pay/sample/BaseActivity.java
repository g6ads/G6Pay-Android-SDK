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
        
        int i = 999;
        
        TextView tv = new TextView(this);
        tv.setText("Hello, G6ers");
        tv.setId(++i);
        r.addView(tv);
        
        Button offersButton = new Button(getApplicationContext());
        offersButton.setText("Show me some offers!");
        offersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showOfferWall();
            }
        }
        );
        offersButton.setId(++i);
        
        RelativeLayout.LayoutParams offersButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        offersButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(offersButton, offersButtonLp);
        
        Button confirmButton = new Button(getApplicationContext());
        confirmButton.setText("Sample confirm");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                installConfirm();
            }
        }
        );
        confirmButton.setId(++i);
        
        RelativeLayout.LayoutParams confirmButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        confirmButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(confirmButton, confirmButtonLp);

        Button creditButton = new Button(getApplicationContext());
        creditButton.setText("Sample credit user");
        creditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creditUser();
            }
        }
        );
        creditButton.setId(++i);
        
        RelativeLayout.LayoutParams creditButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        creditButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(creditButton, creditButtonLp);

        Button debitButton = new Button(getApplicationContext());
        debitButton.setText("Sample debit user");
        debitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                debitUser();
            }
        }
        );
        debitButton.setId(++i);
        
        RelativeLayout.LayoutParams debitButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        debitButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(debitButton, debitButtonLp);

        Button balanceButton = new Button(getApplicationContext());
        balanceButton.setText("Get user balance");
        balanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUserBalance();
            }
        }
        );
        balanceButton.setId(++i);
        
        RelativeLayout.LayoutParams balanceButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        balanceButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(balanceButton, balanceButtonLp);

        Button transactionsButton = new Button(getApplicationContext());
        transactionsButton.setText("Get user transactions");
        transactionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUserTransactions();
            }
        }
        );
        transactionsButton.setId(++i);
        
        RelativeLayout.LayoutParams transactionsButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        transactionsButtonLp.addRule(RelativeLayout.BELOW, i-1);
        r.addView(transactionsButton, transactionsButtonLp);

        // set the layout view
        setContentView(r);

        
    }
    
    public void showOfferWall() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.showOffers(this.getApplicationContext(), "fakeUserId", null);
        
    }
    public void installConfirm() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.installConfirm();
        
    }

    public void creditUser() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.creditUser(""+System.currentTimeMillis(), "fakeUserId", 0.5f, null);
        
    }

    public void debitUser() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.debitUser(""+System.currentTimeMillis(), "fakeUserId", 40.5f, null);
        
    }


    public void getUserBalance() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.getUserBalance("fakeUserId", null);
        
    }
    
    public void getUserTransactions() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        sdk.getAllTransactions("fakeUserId", null);
        
    }

}