package com.g6pay.listener;

import java.util.ArrayList;

import com.g6pay.dto.TransactionDTO;

public interface G6TransactionListener {

    public void getAllTransactionsSuccess(ArrayList<TransactionDTO> transactions);
    public void getAllTransactionsFail();
    
}
