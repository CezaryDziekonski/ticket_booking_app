package com.mordor.model.mapper;

import org.modelmapper.ModelMapper;

public interface MapperDTO<T,S> {
	static final ModelMapper modelMapper = new ModelMapper();
	
	S mapToEntity(T dto);
	T mapToDTO(S entity);
}
