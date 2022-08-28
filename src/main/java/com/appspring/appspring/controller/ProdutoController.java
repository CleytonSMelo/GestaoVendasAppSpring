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


@RestController
@RequestMapping("**/Home/Produto/")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	

	@GetMapping("Cadastro")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/Home/Produto/Cadastro");
		mv.addObject("produtoobj", new Produto());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Produto produto, RedirectAttributes ra) {
		if (produto.getId() == 0) {
			produto.setDeletado(false);
			produtoRepository.save(produto);
			ra.addFlashAttribute("msg", "Produto Cadastrado com Sucesso");		
			ModelAndView mv = new ModelAndView("redirect:/Home/Produto/Cadastro");
			return mv;
		}else {
			Produto produtoAtualizar = produtoRepository.buscarPorId(produto.getId());
			produtoAtualizar.setDeletado(produtoAtualizar.getDeletado());
			produtoAtualizar.setNome(produto.getNome());
			produtoAtualizar.setValorCompra(produto.getValorCompra());
			produtoAtualizar.setCategoria(produto.getCategoria());
			produtoAtualizar.setFornecedor(produto.getFornecedor());
			produtoRepository.save(produtoAtualizar);
			ra.addFlashAttribute("msg", "Produto Atualizado com Sucesso");			
			ModelAndView mv = new ModelAndView("redirect:/Home/Produto/Cadastro");
			return mv;
		}
	}
	
	@GetMapping("EditarProduto/{idProduto}")
	public ModelAndView editar(@PathVariable("idProduto") Long idProduto, RedirectAttributes ra) {
		Produto Produto = produtoRepository.buscarPorId(idProduto);
		ModelAndView mv = new ModelAndView("/Home/Produto/Cadastro");
		mv.addObject("produtoobj", Produto);
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}
	
	@GetMapping("DeletarProduto/{idProduto}")
	public ModelAndView deletar(@PathVariable("idProduto") Long idProduto, RedirectAttributes ra) {
		Produto Produto = produtoRepository.buscarPorId(idProduto);
		Produto.setDeletado(true);
		produtoRepository.save(Produto);
		ra.addFlashAttribute("msg", "Produto Deletado com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Produto/Cadastro");
		return mv;
	}	
	
	@GetMapping(value = "{id}" , produces = "application/json")
	public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable(value = "id")Long id) {
		if (id != null) {
			Produto produto = produtoRepository.buscarPorId(id);	
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		}else {
			return new ResponseEntity<Produto>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
