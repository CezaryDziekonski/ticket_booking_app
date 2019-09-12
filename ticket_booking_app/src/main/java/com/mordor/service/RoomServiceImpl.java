package com.mordor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mordor.dao.RoomDAO;
import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.enitity.Room;

@Service
public class RoomServiceImpl implements RoomService{
	
	private RoomDAO roomDAO;
	
	@Autowired
	public RoomServiceImpl(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}
	
	@Override
	public Room findById(Long id) {
		return roomDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room with id: " + id + " not found"));
	}
}
