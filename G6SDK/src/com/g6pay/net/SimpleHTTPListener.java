package com.g6pay.net;

public interface SimpleHTTPListener {
    public void resultBody(String body);
    public void requestFailed(int statusCode);

}
