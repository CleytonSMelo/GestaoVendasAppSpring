package com.appspring.appspring.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.dto.CategoriaDto;
import com.appspring.appspring.modelo.Estoque;
import com.appspring.appspring.modelo.Fornecedor;
import com.appspring.appspring.modelo.Produto;
import com.appspring.appspring.modelo.Vendas;
import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.ProdutoRepository;
import com.appspring.appspring.repository.VendasRepository;
import com.appspring.appspring.service.VendasService;

@Controller
@RequestMapping("**/Home/Vendas/")
public class VendasController {
		
	private final VendasService vendasService;
	
	@Autowired
	public VendasController(VendasService vendasService) {
		this.vendasService = vendasService;
	}

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = vendasService.cadastrarVenda();
		return mv;
	}
	
	@GetMapping("ListarVendas")
	public ModelAndView ListarVendas() {
		ModelAndView mv = vendasService.ListarVenda();
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Vendas venda ,RedirectAttributes ra) {
		ModelAndView mv = vendasService.SalvarVenda(venda, ra);
		return mv;
	}
			
	@GetMapping("Deletar/{idVendas}")
	public ModelAndView deletar(@PathVariable("idVendas") Long idVendas, RedirectAttributes ra) {
		ModelAndView mv = vendasService.DeletarVenda(idVendas, ra);
		return mv;
	}
	
	@GetMapping("Relatorio")
	public ModelAndView Relatorio() {
		ModelAndView mv = vendasService.RelatorioVenda();
		return mv;
	}
	

}
