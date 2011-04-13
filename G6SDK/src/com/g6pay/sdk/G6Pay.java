package com.g6pay.sdk;

import com.g6pay.constants.G6Params;
import com.g6pay.dto.OfferDTO;
import com.g6pay.dto.TransactionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.g6pay.listener.G6OfferListener;
import com.g6pay.listener.G6TransactionListener;
import com.g6pay.listener.G6UserAccountListener;
import com.g6pay.net.SimpleAsyncHTTPTask;
import com.g6pay.net.SimpleHTTPRequest;
import com.g6pay.util.ResponseParser;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * The G6 Pay SDK.  This class provides all the methods for interacting
 * with a user's account, etc.
 * 
 * <pre>
 * <b>Sample install confirm:</b>
 * 
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        sdk.installConfirm();

 *
 * <b>Sample offerwall showing:</b>
 * 
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6OfferListener listener = new G6OfferListener() {
            public void offerWasCompleted(OfferDTO offer) {
                // handle offer completion (implement this yourself)
                offerCompleted(offer);
            }
        };
        
        sdk.showOffers(this.getApplicationContext(), "USERIDHERE", listener);
 *
 * 
 * Notes:
 * <activity android:name="com.g6pay.sdk.OffersWebView" android:configChanges="keyboardHidden|orientation" />
 * 
 * </pre>
 * 
 * See appropriate Listener classes for how to receive notifications of events.
 * 
 * @see com.g6pay.listener.G6OfferListener
 * @see com.g6pay.listener.G6TransactionListener
 * @see com.g6pay.listener.G6UserAccountListener
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class G6Pay implements G6AdvertiserIF, G6PublisherIF {
    private static final String mLogStr = "G6Pay";

    private static G6Pay _theInstance = null;
    private String APP_ID = null;
    private String SECRET_KEY = null;
    private boolean setupCompleted = false;
    
    private G6OfferListener offerListener;
    
    private HashMap<String, String> udids = null;
    
    /**
     * Get the singleton instance of the G6Pay SDK.
     * @param ctx The application context of this application
     * @return The G6Pay instance
     */
    public static G6Pay getG6PayInstance(Context ctx) {
        
        if (_theInstance == null) {
            _theInstance = new G6Pay(ctx);
        }
        
        return _theInstance;
    }
    
    private G6Pay(Context ctx) {
        // This will set the APPID, UDIDS
        
        PackageManager manager = ctx.getPackageManager();
        
        boolean failed = false;
        try {
            // Initialize all the parameters from the package
            ApplicationInfo info = manager.getApplicationInfo(ctx.getPackageName(),
                    PackageManager.GET_META_DATA);
            
            // Log.d(mLogStr, "packagename is " + ctx.getPackageName());
            if (info != null && info.metaData != null) 
            {
                // Get the APP ID and validate it's not empty or missing
                int appId = info.metaData.getInt(G6Params.ANDROID_MANIFEST_APP_ID);
                if (appId > 0) {
                    this.APP_ID = ""+appId;
                } else {
                    this.APP_ID = info.metaData.getString(G6Params.ANDROID_MANIFEST_APP_ID);
                }
                
                if (APP_ID == null || APP_ID.trim().equalsIgnoreCase(""))  {
                    Log.e(mLogStr, "Can't find " + G6Params.ANDROID_MANIFEST_APP_ID + 
                            ". Please add this value to the android manifest in the <application> tag:" +
                            "\n  <meta-data android:name=\"" + G6Params.ANDROID_MANIFEST_APP_ID +"\" android:value=\"YOUR_APP_ID_HERE\"/>");
                    failed = true;
                }

                // Get the SECRET KEY and validate it's not empty or missing
                this.SECRET_KEY = info.metaData.getString(G6Params.ANDROID_MANIFEST_SECRET_KEY);
                
                if (SECRET_KEY == null || SECRET_KEY.trim().equalsIgnoreCase(""))  {
                    Log.e(mLogStr, "Can't find " + G6Params.ANDROID_MANIFEST_SECRET_KEY + 
                            ". Please add this value to the android manifest in the <application> tag:" +
                            "\n  <meta-data android:name=\"" + G6Params.ANDROID_MANIFEST_SECRET_KEY +"\" android:value=\"YOUR_SECRET_KEY_HERE\"/>");
                    failed = true;
                }
            } else {
                Log.e(mLogStr, "Can't find " + G6Params.ANDROID_MANIFEST_APP_ID +
                        " and " + G6Params.ANDROID_MANIFEST_SECRET_KEY + 
                        ". Please add these value to the android manifest in the <application> tag:\n" +
                        "\n  <meta-data android:name=\"" + G6Params.ANDROID_MANIFEST_APP_ID +"\" android:value=\"YOUR_APP_ID_HERE\"/>" +
                        "\n  <meta-data android:name=\"" + G6Params.ANDROID_MANIFEST_SECRET_KEY +"\" android:value=\"YOUR_SECRET_KEY_HERE\"/>");
                failed = true;
            }
        } catch (NameNotFoundException e) {
            // Can't find android manifest!
            Log.e(mLogStr, "Can't find android manifest file, G6 SDK not loaded");
            failed = true;
        }

        try {
            this.udids = UDID.getDeviceId(ctx);
        } catch (SecurityException e) {
            Log.e(mLogStr, "Error reading state from device.  Please add proper" +
            		" permission to the android manifest in the <manifest> tag:" +
            		"\n  <uses-permission android:name=\"android.permission.READ_PHONE_STATE\" />");
            failed = true;
        }
        
        if (ctx.getPackageManager().checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, ctx.getPackageName()) != 0) {
            Log.e(mLogStr, "Network state unavailable.  Please add proper" +
                    " permission to the android manifest in the <manifest> tag:" +
                    "\n  <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />");
            failed = true;
        }
        
        if (ctx.getPackageManager().checkPermission(Manifest.permission.INTERNET, ctx.getPackageName()) != 0) {
            Log.e(mLogStr, "Internet unavailable.  Please add proper" +
                    " permission to the android manifest in the <manifest> tag:" +
                    "\n  <uses-permission android:name=\"android.permission.INTERNET\" />");
            failed = true;
        }
        setupCompleted = !failed;
    }
    
    /**
     * Show a new WebView activity that displays all the available offers for
     * the user to complete.
     * @param ctx The application context
     * @param userId The unique user Id of this user in your application
     * @param listener The optional listener to receive event notifications
     */
    public void showOffers(Context ctx, String userId,
            G6OfferListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        
        try {
            Intent intent = new Intent(ctx, OffersWebView.class);
            intent.putExtra(G6Params.G6_PARAM_APP_ID, APP_ID);
            intent.putExtra(G6Params.G6_PARAM_USER_ID, userId);
            intent.putExtra(G6Params.UDID_MAP, udids);
            intent.putExtra(G6Params.G6_PARAM_SECRET_KEY, SECRET_KEY);
            
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            
            offerListener = listener;
        } catch (ActivityNotFoundException ex) {
            Log.e(mLogStr, "Error launching offer webview.  Please add activity" +
                    " to the android manifest in the <application> tag:" +
                    "\n  <activity android:name=\"com.g6pay.sdk.OffersWebView\" android:configChanges=\"keyboardHidden|orientation\" />");
        }

    }
    
    private void retryIsCompleted(final SimpleHTTPRequest request) {
        // Log.d(mLogStr, "retrying iscompleted");
        // Wait 5 seconds then poll again..
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            public void run() {
                new SimpleAsyncHTTPTask().execute(request);        
            }

        };
        handler.postDelayed(runnable, 5000);
    }
    
    /**
     * Private because it's automatically called when offer wall offer is selected
     * @param signature
     * @param listener
     */
    private void isCompleted(String signature, final G6OfferListener listener) {
        
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_SIGNATURE, signature);

        HashMap<String, String> nonSigParams = new HashMap<String, String>();
        nonSigParams.put(G6Params.G6_PARAM_PLATFORM, G6Params.PLATFORM_ID_ANDROID);
        
        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_ISCOMPLETED, params, nonSigParams,
                this.SECRET_KEY) {
          
            public void resultBody(String body) {
                // Log.d(mLogStr, body);
                if (listener == null) return;
                
                if (body != null) {
                    OfferDTO offer = ResponseParser.parseOffer(body);
                    if (offer != null) {
                        listener.offerWasCompleted(offer);
                        return;
                    }
                }
                retryIsCompleted(this);
            }

            public void requestFailed(int statusCode) {
                // Log.d(mLogStr, ""+statusCode);
                if (listener == null) return;
                
                retryIsCompleted(this);
            }
        };
        new SimpleAsyncHTTPTask().execute(request);        
    }
    
    /**
     * Credit a user's virtual currency account at G6.
     * @param transactionId The unique transaction Id that identifies this transaction
     * @param amount The amount to credit to the user
     * @param userId The unique user Id of this user in your application
     * @param listener The optional listener to receive event notifications
     */
    public void creditUser(final String transactionId, final String userId, final float amount,
            final G6UserAccountListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_AMOUNT, ""+amount);
        params.put(G6Params.G6_PARAM_CREDIT_TRANSACTION_ID, transactionId);
        params.put(G6Params.G6_PARAM_APP_ID, this.APP_ID);
        params.put(G6Params.G6_PARAM_USER_ID, userId);
        params.put(G6Params.G6_PARAM_TIMESTAMP, ""+System.currentTimeMillis()/1000);

        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_CREDIT, params, null,
                this.SECRET_KEY) {
          
            public void resultBody(String body) {
                // Log.d(mLogStr, body);
                if (listener == null) return;
                
                if (body != null && body.trim().equalsIgnoreCase("success")) {
                    listener.creditUserSuccess(userId, transactionId, amount);
                    return;
                }
                
                listener.creditUserFailure(userId, transactionId, amount);
            }

            public void requestFailed(int statusCode) {
                // Log.d(mLogStr, ""+statusCode);
                if (listener == null) return;

                listener.creditUserFailure(userId, transactionId, amount);
            }
        };
        new SimpleAsyncHTTPTask().execute(request);

    }
    
    /**
     * Debits a user's virtual currency account at G6.
     * @param transactionId The unique transaction Id that identifies this transaction
     * @param amount The amount to debit to the user
     * @param userId The unique user Id of this user in your application
     * @param listener The optional listener to receive event notifications
     */
    public void debitUser(final String transactionId, final String userId, final float amount,
            final G6UserAccountListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_AMOUNT, ""+amount);
        params.put(G6Params.G6_PARAM_DEBIT_TRANSACTION_ID, transactionId);
        params.put(G6Params.G6_PARAM_APP_ID, this.APP_ID);
        params.put(G6Params.G6_PARAM_USER_ID, userId);
        params.put(G6Params.G6_PARAM_TIMESTAMP, ""+System.currentTimeMillis()/1000);

        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_DEBIT, params, null,
                this.SECRET_KEY) {
          
            public void resultBody(String body) {
                // Log.d(mLogStr, body);
                if (listener == null) return;
                
                if (body != null && body.trim().equalsIgnoreCase("success")) {
                    listener.debitUserSuccess(userId, transactionId, amount);
                    return;
                }
                
                listener.debitUserFailure(userId, transactionId, amount);
            }

            public void requestFailed(int statusCode) {
                // Log.d(mLogStr, ""+statusCode);
                if (listener == null) return;

                listener.debitUserFailure(userId, transactionId, amount);
            }
        };
        new SimpleAsyncHTTPTask().execute(request);
    }
    
    /**
     * Get the transactions that have been completed for this user at G6.
     * @param userId The unique user Id of this user in your application
     * @param listener The optional listener to receive event notifications
     */
    public void getAllTransactions(final String userId,
            final G6TransactionListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_APP_ID, this.APP_ID);
        params.put(G6Params.G6_PARAM_USER_ID, userId);
        params.put(G6Params.G6_PARAM_TIMESTAMP, ""+System.currentTimeMillis()/1000);

        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_TRANSACTIONS, params, null,
                this.SECRET_KEY) {
          
            public void resultBody(String body) {
                // Log.d(mLogStr, body);
                
                if (listener == null) return;
                
                if (body != null) {
                    ArrayList<TransactionDTO> transactions = ResponseParser.parseTransactions(body);
                    if (transactions != null) {
                        listener.getAllTransactionsSuccess(userId, transactions);
                        return;
                    }
                }
                listener.getAllTransactionsFail(userId);
            }

            public void requestFailed(int statusCode) {
                // Log.d(mLogStr, ""+statusCode);
                if (listener == null) return;
                
                listener.getAllTransactionsFail(userId);
            }
        };
        new SimpleAsyncHTTPTask().execute(request);
    }
    
    /**
     * Get the current user's balance at G6.
     * @param userId The unique user Id of this user in your application
     * @param listener The optional listener to receive event notifications
     */
    public void getUserBalance(final String userId,
            final G6UserAccountListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_APP_ID, this.APP_ID);
        params.put(G6Params.G6_PARAM_USER_ID, userId);
        params.put(G6Params.G6_PARAM_TIMESTAMP, ""+System.currentTimeMillis()/1000);

        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_BALANCE, params, null,
                this.SECRET_KEY) {
          
            public void resultBody(String body) {
                // Log.d(mLogStr, body);
                if (listener == null) return;
                
                if (body != null) {
                    try {
                        float balance = ResponseParser.balanceFromResponse(body);
                        listener.getUserBalanceSuccess(userId, balance);
                        return;
                    } catch (Exception ex) {
                    }
                }
                listener.getUserBalanceFail(userId);
            }

            public void requestFailed(int statusCode) {
                // Log.d(mLogStr, ""+statusCode);
                if (listener == null) return;

                listener.getUserBalanceFail(userId);
            }
        };
        new SimpleAsyncHTTPTask().execute(request);
    }

    /**
     * Confirm that this app was installed.  This is required for using
     * G6 Pay Per Install
     */
    public void installConfirm() {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put(G6Params.G6_PARAM_APP_ID, this.APP_ID);
        params.put(G6Params.G6_PARAM_PHONE_ID, udids.get(UDID.UDID_TELEPHONY_ID));
        
        HashMap<String, String> nonSigParams = new HashMap<String, String>();
        nonSigParams.put(G6Params.G6_PARAM_PLATFORM, G6Params.PLATFORM_ID_ANDROID);

        // Call setup complete.. No need for callback since this should get 
        // called every time the app starts up
        SimpleHTTPRequest request = new SimpleHTTPRequest(
                G6Params.G6_API_URL_INSTALLCONFIRM, params, nonSigParams,
                this.SECRET_KEY);
        new SimpleAsyncHTTPTask().execute(request);
    }
    

    // Callbacks from offers page
    protected void didCloseOffers() {
        // Currently nothing to note here..
        offerListener = null;
    }
    
    

    protected void didSelectOffer(String signature) {
        // Don't bother tracking if there's no one to notify..
        if (offerListener == null) return;
        
        // otherwise, start polling by calling isCompleted
        this.isCompleted(signature, offerListener);
        
    }

}
