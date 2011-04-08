package com.g6pay.net;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Really simple async http task.  Performs a call and returns the body if
 * successful otherwise, notifies of a failed result..
 * @author phsu
 *
 */
public class SimpleAsyncHTTPTask extends AsyncTask<SimpleHTTPRequest, Void, SimpleHTTPResult> {
    private SimpleHTTPRequest request;
    
    @Override
    protected SimpleHTTPResult doInBackground(SimpleHTTPRequest... requests) {
        int count = requests.length;
        
        if (count <= 0) return null;
        
        this.request = requests[0];

        HttpURLConnection connection = null;
        BufferedReader rd  = null;
        StringBuilder sb = null;
        String line = null;
      
        URL serverAddress = null;
      
        try {
            
            Log.d("G6Pay", "URL:"+request.getURLString());
            
            serverAddress = new URL(request.getURLString());
            connection = null;
          
            // Set up the connection
            connection = (HttpURLConnection)serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
                      
            connection.connect();
          
            sb = new StringBuilder("");
            // Read the result
            try {
                rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          
                while ((line = rd.readLine()) != null)
                {
                    sb.append(line);
                }
            } catch (FileNotFoundException e) {
                // no body, just return ""
            }
            
            SimpleHTTPResult result = new SimpleHTTPResult();
            Log.d("G6Pay", "Response:" + connection.getResponseCode() + ":" +
                    connection.getResponseMessage());
            result.setResponseCode(connection.getResponseCode());
            result.setResponseMessage(connection.getResponseMessage());
            result.setResponseBody(sb.toString());
          
            return result;
                      
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            //close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            sb = null;
            connection = null;
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(SimpleHTTPResult result) {
        
        if (result != null) {
            if (result.getResponseCode() >= 200 && result.getResponseCode() < 300) {
                request.resultBody(result.getResponseBody());
            } else {
                request.requestFailed(result.getResponseCode());
            }
        } else {
            request.requestFailed(0);
        }
            
    }


}

