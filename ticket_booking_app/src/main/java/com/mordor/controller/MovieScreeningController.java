package com.mordor.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mordor.model.dto.ScreeningsDTO;

@RestController
@RequestMapping("/api")
public interface MovieScreeningController {
	
	@RequestMapping(value = "/screening", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreeningsDTO>getScreenings(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timeBegin, 
											@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timeEnd);	
}