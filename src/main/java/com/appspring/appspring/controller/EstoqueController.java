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

import com.appspring.appspring.modelo.Estoque;
import com.appspring.appspring.modelo.Produto;
import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.ProdutoRepository;
import com.appspring.appspring.service.EstoqueService;

@RestController
@RequestMapping("**/Home/Estoque/")
public class EstoqueController {
	
	private final EstoqueService estoqueService;
	
	@Autowired
	public EstoqueController(EstoqueService estoqueService) {
		this.estoqueService = estoqueService;
	}
	
	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = estoqueService.CadastroEstoque();
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Estoque estoque, RedirectAttributes ra) {
		ModelAndView mv = estoqueService.SalvarEstoque(estoque, ra);
		return mv;
	}
	
	@GetMapping("EditarEstoque/{idEstoque}")
	public ModelAndView editar(@PathVariable("idEstoque") Long idEstoque, RedirectAttributes ra) {
		ModelAndView mv = estoqueService.EditarEstoque(idEstoque, ra);
		return mv;
	}
	
	@GetMapping("DeletarEstoque/{idEstoque}")
	public ModelAndView deletar(@PathVariable("idEstoque") Long idEstoque, RedirectAttributes ra) {
		ModelAndView mv = estoqueService.DeletarEstoque(idEstoque, ra);
		return mv;
	}
	
	@GetMapping(value = "{id}" , produces = "application/json")
	public ResponseEntity<Estoque> buscarProdutoPorId(@PathVariable(value = "id")Long id) {
		if (id != null) {
			Estoque estoque = estoqueService.buscaProdutoPorId(id);
			return new ResponseEntity<Estoque>(estoque, HttpStatus.OK);
		}else {
			return new ResponseEntity<Estoque>(HttpStatus.BAD_REQUEST);
		}		
	}
}
