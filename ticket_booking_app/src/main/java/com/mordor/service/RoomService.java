package com.mordor.service;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.enitity.Room;

public interface RoomService {
	Room findById(Long id) throws ResourceNotFoundException;
}
