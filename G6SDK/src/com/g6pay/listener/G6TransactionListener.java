package com.g6pay.listener;

import java.util.ArrayList;

import com.g6pay.dto.TransactionDTO;

public abstract class G6TransactionListener {

    public void getAllTransactionsSuccess(String userId, ArrayList<TransactionDTO> transactions) {
        
    }
    
    public void getAllTransactionsFail(String userId) {
        
    }
    
}
