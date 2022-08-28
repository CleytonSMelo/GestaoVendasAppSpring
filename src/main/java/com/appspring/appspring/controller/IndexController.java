package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.VendasRepository;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private VendasRepository vendasRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@GetMapping("/")
	public ModelAndView index() {
		Long qtd = estoqueRepository.QuantidadedeProdutosRegistrado();
		if (qtd == null) {
			int qtdAux = 0;
			qtd = Long.valueOf(qtdAux);
		}
		ModelAndView mv = new ModelAndView("/Home/Dashboard/index");
		mv.addObject("totalVendas", vendasRepository.totalVendas());
		mv.addObject("totalFornecedores", fornecedorRepository.TotalFornecedoresRegistrado());
		mv.addObject("produtoemEstoque", qtd);
		return mv;
	}
}
