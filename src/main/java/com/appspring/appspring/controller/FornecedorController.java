package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Fornecedor;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.service.FornecedorService;

@Controller
@RequestMapping("**/Home/Fornecedor/")
public class FornecedorController {

	private final FornecedorService fornecedorService;
	
	@Autowired
	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = fornecedorService.CadastroFornecedor();
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Fornecedor fornecedor, RedirectAttributes ra) {
		ModelAndView mv = fornecedorService.SalvarFornecedor(fornecedor, ra);
		return mv;
	}
	
	@GetMapping("EditarFornecedor/{idFornecedor}")
	public ModelAndView editar(@PathVariable("idFornecedor") Long idFornecedor, RedirectAttributes ra) {
		ModelAndView mv = fornecedorService.EditarFornecedor(idFornecedor, ra);
		return mv;
	}
	
	@GetMapping("DeletarFornecedor/{idFornecedor}")
	public ModelAndView deletar(@PathVariable("idFornecedor") Long idFornecedor, RedirectAttributes ra) {
		ModelAndView mv = fornecedorService.DeletarFornecedor(idFornecedor, ra);
		return mv;
	}	
}
