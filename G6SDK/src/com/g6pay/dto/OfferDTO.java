package com.g6pay.dto;

/**
 * DTO with details of an offer in progress or completed by the user.
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class OfferDTO {
    private String userId;
    private String offerId;
    private String offerName;
    private float netPayout;
    private float virtualCurrencyAmount;
    private float userBalance;
    private String signature;
    
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
    public float getUserBalance() {
        return userBalance;
    }
    public void setUserBalance(float userBalance) {
        this.userBalance = userBalance;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    @Override
    public String toString() {
        return "OfferDTO [userId=" + userId + ", offerId=" + offerId
                + ", offerName=" + offerName + ", netPayout=" + netPayout
                + ", virtualCurrencyAmount=" + virtualCurrencyAmount
                + ", userBalance=" + userBalance + ", signature=" + signature
                + "]";
    }

    
}
