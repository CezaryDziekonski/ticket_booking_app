package com.mordor.model.dto;

import java.net.URL;
import java.time.Instant;

public class ReservationConfirmationDTO {
	
	private double totalAmount;
	
	private Instant expirationTime;
	
	private URL confirmationLink;

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Instant getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Instant expirationTime) {
		this.expirationTime = expirationTime;
	}

	public URL getConfirmationLink() {
		return confirmationLink;
	}

	public void setConfirmationLink(URL confirmationLink) {
		this.confirmationLink = confirmationLink;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationConfirmationDTO res = (ReservationConfirmationDTO) o;
        return this.totalAmount == res.totalAmount &&
                this.confirmationLink.toString().equals(res.getConfirmationLink().toString()) &&
                this.expirationTime.equals(res.getExpirationTime());
    }
	
	
	
}
