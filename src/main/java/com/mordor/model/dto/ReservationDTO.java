package com.mordor.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
	
	@NotBlank(message = "Name is mendatory")
	@Pattern(message = "reservation.name.invalid", regexp = "^[\\p{Lu}][\\p{L}]{2,}$")
	private String name;
	
	@NotBlank(message = "Surname is mendatory")
	@Pattern(message = "reservation.surname.invalid", 
		regexp = "^[\\p{Lu}][\\p{L}]{2,}-[\\p{Lu}][\\p{L}]{2,}$|^[\\p{Lu}][\\p{L}]{2,}$")
	private String surname;
	
	@Size(min = 1)
	private List<TicketDTO> tickets;
}
