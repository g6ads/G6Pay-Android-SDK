package com.g6pay.net;

/**
 * Simple HTTP listener.
 * 
 * For G6Pay SDK internal consumption
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public interface SimpleHTTPListener {
    public void resultBody(String body);
    public void requestFailed(int statusCode);

}
