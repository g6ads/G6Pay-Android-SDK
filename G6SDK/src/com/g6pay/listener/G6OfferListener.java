package com.g6pay.listener;

import com.g6pay.dto.OfferDTO;

/**
 * Base class that receives notifications of offer completion.
 * 
 * Implement this class to be alerted when user completes an offer.
 * 
 * <pre>
 * <b>Sample usage:</b>
 * 
        G6Pay sdk = G6Pay.getG6PayInstance(this.getApplicationContext());

        G6OfferListener listener = new G6OfferListener() {
            public void offerWasCompleted(OfferDTO offer) {
                // handle completed offer (implement this yourself)
                handleOfferComplete();
            }
        };
        
        sdk.showOffers(this.getApplicationContext(), "USERIDHERE", listener);
 * </pre>
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public abstract class G6OfferListener {
    
    /**
     * Called when a user has successfully completed an offer.
     * @param offer Details of the offer
     */
    public void offerWasCompleted(OfferDTO offer) {
        
    }

}
