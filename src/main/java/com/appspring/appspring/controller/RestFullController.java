package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appspring.appspring.dto.VerificaStringDto;
import com.appspring.appspring.service.RestfullService;

@RestController
@RequestMapping("**/api/consultarvogal/")
public class RestFullController {
	
	
	private final RestfullService restfullService;
	
	@Autowired
    public RestFullController(RestfullService restfullService) {
        this.restfullService = restfullService;
    }
	
	@GetMapping(value = "{string}" , produces = "application/json")
	public ResponseEntity<VerificaStringDto> consultarvogal(@PathVariable(value = "string") String string) {
		VerificaStringDto texto = restfullService.ConsultarVogal(string);
		return new ResponseEntity<VerificaStringDto>(texto,HttpStatus.OK);				
	}
}
