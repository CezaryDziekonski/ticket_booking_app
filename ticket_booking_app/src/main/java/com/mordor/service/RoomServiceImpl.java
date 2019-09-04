package com.mordor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.dao.RoomDAO;
import com.mordor.model.enitity.Room;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomDAO roomDAO;
	
	@Override
	public Room findById(Long id) throws ResourceNotFoundException {
		return roomDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.RoomNotFound + id));
}
}
