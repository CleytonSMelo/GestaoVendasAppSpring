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

@RestController
@RequestMapping("**/Home/Estoque/")
public class EstoqueController {

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	

	@GetMapping("Cadastro")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("Home/Estoque/Cadastro");
		mv.addObject("estoqueobj", new Estoque());
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Estoque estoque, RedirectAttributes ra) {
		if (estoque.getId() == 0) {
			estoque.setDeletado(false);
			estoqueRepository.save(estoque);
			ra.addFlashAttribute("msg", "Produto adicionado ao Estoque com Sucesso");		
			ModelAndView mv = new ModelAndView("redirect:/Home/Estoque/Cadastro");
			return mv;
		}else {
			Estoque EstoqueAtualizar = estoqueRepository.buscarPorId(estoque.getId());
			EstoqueAtualizar.setDeletado(EstoqueAtualizar.getDeletado());
			EstoqueAtualizar.setQuantidade(estoque.getQuantidade());
			EstoqueAtualizar.setValorVenda(estoque.getValorVenda());
			estoqueRepository.save(EstoqueAtualizar);
			ra.addFlashAttribute("msg", "Estoque Atualizado com Sucesso");			
			ModelAndView mv = new ModelAndView("redirect:/Home/Estoque/Cadastro");
			return mv;
		}
	}
	
	@GetMapping("EditarEstoque/{idEstoque}")
	public ModelAndView editar(@PathVariable("idEstoque") Long idEstoque, RedirectAttributes ra) {
		Estoque estoque = estoqueRepository.buscarPorId(idEstoque);
		ModelAndView mv = new ModelAndView("Home/Estoque/Cadastro");
		mv.addObject("estoqueobj", estoque);
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		return mv;
	}
	
	@GetMapping("DeletarEstoque/{idEstoque}")
	public ModelAndView deletar(@PathVariable("idEstoque") Long idEstoque, RedirectAttributes ra) {
		Estoque estoque = estoqueRepository.buscarPorId(idEstoque);
		estoque.setDeletado(true);
		estoqueRepository.save(estoque);
		ra.addFlashAttribute("msg", "Produto Deletado do Estoque com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Estoque/Cadastro");
		return mv;
	}
	
	@GetMapping(value = "{id}" , produces = "application/json")
	public ResponseEntity<Estoque> buscarProdutoPorId(@PathVariable(value = "id")Long id) {
		if (id != null) {
			Estoque estoque = estoqueRepository.buscarPorId(id);	
			return new ResponseEntity<Estoque>(estoque, HttpStatus.OK);
		}else {
			return new ResponseEntity<Estoque>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
