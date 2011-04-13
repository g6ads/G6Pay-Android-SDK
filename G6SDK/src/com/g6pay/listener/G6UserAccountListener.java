package com.g6pay.listener;

public abstract class G6UserAccountListener {

    public void creditUserSuccess(String userId, String transactionId, float amount) {
    }
    public void creditUserFailure(String userId, String transactionId, float amount) {
    }
    
    public void debitUserSuccess(String userId, String transactionId, float amount) {
        
    }
    public void debitUserFailure(String userId, String transactionId, float amount) {
        
    }
    
    public void getUserBalanceSuccess(String userId, float balance) {
        
    }
    public void getUserBalanceFail(String userId) {
        
    }
    
}
