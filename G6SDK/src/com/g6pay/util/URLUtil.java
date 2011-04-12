package com.g6pay.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLUtil {

    private final static String checksumType = "SHA-256";
    
    public static String constructG6URL(String baseURL, 
            LinkedHashMap<String, String> sigParams, Map<String, String> nonSigParams,
            String checksumKey) {
        StringBuffer b = new StringBuffer(baseURL);
        
        boolean first = true;
        if (sigParams != null && sigParams.size() > 0) {
            for (Map.Entry<String, String> entry : sigParams.entrySet()) {
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
                String sig = constructSignature(sigParams, checksumKey);
                if (sig != null) {
                    b.append("&signature=");
                    b.append(URLEncoder.encode(sig));
                }
            }
        }
        if (nonSigParams != null && nonSigParams.size() > 0) {
            for (Map.Entry<String, String> entry : nonSigParams.entrySet()) {
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
            
        }        
        return b.toString();
    }
    
    private static final String HEXES = "0123456789abcdef";
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
    public static String constructSignature(LinkedHashMap<String, String> params,
            String checksumKey) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(checksumType);
            if (md != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    md.update(entry.getValue().getBytes());
                }
                md.update(checksumKey.getBytes());
            }
            
            String hex = getHex(md.digest());
            
            return hex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
