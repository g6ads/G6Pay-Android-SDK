package com.g6pay.sdk;

import com.g6pay.constants.G6Params;

import java.util.HashMap;

import com.g6pay.listener.G6OfferListener;
import com.g6pay.listener.G6TransactionListener;
import com.g6pay.listener.G6UserAccountListener;
import com.g6pay.view.OffersWebView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Notes:
 * <activity android:name="com.g6pay.view.OffersWebView" android:configChanges="keyboardHidden|orientation" />
 * @author phsu
 *
 */
public class G6Pay implements G6AdvertiserIF {
    private static final String mLogStr = "G6Pay";

    private static G6Pay _theInstance = null;
    private String APP_ID = null;
    private String SECRET_KEY = null;
    private boolean setupCompleted = false;
    
    private HashMap<String, String> udids = null;
    
    public static G6Pay getG6PayInstance(Context ctx) {
        
        if (_theInstance == null) {
            _theInstance = new G6Pay(ctx);
        }
        
        return _theInstance;
    }
    
    public G6Pay(Context ctx) {
        // This will set the APPID, UDIDS
        
        PackageManager manager = ctx.getPackageManager();
        
        boolean failed = false;
        try {
            ApplicationInfo info = manager.getApplicationInfo(ctx.getPackageName(),
                    PackageManager.GET_META_DATA);
            
            Log.e("DEBUG", "packagename is " + ctx.getPackageName());
            if (info != null && info.metaData != null) 
            {
                // Get the app ID.
                this.APP_ID = info.metaData.getString(G6Params.ANDROID_MANIFEST_APP_ID);
                
                if (APP_ID == null || APP_ID.trim().equalsIgnoreCase(""))  {
                    Log.e(mLogStr, "Can't find " + G6Params.ANDROID_MANIFEST_APP_ID + 
                            ". Please add this value to the android manifest in the <application> tag:" +
                            "\n  <meta-data android:name=\"" + G6Params.ANDROID_MANIFEST_APP_ID +"\" android:value=\"YOUR_APP_ID_HERE\"/>");
                    failed = true;
                }

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
        
        setupCompleted = !failed;
    }
    
    /**
     * Launch a WebView with offers for the user to click
     */
    public void showOffers(Context ctx, String userId,
            G6OfferListener listener) {
        if (!setupCompleted) {
            Log.e(mLogStr, "SDK setup incomplete.. bailing.  Please fix previous errors");
            return;
        }
        
        try {
            Intent intent = new Intent(ctx, OffersWebView.class);
            intent.putExtra(G6Params.G6_PARAM_USER_ID, userId);
            intent.putExtra(G6Params.UDID_MAP, udids);
            
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Log.e(mLogStr, "Error launching offer webview.  Please add activity" +
                    " to the android manifest in the <application> tag:" +
                    "\n  <activity android:name=\"com.g6pay.view.OffersWebView\" android:configChanges=\"keyboardHidden|orientation\" />");
        }

    }
    
    public void creditUser(String transactionId, String userId, float amount,
            G6UserAccountListener listener) {
    }
    
    public void debitUser(String transactionId, String userId, float amount,
            G6UserAccountListener listener) {
    }
    
    public void getAllTransactions(String userId,
            G6TransactionListener listener) {
    }
    
    public void getUserBalance(String userId,
            G6UserAccountListener listener) {
    }

    public void installConfirm() {
        
    }
}
