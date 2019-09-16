package com.mordor.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReservationConfirmationDTO {
	private BigDecimal totalAmount;
	private Instant expirationTime;
	private String confirmationLink;	
}
