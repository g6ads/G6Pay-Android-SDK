package com.g6pay.sdk;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.g6pay.constants.G6Params;
import com.g6pay.sdk.G6Pay;
import com.g6pay.sdk.UDID;
import com.g6pay.util.URLUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The Activity that shows the current available offers.  This is launched
 * when the showOffers() method is called.
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class OffersWebView extends Activity {

    private ProgressBar progressBar;
    private TextView progressText;
    private RelativeLayout webviewLayout;
    private String signature;
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
        
        // Turn off title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        RelativeLayout r = new RelativeLayout(this);
        LayoutParams rp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        r.setLayoutParams(rp);
        r.setGravity(Gravity.CENTER);
        
        setContentView(r);
        
        progressBar = new ProgressBar(this);
        progressBar.setId(1000);
        RelativeLayout.LayoutParams progressLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        progressLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        r.addView(progressBar, progressLp);

        progressText = new TextView(this);
        progressText.setText("0%");
        progressText.setId(1001);
        RelativeLayout.LayoutParams progressTextLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        progressTextLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        progressTextLp.addRule(RelativeLayout.BELOW, 1000);
        r.addView(progressText, progressTextLp);

        TextView tv = new TextView(this);
        tv.setText("Loading offers.. ");
        tv.setId(1002);
        tv.setGravity(Gravity.CENTER);
        
        RelativeLayout.LayoutParams tvLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tvLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvLp.addRule(RelativeLayout.BELOW, 1001);
        r.addView(tv, tvLp);
        
        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToApp();
            }
        }
        );
        
        RelativeLayout.LayoutParams cancelButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        cancelButtonLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        cancelButtonLp.addRule(RelativeLayout.BELOW, 1002);
        cancelButton.setLayoutParams(cancelButtonLp);
        
        // Don't add the cancel button
        // r.addView(cancelButton);

        // Set up the webview.. this will be set as the content after it has
        // finished loading..
        WebView webview = new WebView(this);
        
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
              // Activities and WebViews measure progress with different scales.
              // The progress meter will automatically disappear when we reach 100%
                updateProgress(progress);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pageFinished();
            }
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                // market urls start with market://details?id=<package_name>
                if (url != null && url.startsWith("market:"))
                    didClickOffer(url);
                
                // Don't ever redirect, we should handle all cases
                return true;
            }
        });
        
        String baseURL = G6Params.G6_API_URL_OFFERWALL;
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        
        params.put(G6Params.G6_PARAM_APP_ID, extras.getString(G6Params.G6_PARAM_APP_ID));
        params.put(G6Params.G6_PARAM_USER_ID, extras.getString(G6Params.G6_PARAM_USER_ID));
        params.put(G6Params.G6_PARAM_TIMESTAMP, ""+System.currentTimeMillis()/1000);

        
        // nonSignParams
        HashMap<String, String> nonSigParams = new HashMap<String, String>();
        Map<String, String> udids = (Map<String, String>) extras.get(G6Params.UDID_MAP);
        nonSigParams.put(G6Params.G6_PARAM_PHONE_ID, udids.get(UDID.UDID_TELEPHONY_ID));
        nonSigParams.put(G6Params.G6_PARAM_PLATFORM, G6Params.PLATFORM_ID_ANDROID);
        
        String secretKey = extras.getString(G6Params.G6_PARAM_SECRET_KEY);
        
        // stash the signature for later
        signature = URLUtil.constructSignature(params, secretKey);
        
        String url = URLUtil.constructG6URL(baseURL, params, nonSigParams,
                secretKey);
        
        webview.loadUrl(url);

        // Set up the layout view with the button
        webviewLayout = new RelativeLayout(this);
        webviewLayout.setLayoutParams(rp);
        webviewLayout.addView(webview);
        
        Button closeButton = new Button(this);
        closeButton.setText("Return to app");
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToApp();
            }
        }
        );
        
        RelativeLayout.LayoutParams closeButtonLp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        closeButtonLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        closeButtonLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButton.setLayoutParams(closeButtonLp);
        
        // Don't add the close button
        // webviewLayout.addView(closeButton);
    }
    
    //////
    // protected methods
    protected void returnToApp() {
        G6Pay.getG6PayInstance(this.getApplicationContext()).didCloseOffers();
        this.finish();
    }
    
    protected void didClickOffer(String uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW); 
            intent.setData(Uri.parse(uri));
            startActivity(intent); 
            G6Pay.getG6PayInstance(this.getApplicationContext()).didSelectOffer(signature);
            finish();
        } catch (Exception ex) {
            Builder builder = new AlertDialog.Builder(this);
            
            builder.setTitle("Android Market not found");
            builder.setMessage("You need to install Android Market to complete this offer.");
            
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    dialog.dismiss();
                    returnToApp();
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
    }
    
    protected void pageFinished() {
    }
    
    protected void updateProgress(int progress) {
        progressBar.setProgress(progress);
        progressText.setText(""+progress+"%");
        
        // finished, switch it to the loaded web view
        if (progress >= 100) {
            setContentView(webviewLayout);
        }
    }
  

}
