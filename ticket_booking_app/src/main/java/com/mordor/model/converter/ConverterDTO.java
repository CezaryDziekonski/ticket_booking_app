package com.mordor.model.converter;

import com.mordor.Utils.ResourceNotFoundException;

public interface ConverterDTO<T,S> {
	S convertToEntity(T dto) throws ResourceNotFoundException;
	T convertToDto(S entity);
}
