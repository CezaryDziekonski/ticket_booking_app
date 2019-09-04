package com.mordor.Utils;

import com.mordor.model.enitity.Reservation;
import com.mordor.service.ReservationService;
import com.mordor.service.ReservationServiceImpl;


public class RunnableExpirationTask implements Runnable {

	private Reservation reservation;
	
	public RunnableExpirationTask(Reservation reservation) {
		this.reservation = reservation;
	}
	
	@Override
	public void run() {
		ApplicationContextProvider appContext = new ApplicationContextProvider();
		ReservationService reservatonBean = appContext.getApplicationContext().getBean("reservationServiceImpl", ReservationServiceImpl.class);
		
		Reservation rs = null;
		try {
			rs = reservatonBean.findById(reservation.getId());
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		if (rs != null) {
			if(!rs.IsConfirmed()) {
				reservatonBean.deleteById(reservation.getId());
			}
		}	
	}

}
