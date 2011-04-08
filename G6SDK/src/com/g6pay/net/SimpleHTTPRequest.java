package com.g6pay.net;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;

/**
 * Simple class used to make requests.. Override resultBody and requestFailed
 * if you want to do anything special..
 * @author phsu
 *
 */
public class SimpleHTTPRequest implements SimpleHTTPListener {
    private String baseURL;
    private LinkedHashMap<String, String>params;
    private String checksumType;
    private String checksumKey;

    public SimpleHTTPRequest(String baseURL, LinkedHashMap<String, String> params,
            String checksumType, String checksumKey) {
        super();
        this.baseURL = baseURL;
        this.params = params;
        this.checksumType = checksumType;
        this.checksumKey = checksumKey;
    }
    
    public String getURLString() {
        
        StringBuffer b = new StringBuffer(baseURL);
        
        MessageDigest md = null;
        
        if (params != null && params.size() > 0) {
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    b.append("?");
                    first = false;
                } else {
                    b.append("&");
                }
                
                b.append(entry.getKey());
                b.append("=");
                b.append(URLEncoder.encode(entry.getValue()));
            }
            
            if (checksumType != null && checksumKey != null) {
                try {
                    md = MessageDigest.getInstance(checksumType);
                    if (md != null) {
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            md.update(entry.getValue().getBytes());
                        }
                        md.update(checksumKey.getBytes());
                    }
                    
                    String hex = getHex(md.digest());
                    
                    b.append("?signature=");
                    b.append(URLEncoder.encode(hex));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return b.toString();
    }
    
    public void resultBody(String body) {
        Log.e("DEBUG resultBody", body);
    }

    public void requestFailed(int statusCode) {
        Log.e("DEBUG requestFailed", ""+statusCode);
    }
    
    static final String HEXES = "0123456789abcdef";
    public static String getHex( byte [] raw ) {
        if ( raw == null ) {
          return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
          hex.append(HEXES.charAt((b & 0xF0) >> 4))
             .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}

