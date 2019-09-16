package com.mordor.model;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationConfirmation {
	private BigDecimal totalAmount;
	private Instant expirationTime;
	private String confirmationLink;
}
