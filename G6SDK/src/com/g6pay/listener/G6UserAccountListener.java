package com.g6pay.listener;

/**
 * 
 * Base class that receives notifications of user account calls.
 * 
 * Implement this class to be alerted when get user account calls complete.
 * 
 * <pre>
 * <b>Sample usage for credit:</b>
 * 
        float amount = 1.50f;
        
        G6UserAccountListener listener = new G6UserAccountListener() {
            public void creditUserSuccess(String userId, String transactionId, float amount) {
                // handle credit (implement this yourself)
                creditUserSuccess(amount);
            }
            public void creditUserFailure(String userId, String transactionId, float amount) {
                // handle failures
            }
        };
        
        sdk.creditUser("TRANSACTIONIDHERE", "USERIDHERE", amount,
                listener);
 *
 * <b>Sample usage for debit:</b>
 * 
 
        float amount = 1.50f;
        
        G6UserAccountListener listener = new G6UserAccountListener() {
            public void debitUserSuccess(String userId, String transactionId, float amount) {
                // handle debit (implement this yourself)
                debitUserSuccess(amount);
            }
            public void debitUserFailure(String userId, String transactionId, float amount) {
                // handle failures
            }
        };
        
        sdk.debitUser("TRANSACTIONIDHERE", "USERIDHERE", amount,
                listener);
 *       
 *  
 * <b>Sample usage for get user balance:</b>
 * 
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6UserAccountListener listener = new G6UserAccountListener() {
            public void getUserBalanceSuccess(String userId, float balance) {
                // handle balance (implement this yourself)
                handleUserBalance(balance);
            }
            public void getUserBalanceFail(String userId) {
                // handle failures
            }
        };
        
        sdk.getUserBalance("USERIDHERE", listener);
 * </pre>
 * 
 * 
 */
public abstract class G6UserAccountListener {

    /**
     * This is called when the user was successfully credited.
     * @param userId This is the unique id of the user in your application
     * @param transactionId The unique transaction id
     * @param amount The amount in this transaction
     */
    public void creditUserSuccess(String userId, String transactionId, float amount) {
    }
    /**
     * There was an error attempting to make this server call.  Please check
     * network availability, then contact G6 support
     * @param userId This is the unique id of the user in your application
     * @param transactionId The unique transaction id
     * @param amount The amount in this transaction
     */
    public void creditUserFailure(String userId, String transactionId, float amount) {
    }
    
    /**
     * This is called when the user was successfully debited.
     * @param userId This is the unique id of the user in your application
     * @param transactionId The unique transaction id
     * @param amount The amount in this transaction
     */
    public void debitUserSuccess(String userId, String transactionId, float amount) {
        
    }
    
    /**
     * There was an error attempting to make this server call.  Please check
     * network availability, then contact G6 support
     * @param userId This is the unique id of the user in your application
     * @param transactionId The unique transaction id
     * @param amount The amount in this transaction
     */
    public void debitUserFailure(String userId, String transactionId, float amount) {
        
    }
    
    /**
     * The user's balance was successfully retrieved from the server
     * @param userId This is the unique id of the user in your application
     * @param balance The current balance of the user
     */
    public void getUserBalanceSuccess(String userId, float balance) {
        
    }
    
    /**
     * There was an error attempting to make this server call.  Please check
     * network availability, then contact G6 support
     * @param userId This is the unique id of the user in your application
     */
    public void getUserBalanceFail(String userId) {
        
    }
    
}
