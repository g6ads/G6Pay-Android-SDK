package com.g6pay.sample;

import java.util.ArrayList;

import com.g6pay.dto.OfferDTO;
import com.g6pay.dto.TransactionDTO;
import com.g6pay.listener.G6OfferListener;
import com.g6pay.listener.G6TransactionListener;
import com.g6pay.listener.G6UserAccountListener;
import com.g6pay.sdk.G6Pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Sample Activity that utilizes all the methods of the G6Pay SDK.
 * 
 * @see com.g6pay.sdk.G6Pay
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class G6SampleActivity extends Activity {
    
    private EditText creditValue;
    private EditText debitValue;
    private TextView balanceValue;
    
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

        // credit button
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

        // credit value
        creditValue = new EditText(getApplicationContext());
        creditValue.setText("1.00");
        creditValue.setId(i+1000);
        
        RelativeLayout.LayoutParams creditValueLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        creditValueLp.addRule(RelativeLayout.RIGHT_OF, i);
        creditValueLp.addRule(RelativeLayout.ALIGN_BASELINE, i);
        r.addView(creditValue, creditValueLp);

        // debit button
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

        // debit value
        debitValue = new EditText(getApplicationContext());
        debitValue.setText("1.00");
        debitValue.setId(i+1000);
        
        RelativeLayout.LayoutParams debitValueLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        debitValueLp.addRule(RelativeLayout.RIGHT_OF, i);
        debitValueLp.addRule(RelativeLayout.ALIGN_BASELINE, i);
        r.addView(debitValue, debitValueLp);
        
        // balance button
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

        balanceValue = new TextView(getApplicationContext());
        balanceValue.setText("???");
        balanceValue.setId(i+1000);
        
        RelativeLayout.LayoutParams balanceValueLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        balanceValueLp.addRule(RelativeLayout.RIGHT_OF, i);
        balanceValueLp.addRule(RelativeLayout.ALIGN_BASELINE, i);
        r.addView(balanceValue, balanceValueLp);

        // get transactions button
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

        G6OfferListener listener = new G6OfferListener() {
            public void offerWasCompleted(OfferDTO offer) {
                showAlert("Offer completed", offer.toString());
            }
        };
        
        sdk.showOffers(this.getApplicationContext(), "fakeUserId", listener);

    }

    public void installConfirm() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        sdk.installConfirm();

    }

    private void showAlert(String title, String message) {
        Builder builder = new AlertDialog.Builder(this);
        
        builder.setTitle(title);
        builder.setMessage(message);
        
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        }).create();
        
        try
        {
            builder.show();
        }
        catch(Exception e)
        {
        }    
    }
    
    public void callFailed() {
        showAlert("Call Failed", "There was an error performing this call.");
    }
    
    public void creditUser() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());
        
        G6UserAccountListener listener = new G6UserAccountListener() {
            public void creditUserSuccess(String userId, String transactionId, float amount) {
                showAlert("Credit User Success", "The call was successfully performed");
            }
            public void creditUserFailure(String userId, String transactionId, float amount) {
                callFailed();
            }
        };
        
        float amount = 0;
        try {
            amount = Float.parseFloat(creditValue.getText().toString());
        } catch (Exception ex) {
            showAlert("Invalid data", "Please enter a valid value");
            return;
        }
        sdk.creditUser("" + System.currentTimeMillis(), "fakeUserId", amount,
                listener);
        

    }

    public void debitUser() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6UserAccountListener listener = new G6UserAccountListener() {
            public void debitUserSuccess(String userId, String transactionId, float amount) {
                showAlert("Debit User Success", "The call was successfully performed");
            }
            public void debitUserFailure(String userId, String transactionId, float amount) {
                callFailed();
            }
        };
        
        float amount = 0;
        try {
            amount = Float.parseFloat(debitValue.getText().toString());
        } catch (Exception ex) {
            showAlert("Invalid data", "Please enter a valid value");
            return;
        }

        sdk.debitUser("" + System.currentTimeMillis(), "fakeUserId", amount,
                listener);

    }

    public void getUserBalance() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6UserAccountListener listener = new G6UserAccountListener() {
            public void getUserBalanceSuccess(String userId, float balance) {
                showAlert("Get User Balance Success", "Current user balance is "+balance);
                balanceValue.setText(""+balance);
            }
            public void getUserBalanceFail(String userId) {
                callFailed();
            }
        };
        
        sdk.getUserBalance("fakeUserId", listener);

    }

    public void getUserTransactions() {
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6TransactionListener listener = new G6TransactionListener() {
            public void getAllTransactionsSuccess(String userId, ArrayList<TransactionDTO> transactions) {
                
                if (transactions == null || transactions.size() == 0) {
                    showAlert("Get User Transactions Success", 
                    "But no transactions have been made");
                }
                else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(transactions.size() + " transactions total.\n");
                    for (TransactionDTO transaction : transactions) {
                        sb.append("\n"+transaction.toString());
                    }
                    showAlert("Get User Transactions Success", 
                            sb.toString());
                }
            }
            
            public void getAllTransactionsFail(String userId) {
                callFailed();
            }
        };
        
        sdk.getAllTransactions("fakeUserId", listener);

    }

}