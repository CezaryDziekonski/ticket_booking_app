package com.mordor.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import com.mordor.model.enitity.Reservation;
import com.mordor.service.ReservationService;

@Component
public class ReservationExpirationTask {
	
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	private ApplicationContext appContext;
	
	@Autowired
	public ReservationExpirationTask(ApplicationContext appContext, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		this.appContext = appContext;
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
	}
	
	@PostConstruct
	private void init() {
		threadPoolTaskScheduler.setPoolSize(10);
	}
	
	public void schedule(Reservation reservation, Instant screeningTime) {
		Instant expirationDueToScreeningTime = screeningTime.minus(
				ReservationService.durationToExpirationOfReservation, ChronoUnit.MINUTES);
		Date scheduledDate;
		
		//delete reservation due to reservation expiration Time
		if(reservation.getExpirationTime().compareTo(expirationDueToScreeningTime) < 0) {
			scheduledDate = Date.from(reservation.getExpirationTime());
		}
		//delete due to screening time
		else { 
			scheduledDate = Date.from(expirationDueToScreeningTime);
		}
		Runnable runnable = () -> {
			Reservation rs = appContext.getBean(ReservationService.class).findById(reservation.getId());
			if(!rs.getConfirmed()) {
				appContext.getBean(ReservationService.class).deleteById(reservation.getId());
			}};

		threadPoolTaskScheduler.schedule(runnable,scheduledDate);	
		}
}
