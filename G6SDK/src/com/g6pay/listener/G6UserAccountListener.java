package com.g6pay.listener;

public interface G6UserAccountListener {

    public void creditUserSuccess(String transactionId, float amount);
    public void creditUserFailure(String transactionId, float amount);
    
    public void debitUserSuccess(String transactionId, float amount);
    public void debitUserFailure(String transactionId, float amount);
    
    public void getUserBalanceSuccess(String userId, float balance);
    public void getUserBalanceFail();
    
}
