package com.g6pay.sdk;

import android.content.Context;

import com.g6pay.listener.G6OfferListener;
import com.g6pay.listener.G6TransactionListener;
import com.g6pay.listener.G6UserAccountListener;

public interface G6PublisherIF {
    public void showOffers(Context ctx, String userId,
            G6OfferListener listener);
    
    public void creditUser(String transactionId, String userId, float amount,
            G6UserAccountListener listener);
    
    public void debitUser(String transactionId, String userId, float amount,
            G6UserAccountListener listener);
    
    public void getAllTransactions(String userId, G6TransactionListener listener);
    
    public void getUserBalance(String userId, G6UserAccountListener listener);
}
