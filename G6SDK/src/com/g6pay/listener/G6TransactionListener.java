package com.g6pay.listener;

import java.util.ArrayList;

import com.g6pay.dto.TransactionDTO;

/**
 * Base class that receives notifications of transaction calls.
 * 
 * Implement this class to be alerted when get user transactions calls complete.
 * 
 * <pre>
 * <b>Sample usage:</b>
 * 
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6TransactionListener listener = new G6TransactionListener() {
            public void getAllTransactionsSuccess(String userId, ArrayList<TransactionDTO> transactions) {
                // handle transactions (implement this yourself)
                handleTransactionsComplete(transactions);
            }
            
            public void getAllTransactionsFail(String userId) {
                // handle failures
            }
        };
        
        sdk.getAllTransactions("USERIDHERE", listener);
 * </pre>
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 * 
 */
public abstract class G6TransactionListener {

    /**
     * Called when the server responds with the transactions for this user
     * @param userId This is the unique id of the user in your application
     * @param transactions The transactions for this user (in no particular order)
     */
    public void getAllTransactionsSuccess(String userId, ArrayList<TransactionDTO> transactions) {
        
    }
    
    /**
     * There was an error attempting to make this server call.  Please check
     * network availability, then contact G6 support
     * @param userId This is the unique id of the user in your application
     */
    public void getAllTransactionsFail(String userId) {
        
    }
    
}
