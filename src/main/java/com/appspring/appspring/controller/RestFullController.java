package com.appspring.appspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appspring.appspring.dto.VerificaStringDto;

@RestController
@RequestMapping("/api/verificarvogal/")
public class RestFullController {
	
	@GetMapping(value = "{string}" , produces = "application/json")
	public ResponseEntity<VerificaStringDto> verificarVogal(@PathVariable(value = "string") String string) {
		long tempoInicial = System.currentTimeMillis();
		
		
		//String string = "aAbBABacafe";
		char[] vogais = { 'a', 'e', 'i', 'o', 'u' };
		char[] consoante = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x','w', 'y', 'z' };
		
		String str = "";
		String strAux ="";
		
		for (int i = 0; i < string.length(); i++) {
			for (int j = 0; j < vogais.length; j++) {
				if (Character.toLowerCase(string.charAt(i)) == vogais[j]) {
					if (i > 0) {
						for (int j2 = 0; j2 < consoante.length; j2++) {
							if (Character.toLowerCase(string.charAt(i - 1)) == consoante[j2]) {
								if (i > 1) {
									for (int j3 = 0; j3 < vogais.length; j3++) {
										if (Character.toLowerCase(string.charAt(i - 2)) == vogais[j3]) {
											str = str + string.charAt(i);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		for (int k = str.length() -1; k >= 0 ; k--) {			
				if(Character.toLowerCase(str.charAt(k)) != Character.toLowerCase(str.charAt(k-1))) {					
					strAux = strAux + str.charAt(k);
					break;
				}else {
					k--;
				}						
		}	
		
		VerificaStringDto texto = new VerificaStringDto();
		texto.setString(string);
		texto.setVogal(strAux);	
		long tempoFinal = System.currentTimeMillis();
		String ms = String.valueOf(((tempoFinal - tempoInicial) / 1000d));
		texto.setTempoTotal(ms+"ms");
		
	return new ResponseEntity<VerificaStringDto>(texto,HttpStatus.OK);		
		
	}

}
