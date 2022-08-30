package com.appspring.appspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Produto;
import com.appspring.appspring.repository.CategoriaRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ModelAndView CadastroProduto() {
		ModelAndView mv = new ModelAndView("Home/Produto/Cadastro");
		mv.addObject("produtoobj", new Produto());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}

	public ModelAndView SalvarProduto(Produto produto, RedirectAttributes ra) {
		if (produto.getId() == 0) {
			produto.setDeletado(false);
			produtoRepository.save(produto);
			ra.addFlashAttribute("msg", "Produto Cadastrado com Sucesso");
			ModelAndView mv = new ModelAndView("redirect:/Home/Produto/Cadastro");
			return mv;
		} else {
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

	public ModelAndView EditarProduto(Long idProduto, RedirectAttributes ra) {
		Produto Produto = produtoRepository.buscarPorId(idProduto);
		ModelAndView mv = new ModelAndView("Home/Produto/Cadastro");
		mv.addObject("produtoobj", Produto);
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}

	public ModelAndView DeletarProduto(Long idProduto, RedirectAttributes ra) {
		Produto Produto = produtoRepository.buscarPorId(idProduto);
		Produto.setDeletado(true);
		produtoRepository.save(Produto);
		ra.addFlashAttribute("msg", "Produto Deletado com Sucesso");
		ModelAndView mv = new ModelAndView("redirect:/Home/Produto/Cadastro");
		return mv;
	}
	
	public Produto buscaProdutoPorId(Long id) {
		Produto produto = produtoRepository.buscarPorId(id);
		return produto;
	}
}
