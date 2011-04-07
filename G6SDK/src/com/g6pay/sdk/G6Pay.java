package com.g6pay.sdk;

import java.util.ArrayList;
import java.util.HashMap;

import com.g6pay.dto.OfferDTO;
import com.g6pay.dto.TransactionDTO;

import android.content.Context;

public class G6Pay {

    private static String APPID = null;
    private static HashMap<String, String> udids = null;
    
    public static void init(Context applicationContext) {
        // This will set the APPID, UDIDS
    }
    
    /**
     * Launch a WebView with offers for the user to click
     */
    public static void showOffers(String userId) {
        
    }
    
    /**
     * Grabs completed offer from the server. 
     * TODO: add documentation for offer states
     * @return null if no offer is completed or the appropriate offer
     */
    public static OfferDTO getCompletedOffer(String userId) {
        
        return null;
    }
    
    public static boolean creditUser(String transactionId, String userId, float amount) {
        return false;
    }
    
    public static boolean debitUser(String transactionId, String userId, float amount) {
        return false;
    }
    
    public ArrayList<TransactionDTO> getAllTransactions(String userId) {
        return null;
    }
    
    public float getUserBalance(String userId) {
        return 0;
    }
}
