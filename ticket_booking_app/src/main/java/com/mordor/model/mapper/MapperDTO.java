package com.mordor.model.mapper;

import org.modelmapper.ModelMapper;

import com.mordor.exception.ResourceNotFoundException;

public interface MapperDTO<T,S> {
	static final ModelMapper modelMapper = new ModelMapper();
	
	S mapToEntity(T dto) throws ResourceNotFoundException;
	T mapToDTO(S entity);
}
