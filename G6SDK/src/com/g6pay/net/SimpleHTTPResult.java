package com.g6pay.net;

/**
 * Basic HTTP result with status code, status message, and body
 * 
 * For G6Pay SDK internal consumption
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class SimpleHTTPResult {
    private int responseCode;
    private String responseBody;
    private String responseMessage;
    public int getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseBody() {
        return responseBody;
    }
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    
}
