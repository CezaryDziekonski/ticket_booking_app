package com.mordor.model;

import java.net.URL;
import java.time.Instant;

public class ReservationConfirmation {
	
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
	
	
	
	

}
