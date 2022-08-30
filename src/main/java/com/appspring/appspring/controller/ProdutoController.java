package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Produto;
import com.appspring.appspring.repository.CategoriaRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.ProdutoRepository;
import com.appspring.appspring.service.ProdutoService;


@RestController
@RequestMapping("**/Home/Produto/")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	
	@Autowired
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = produtoService.CadastroProduto();
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Produto produto, RedirectAttributes ra) {
		ModelAndView mv = produtoService.SalvarProduto(produto, ra);
		return mv;
	}
	
	@GetMapping("EditarProduto/{idProduto}")
	public ModelAndView editar(@PathVariable("idProduto") Long idProduto, RedirectAttributes ra) {
		ModelAndView mv = produtoService.EditarProduto(idProduto, ra);
		return mv;
	}
	
	@GetMapping("DeletarProduto/{idProduto}")
	public ModelAndView deletar(@PathVariable("idProduto") Long idProduto, RedirectAttributes ra) {
		ModelAndView mv = produtoService.DeletarProduto(idProduto, ra);
		return mv;
	}	
	
	@GetMapping(value = "{id}" , produces = "application/json")
	public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable(value = "id")Long id) {
		if (id != null) {
			Produto produto = produtoService.buscaProdutoPorId(id);
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		}else {
			return new ResponseEntity<Produto>(HttpStatus.BAD_REQUEST);
		}	
	}
}
