package com.g6pay.sdk;

import com.g6pay.constants.G6Params;

import java.util.HashMap;

import com.g6pay.listener.G6OfferListener;
import com.g6pay.listener.G6TransactionListener;
import com.g6pay.listener.G6UserAccountListener;
import com.g6pay.view.OffersWebView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Notes:
 * <activity android:name="com.g6pay.view.OffersWebView" android:configChanges="keyboardHidden|orientation" />
 * @author phsu
 *
 */
public class G6Pay implements G6AdvertiserIF {

    private static G6Pay _theInstance = null;
    
    private String APP_ID = null;
    private String SECRET_KEY = null;
    
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
        
        try {
            ApplicationInfo info = manager.getApplicationInfo(ctx.getPackageName(),
                    PackageManager.GET_META_DATA);
            
            if (info != null && info.metaData != null) 
            {
                // Get the app ID.
                this.APP_ID = info.metaData.getString(G6Params.ANDROID_MANIFEST_APP_ID).trim();
                
                if (APP_ID == null || APP_ID.equalsIgnoreCase(""))  {
                    // TODO: handle this error scenario
                }

                this.SECRET_KEY = info.metaData.getString(G6Params.ANDROID_MANIFEST_SECRET_KEY).trim();
                
                if (SECRET_KEY == null || SECRET_KEY.equalsIgnoreCase(""))  {
                    // Not an error unless we
                    this.SECRET_KEY = null;
                }
            }
        } catch (NameNotFoundException e) {
            // Can't find android manifest!
            e.printStackTrace();
        }

        try {
            this.udids = UDID.getDeviceId(ctx);
        } catch (java.lang.SecurityException e) {
            // TODO: handle the read state not set..
            e.printStackTrace();
        }
    }
    
    /**
     * Launch a WebView with offers for the user to click
     */
    public void showOffers(Context ctx, String userId,
            G6OfferListener listener) {
        Intent intent = new Intent(ctx, com.g6pay.view.OffersWebView.class);
        intent.putExtra(G6Params.G6_PARAM_USER_ID, userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

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
