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

@Controller
@RequestMapping("**/Home/Vendas/")
public class VendasController {
	
	@Autowired
	private VendasRepository vendasRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	List<Vendas> listaItens = new ArrayList<Vendas>();
	
	@GetMapping("Cadastro")
	public ModelAndView index() {
	    listaItens = new ArrayList<Vendas>();
		ModelAndView mv = new ModelAndView("/Home/Vendas/Cadastro");
		mv.addObject("vendasobj", new Vendas());
		mv.addObject("vendas", listaItens);
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		return mv;
	}
	
	@GetMapping("ListarVendas")
	public ModelAndView ListarVendas() {
		ModelAndView mv = new ModelAndView("/Home/Vendas/ListarVendas");
		mv.addObject("vendas", vendasRepository.ListarVendas());
		return mv;
	}
	
	@PostMapping("AddItens")
	public ModelAndView adicionarItens(Vendas vendas) {
		listaItens.add(vendas);
		ModelAndView mv = new ModelAndView("/Home/Vendas/Cadastro");
		mv.addObject("vendasobj", new Vendas());
		mv.addObject("vendas", listaItens);
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(RedirectAttributes ra) {
		Long total = vendasRepository.totalVendas();
		String codVenda = "VEN" + LocalDate.now().getYear() + total;
		for(Vendas v : listaItens) {
			Vendas vendas = new Vendas();
			vendas.setCodVenda(codVenda);
			vendas.setDeletado(false);
			vendas.setEstoque(v.getEstoque());
			vendas.setProduto(v.getProduto());
			vendas.setQuantidade(v.getQuantidade());
			vendas.setValorVenda((v.getValorVenda() * v.getQuantidade()));
			Vendas venda = vendasRepository.save(vendas);
			
			Estoque estoque = estoqueRepository.buscarPorId(v.getEstoque().getId());
			estoque.setQuantidade((estoque.getQuantidade()-venda.getQuantidade()));
			estoqueRepository.save(estoque);
		}
		
		ra.addFlashAttribute("msg", "Venda Confirmada com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Vendas/Cadastro");
		return mv;
		
	}
	
	
	@GetMapping("DeletarItem/{idVendas}")
	public ModelAndView deletar(@PathVariable("idVendas") Long idVendas, RedirectAttributes ra) {
		Vendas vendas = vendasRepository.buscarPorId(idVendas);
		Estoque estoque = estoqueRepository.buscarPorId(vendas.getEstoque().getId());
		estoque.setQuantidade((estoque.getQuantidade() + vendas.getQuantidade()));
	    estoqueRepository.save(estoque);
	    
		vendas.setDeletado(true);
		vendasRepository.save(vendas);
		ra.addFlashAttribute("msg", "Item deletado com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Vendas/ListarVendas");
		return mv;
	}
	
	@GetMapping("Relatorio")
	public ModelAndView Relatorio() {
		List<String> listafornecedores = fornecedorRepository.ListadeProdutosSemEstoque();
		List<String> listaprodutos = produtoRepository.ListadeProdutosSemEstoque();
		List<Object[]> categorias = estoqueRepository.buscarCategoriaComEstoque();
		List<CategoriaDto> categoriaDto = new ArrayList<CategoriaDto>();
		for (Object[] categoria : categorias) {
			CategoriaDto c = new CategoriaDto();
			c.setNome(categoria[0].toString());
			c.setQtdEstoque(categoria[1].toString());
			categoriaDto.add(c);
		}
		
		ModelAndView mv = new ModelAndView("/Home/Vendas/Relatorio");
		mv.addObject("categorias", categoriaDto);
		mv.addObject("produtos", listaprodutos);
		mv.addObject("fornecedores", listafornecedores);
		return mv;
	}
	

}
