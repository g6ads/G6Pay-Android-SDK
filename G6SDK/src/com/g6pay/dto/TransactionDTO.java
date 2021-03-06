package com.g6pay.dto;

import java.util.Date;

/**
 * DTO with data regarding an individual transaction made by the user.
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class TransactionDTO {

    private String userId;
    private String offerId;
    private String offerName;
    private float netPayout;
    private float virtualCurrencyAmount;
    private Date date;
    private String description;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOfferId() {
        return offerId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
    public String getOfferName() {
        return offerName;
    }
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    public float getNetPayout() {
        return netPayout;
    }
    public void setNetPayout(float netPayout) {
        this.netPayout = netPayout;
    }
    public float getVirtualCurrencyAmount() {
        return virtualCurrencyAmount;
    }
    public void setVirtualCurrencyAmount(float virtualCurrencyAmount) {
        this.virtualCurrencyAmount = virtualCurrencyAmount;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "TransactionDTO [userId=" + userId + ", offerId=" + offerId
                + ", offerName=" + offerName + ", netPayout=" + netPayout
                + ", virtualCurrencyAmount=" + virtualCurrencyAmount
                + ", date=" + date + ", description=" + description + "]";
    }
    
    
}
