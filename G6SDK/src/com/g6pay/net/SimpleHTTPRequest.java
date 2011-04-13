package com.g6pay.net;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.g6pay.util.URLUtil;

import android.util.Log;

/**
 * Simple class used to make requests.. Override resultBody and requestFailed
 * if you want to do anything special..
 * 
 * For G6Pay SDK internal consumption
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class SimpleHTTPRequest implements SimpleHTTPListener {
    private String baseURL;
    private LinkedHashMap<String, String>params;
    private HashMap<String, String>nonSigParams;
    private String checksumKey;

    public SimpleHTTPRequest(String baseURL, LinkedHashMap<String, String> params,
            HashMap<String, String> nonSigParams, String checksumKey) {
        super();
        this.baseURL = baseURL;
        this.params = params;
        this.nonSigParams = nonSigParams;
        this.checksumKey = checksumKey;
    }
    
    public String getURLString() {
        return URLUtil.constructG6URL(baseURL, params, nonSigParams, checksumKey);
    }
    
    public void resultBody(String body) {
        Log.e("DEBUG resultBody", body);
    }

    public void requestFailed(int statusCode) {
        Log.e("DEBUG requestFailed", ""+statusCode);
    }
    
}

