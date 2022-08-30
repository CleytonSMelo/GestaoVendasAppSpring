package com.appspring.appspring.service;

import org.springframework.stereotype.Service;

import com.appspring.appspring.dto.VerificaStringDto;

@Service
public class RestfullService {

	public VerificaStringDto ConsultarVogal(String string) {
		long tempoInicial = System.currentTimeMillis();

		char[] vogais = { 'a', 'e', 'i', 'o', 'u' };
		char[] consoante = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x',
				'w', 'y', 'z' };
		String vogaisEncontradas = "";
		String str = "";
		String strAux = "";

		int totalCharacters = 0;
		char vogal;

		for (int i = 0; i < string.length(); i++) {
			for (int j = 0; j < vogais.length; j++) {
				if (Character.toLowerCase(string.charAt(i)) == vogais[j]) {
					vogaisEncontradas = vogaisEncontradas + string.charAt(i);
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

		for (int i = 0; i < str.length(); i++) {
			vogal = Character.toLowerCase(str.charAt(i));
			for (int j = 0; j < vogaisEncontradas.length(); j++) {
				if (vogal == Character.toLowerCase(vogaisEncontradas.charAt(j))) {
					totalCharacters++;
				}
			}
			if (totalCharacters == 1) {
				strAux = strAux + str.charAt(i);
				break;
			} else {
				totalCharacters = 0;
			}
		}

		VerificaStringDto texto = new VerificaStringDto();
		texto.setString(string);
		texto.setVogal(strAux);
		long tempoFinal = System.currentTimeMillis();
		String ms = String.valueOf(((tempoFinal - tempoInicial) / 1000d));
		texto.setTempoTotal(ms + "ms");
		return texto;

	}
}
