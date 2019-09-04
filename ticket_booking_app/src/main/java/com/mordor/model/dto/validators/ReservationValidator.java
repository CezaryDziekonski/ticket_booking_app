package com.mordor.model.dto.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import com.mordor.model.dto.ReservationDTO;


@Component()
@Qualifier("reservationValidator")
public class ReservationValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> valClass) {
		
		return ReservationDTO.class.equals(valClass);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		ReservationDTO reservation = (ReservationDTO) obj;
		
		if (checkInputString(reservation.getName())) {
			errors.rejectValue("name", "name.empty");
			
		}
		else if (checkName(reservation.getName())) {
			errors.rejectValue ("name", "name.wrongPattern");
		}
		
		
		if (checkInputString(reservation.getSurname())) {
			errors.rejectValue("surname", "surname.empty");
		}
		else if (checkSurname(reservation.getSurname())) {
			errors.rejectValue("surname", "surname.wrongPattern");
			
		}
		
		if (checkTicketNumber(reservation.getTickets().size())) {
			errors.rejectValue("tickets", "tickets.empty");
		}
		
	}
	
	private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
	
	private boolean checkName(String input) {
		Pattern p = Pattern.compile("^[\\p{Lu}][\\p{L}]{2,}$");
		Matcher m = p.matcher(input);
		
		return !m.matches();
		
	}
	private boolean checkSurname(String input) {
		Pattern p1 = Pattern.compile("^[\\p{Lu}][\\p{L}]{2,}$");
		Matcher m1 = p1.matcher(input);
		
		Pattern p2 = Pattern.compile("^[\\p{Lu}][\\p{L}]{2,}-[\\p{Lu}][\\p{L}]{2,}$");
		Matcher m2 = p2.matcher(input);
		
		return !(m1.matches() || m2.matches());	
	}
	
	private boolean checkTicketNumber(int size) {
		return (size < 1);
	}
		
}
