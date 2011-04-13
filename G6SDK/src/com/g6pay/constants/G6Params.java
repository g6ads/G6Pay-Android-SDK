package com.g6pay.constants;

/**
 * Constants for the G6Pay platform.
 * 
 * Android Manifest params are prefixed with ANDROID_MANIFEST_
 * HTTP params are prefixed with G6_PARAM_
 * G6 API URLs are prefixed with G6_API_URL_
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class G6Params {
    public static final String G6_API_URL_INSTALLCONFIRM =     
        "http://www.g6pay.com/api/installconfirm";
    
    public static final String G6_API_URL_OFFERWALL =
        "http://www.g6pay.com/api/buycurrency";
    public static final String G6_API_URL_ISCOMPLETED =
        "http://www.g6pay.com/api/iscompleted";
    
    public static final String G6_API_URL_CREDIT = 
        "http://www.g6pay.com/api/credit";
    public static final String G6_API_URL_DEBIT = 
        "http://www.g6pay.com/api/debit";
    public static final String G6_API_URL_BALANCE =  
        "http://www.g6pay.com/api/getuserbalance";
    
    public static final String G6_API_URL_TRANSACTIONS =      
        "http://www.g6pay.com/api/getalltransactions";
    
    public static final String ANDROID_MANIFEST_APP_ID         = "G6_APP_ID";
    public static final String ANDROID_MANIFEST_SECRET_KEY     = "G6_SECRET_KEY";
    
    public static final String UDID_MAP                        = "udids";
    
    public static final String PLATFORM_ID_ANDROID             = "android";

    public static final String G6_PARAM_APP_ID                 = "app_id";
    public static final String G6_PARAM_SECRET_KEY             = "secret_key";
    public static final String G6_PARAM_UDID                   = "udid";
    public static final String G6_PARAM_PHONE_ID               = "phone_id";
    public static final String G6_PARAM_USER_ID                = "user_id";
    public static final String G6_PARAM_OFFER_ID               = "offer_id";
    public static final String G6_PARAM_OFFER_NAME             = "offer_name";
    public static final String G6_PARAM_NET_PAYOUT             = "net_payout";
    public static final String G6_PARAM_CURRENCY               = "virtual_currency_amount";
    public static final String G6_PARAM_DATE                   = "date";
    public static final String G6_PARAM_DESCRIPTION            = "description";
    public static final String G6_PARAM_USER_BALANCE           = "user_balance";
    public static final String G6_PARAM_SIGNATURE              = "signature";
    public static final String G6_PARAM_TIMESTAMP              = "timestamp";
    public static final String G6_PARAM_PLATFORM               = "platform";
    public static final String G6_PARAM_CREDIT_TRANSACTION_ID  = "credit_transaction_id";
    public static final String G6_PARAM_DEBIT_TRANSACTION_ID   = "debit_transaction_id";
    public static final String G6_PARAM_AMOUNT                 = "amount";
}
