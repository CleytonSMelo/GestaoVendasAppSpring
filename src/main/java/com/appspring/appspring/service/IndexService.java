package com.appspring.appspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.VendasRepository;

@Service
public class IndexService {

	@Autowired
	private VendasRepository vendasRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	public ModelAndView index() {
		Long qtd = estoqueRepository.QuantidadedeProdutosRegistrado();
		if (qtd == null) {
			int qtdAux = 0;
			qtd = Long.valueOf(qtdAux);
		}
		ModelAndView mv = new ModelAndView("Home/Dashboard/index");
		mv.addObject("totalVendas", vendasRepository.totalVendas());
		mv.addObject("totalFornecedores", fornecedorRepository.TotalFornecedoresRegistrado());
		mv.addObject("produtoemEstoque", qtd);
		return mv;
	}
}
