package com.appspring.appspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Estoque;
import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.ProdutoRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public ModelAndView CadastroEstoque() {
		ModelAndView mv = new ModelAndView("Home/Estoque/Cadastro");
		mv.addObject("estoqueobj", new Estoque());
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		return mv;
	}
	
	public ModelAndView SalvarEstoque(Estoque estoque, RedirectAttributes ra) {
		if (estoque.getId() == 0) {
			estoque.setDeletado(false);
			estoqueRepository.save(estoque);
			ra.addFlashAttribute("msg", "Produto adicionado ao Estoque com Sucesso");
			ModelAndView mv = new ModelAndView("redirect:/Home/Estoque/Cadastro");
			return mv;
		} else {
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

	public ModelAndView EditarEstoque(Long idEstoque, RedirectAttributes ra) {
		Estoque estoque = estoqueRepository.buscarPorId(idEstoque);
		ModelAndView mv = new ModelAndView("Home/Estoque/Cadastro");
		mv.addObject("estoqueobj", estoque);
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		mv.addObject("produtos", produtoRepository.ListarProdutos());
		return mv;
	}

	public ModelAndView DeletarEstoque(Long idEstoque, RedirectAttributes ra) {
		Estoque estoque = estoqueRepository.buscarPorId(idEstoque);
		estoque.setDeletado(true);
		estoqueRepository.save(estoque);
		ra.addFlashAttribute("msg", "Produto Deletado do Estoque com Sucesso");
		ModelAndView mv = new ModelAndView("redirect:/Home/Estoque/Cadastro");
		return mv;
	}
	
	public Estoque buscaProdutoPorId(Long id) {
		Estoque estoque = estoqueRepository.buscarPorId(id);	
		return estoque;
	}
}
