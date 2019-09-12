package com.mordor.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
	private Long screeningId;
	private Long seatId;
	private Long ticketTypeId;
}
